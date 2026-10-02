<template>
  <div>
    <!-- 图片/视频 -->
    <template v-if="type === 'img' || type === 'video'">
      <el-upload
        ref="upload"
        :file-list="fileList"
        :action="action"
        :headers="headers"
        :show-file-list="isArray"
        :accept="accept"
        :on-preview="handlePictureCardPreview"
        :multiple="limit > 1"
        :on-success="handleSuccess"
        :before-upload="beforeUpload"
        :on-error="handleError"
        list-type="picture-card"
      >
        <slot>
          <template v-if="isArray">
            <i class="el-icon-plus"></i>
          </template>
          <!-- 非数组 -->
          <template v-if="!isArray">
            <div v-if="type === 'img'" class="avatar">
              <img v-if="file" :src="file" class="avatar_img" />
              <i v-else class="el-icon-plus"></i>
            </div>
            <!-- 视频 -->
            <div v-if="type === 'video'" class="video_wrap">
              <video v-if="file" :src="file" />
              <i v-else class="el-icon-plus"></i>
            </div>
          </template>
        </slot>

        <div slot="file" slot-scope="{ file }" class="file_box">
          <!-- 视频 -->
          <template v-if="file && checkType(file.url) === 'VIDEO'">
            <video :src="file.url" controls class="media_video"></video>
          </template>
          <!-- 图片 -->
          <template v-if="file && checkType(file.url) === 'IMG'">
            <img
              class="el-upload-list__item-thumbnail"
              :src="file.url"
              alt=""
            />
          </template>

          <!-- 操作按钮 -->
          <span class="el-upload-list__item-actions">
            <span
              class="el-upload-list__item-preview"
              @click="handlePictureCardPreview(file)"
            >
              <i class="el-icon-zoom-in"></i>
            </span>
            <span
              class="el-upload-list__item-delete"
              @click="beforeRemove(file)"
            >
              <i class="el-icon-delete"></i>
            </span>
          </span>
        </div>
      </el-upload>
    </template>
    <!-- 文件 -->
    <template v-else>
      <el-upload
        :show-file-list="false"
        action=""
        drag
        :accept="accept"
        :http-request="importFile"
        :before-upload="beforeUpload"
      >
        <slot>
          <i class="el-icon-upload"></i>
          <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
        </slot>
      </el-upload>
    </template>

    <!-- 预览图片 -->
    <el-dialog title="预览图片" :visible.sync="dialogVisible" append-to-body>
      <img width="100%" :src="dialogImageUrl" alt="" />
    </el-dialog>
  </div>
</template>

<script>
import { getToken } from "@/utils/auth";
export default {
  props: {
    // type为具体的文件类型
    type: {
      type: [Array, String],
      default: "img",
    },
    // 一次可以上传个文件
    limit: {
      type: Number,
      default: 1,
    },
    // 单张图片回显
    file: {
      type: [Array, String],
      default: "",
    },
    // 上传文件的大小
    size: {
      type: Number,
      default: 2,
    },
  },
  watch: {
    file(val, oldVal) {
      // 长度为0时，清空上传的数据
      if (this.isArray && val.length === 0) {
        this.fileList = [];
      }

      // 首次赋值
      if (!oldVal && this.isArray && val.length > 0) {
        this.fileList = [];
        this.file.forEach((item) => {
          this.fileList.push({
            url: item,
          });
        });
      }
    },
  },
  data() {
    return {
      headers: {
        Authorization: "Bearer " + getToken(),
      },
      action: process.env.VUE_APP_BASE_API + "/cdn/upload",
      fileList: [],
      loading: "",
      accept: "",
      isArray: false,
      dialogVisible: false,
      dialogImageUrl: "",
    };
  },
  mounted() {
    if (this.type === "img") {
      this.accept = "image/*";
    } else if (this.type === "video") {
      this.accept = "video/*";
    } else if (this.type === "xlsx") {
      this.accept =
        "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet, application/vnd.ms-excel";
    }
    this.isArray = this.limit > 1;
  },
  methods: {
    // 限制上传
    beforeUpload(file) {
      this.loading = this.$loading({
        lock: true,
        text: "Loading",
        spinner: "el-icon-loading",
        background: "rgba(0, 0, 0, 0.7)",
      });
      const limitSize = file.size / 1024 / 1024 < this.size;
      if (!limitSize) {
        this.$message({
          message: `上传的文件大小不能超过${this.size}M`,
          type: "error",
          duration: 2000,
        });
        this.loading.close();
        return false;
      }
      // 截取后缀
      let ext = file.name.substring(file.name.lastIndexOf(".") + 1);
      // 图片
      if (this.type === "img") {
        var isPic =
          ext === "jpeg" || ext === "png" || ext === "jpg" || ext === "gif";
        if (!isPic) {
          this.$message({
            message: "请上传jpg/jpeg/png/gif类型的图片文件",
            type: "error",
            duration: 2000,
          });
          this.loading.close();
          return false;
        }
      }
      // xlsx
      if (this.type === "xlsx") {
        var isExcel = ext === "xlsx" || ext === "xls";
        if (!isExcel) {
          this.$message({
            message: "请上传xlsx表格文件",
            type: "error",
            duration: 2000,
          });
          this.loading.close();
          return false;
        }
      }

      // 视频
      if (this.type === "video") {
        var isVideo = ["mp4", "webm", "ogg", "avi"].includes(ext);
        if (!isVideo) {
          this.$message({
            message: "mp4/webm/ogg/avi类型的视频文件",
            type: "error",
            duration: 2000,
          });
          this.loading.close();
          return false;
        }
      }
    },
    // 校验类型
    checkType(name) {
      // 截取后缀
      let ext = "";
      if (name && name.lastIndexOf(".") != -1) {
        ext = name.substring(name.lastIndexOf(".") + 1).toLowerCase();
      } else if (name && name.lastIndexOf("/") != -1) {
        ext = name.substring(name.lastIndexOf("/") + 1).toLowerCase();
      }
      if (["jpeg", "png", "jpg", "gif"].includes(ext)) {
        return "IMG";
      }
      if (["mp4", "avi", "ogg", "webm", "mov"].includes(ext)) {
        return "VIDEO";
      }
    },
    // 移除文件
    beforeRemove(res) {
      console.log("res", res);
      let url = res.response?.data?.url;
      // 删除fileList中的图片，因为上传多张图片时顺序不一定
      const uploadFiles = this.$refs.upload.uploadFiles;
      for (let i = 0; i < uploadFiles.length; i++) {
        if (uploadFiles[i]["url"] === res.url) {
          uploadFiles.splice(i, 1);
        }
      }
      // 删除数组中的图片
      if (url) {
        // 根据url找到数组里的长度
        let index = this.file.findIndex((item) => {
          return item === url;
        });
        console.log("index", index);
        if (index > -1) {
          this.$emit("removeFile", index);
        }
      }
    },
    // 上传异常
    handleError() {
      console.log("捕获异常");
      this.loading.close();
    },
    // 预览图片
    handlePictureCardPreview(file) {
      this.dialogImageUrl = file.url;
      this.dialogVisible = true;
    },
    // 上传成功
    handleSuccess(res, file) {
      console.log("成功", res, file);
      file.url = res.data.url;
      if (res.code === 200) {
        this.$emit("upload", res.data);
      } else {
        this.msgError(res.msg);
      }
      this.loading.close();
    },
    // 上传文件成功
    importFile(res) {
      console.log("res", res.file);
      var file = new FormData();
      file.append("file", res.file);
      this.$emit("upload", file);
      this.loading.close();
    },
  },
};
</script>

<style lang="scss" scoped>
::v-deep .el-upload {
  display: inline-block;
}

.avatar {
  width: 100%;
  height: 100%;
  // width: 148px;
  // height: 148px;
  // line-height: 148px;
  cursor: pointer;
  vertical-align: top;
  box-sizing: border-box;
  border-radius: 6px;
  background-color: #fbfdff;
  // border: 1px dashed #c0ccda;

  .avatar_img {
    width: 100%;
    height: 100%;
    display: block;
    border-radius: 6px;
  }

  ::v-deep .el-icon-plus {
    width: 102px;
    height: 102px;
    line-height: 102px;
  }
}

// .avatar:hover {
//   border: 1px dashed #409eff;
// }

::v-deep .el-upload-dragger {
  margin: 0 auto;
}

.file_box {
  width: 100%;
  height: 100%;
}

.media {
  position: relative;
  width: 100%;
  height: 100%;

  .media_video {
    width: 100%;
    height: 100%;
    object-fit: fill;
  }
}
</style>
