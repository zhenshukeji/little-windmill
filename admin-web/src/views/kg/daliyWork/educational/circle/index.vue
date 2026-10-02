<template>
  <div class="app-container contagion">
    <div class="left_box">
      <div class="head">班级动态</div>
      <div class="content">
        <div v-model="listData" class="item" v-for="(item, index) in listData" :key="index">
          <div class="title">
            <span>{{ item.username }} {{ item.postName }}</span>
            <span class="title_times">{{ timeAgo(item.createTime) }}</span>
          </div>
          <div class="desc">{{ item.textContent }}</div>
          <template v-if="item.uploadType === 1">
            <div class="video">
              <video controls :src="item.attachUrl" :autoplay="false"></video>
            </div>
          </template>
          <template v-else>
            <div class="img">
              <img v-if="item.imgUrlList" :src="item.imgUrlList[0]" alt="" />
            </div>
          </template>
          <div class="operator"><el-button size="mini" type="info" round @click="handleDelete(item)">删除</el-button></div>
        </div>
        <div v-if="notMove" class="check_move" @click="move">点击查看更多</div>
        <div v-else class="check_move">没有更多数据</div>
      </div>
    </div>
    <div class="right_box">
      <div class="head">发布动态</div>
      <div class="right_content">
        <div class="text_box">
          <el-input type="textarea" v-model.trim="textContent" :rows="4" maxlength="150" show-word-limit placeholder="说点什么吧..."></el-input>
        </div>
        <div class="select_box">
          <el-select v-model="uploadType" placeholder="请选择" @change="selectClick">
            <el-option v-for="item in uploadTypeList" :key="item.value" :label="item.name" :value="item.value"></el-option>
          </el-select>
          <span style="margin-left: 10px; color: #999999; font-size: 14px">注：切换后已上传的内容将被清空</span>
        </div>
        <div class="up_load_box">
          <el-upload v-if="(uploadType === 1 && attachUrls.length === 0) || (uploadType === 0 && attachUrls.length < 36)" class="avatar_uploader" :action="action" :headers="headers" :multiple="true" :limit="uploadType === 0 ? 36 : 1" :show-file-list="false" :on-success="handleAvatarSuccess" :before-upload="handleUploadImgBefore" :on-exceed="handleLimit">
            <!-- <i class="el-icon-plus avatar-uploader-icon"></i> -->
            <span style="font-size: 62px; color: #999">+</span>
            <span class="tips" v-if="uploadType === 0">可上传36张图</span>
            <span class="tips" v-if="uploadType === 1">可上传1个视频</span>
          </el-upload>
          <template v-if="attachUrls">
            <template v-if="uploadType === 0">
              <div class="item_img" v-for="(item, index) in attachUrls">
                <div class="close" @click="deleteClick(index)">X</div>
                <img :src="item" alt="" />
              </div>
            </template>
            <template v-if="uploadType === 1">
              <div class="item_img2" v-if="attachUrls.length != 0">
                <div class="close" @click="deleteClick(index)">X</div>
                <video :src="attachUrls[0]" controls id="videoId" :autoplay="false"></video>
              </div>
            </template>
          </template>
        </div>
        <div class="submit">
          <el-button type="primary" style="width: 100%" @click="submit">确定</el-button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { list, addPublish, delPublish } from '@/api/kg/work/educational/circle.js'
import { getToken } from '@/utils/auth'
import { timeAgo } from '@/utils/times'
export default {
  data() {
    return {
      timeAgo,
      notMove: false,
      listData: [],
      total: 0,
      queryParams: {
        pageNum: 1,
        pageSize: 10
      },
      textContent: '',
      uploadType: 0,
      attachUrls: [],
      uploadTypeList: [
        { value: 0, name: '图片' },
        { value: 1, name: '视频' }
      ],

      action: process.env.VUE_APP_BASE_API + '/cdn/upload',
      headers: {
        Authorization: 'Bearer ' + getToken()
      }
    }
  },
  mounted() {
    this.getList()
  },
  methods: {
    // 删除班级圈
    handleDelete (item) {
      delPublish({id: item.id}).then((res) => {
        console.info(res);
        if( res.code === 200) {
          this.$message.success("删除成功！");
          this.getList();
        } else {
          this.$message.success("删除成功！");
        }
      })
    },
    // 获取班级动态
    getList () {
      list(this.queryParams).then((response) => {
        if (response.code === 200) {
          this.total = response.data.total;
          console.info(this.total)
          console.info(response.data.records.length)
          console.info('flag：'+response.data.records.length < this.total)
          if (response.data.records.length < this.total) {
            console.info("in")
            this.notMove = true;
          } else {
            this.notMove = false;
          }
          // if (this.queryParams.pageSize > response.data.records.length) {
          //   this.notMove = true
          // }
          this.listData = response.data.records;
        }
      })
    },
    selectClick(val) {
      console.log(val)
      this.attachUrls = []
    },
    submit() {
      if (!this.textContent) {
        this.$message({
          message: '请输入内容',
          type: 'warning'
        })
        return false
      }
      if (this.attachUrls.length === 0) {
        this.$message({
          message: '请上传对应的文件',
          type: 'warning'
        })
        return false
      }
      let attachUrl = ''
      for (let i = 0; i < this.attachUrls.length; i++) {
        if (i === 0) {
          attachUrl = this.attachUrls[i]
        } else {
          attachUrl = attachUrl + ',' + this.attachUrls[i]
        }
      }
      addPublish({
        attachUrl: attachUrl,
        textContent: this.textContent,
        uploadType: this.uploadType
      }).then((res) => {
        if (res.code === 200) {
          console.log(res)
          this.$message({
            message: '发布动态成功！',
            type: 'success'
          })
          this.queryParams.pageNum = 1;
          this.queryParams.pageSize = 10;
          this.getList();
          this.textContent = ''
          this.attachUrls = []
          this.uploadType = 0
        }
      })
    },
    move() {
      this.queryParams.pageSize += 10;
      this.getList();
    },
    // 超出数量
    handleLimit() {
      this.msgError('超出规定上限')
    },
    // 图片上传成功回调
    handleAvatarSuccess(res) {
      console.log(res)
      if (res.code === 200) {
        this.attachUrls.push(res.data.url)
        this.$message({
          message: '上传成功',
          type: 'success'
        })
      } else {
        this.$message.error('上传失败')
      }
    },

    // 限制上传文件类型
    handleUploadImgBefore(file) {
      if (this.uploadType === 0) {
        const isPic = [
          'image/jpeg',
          'image/png',
          'image/jpg',
          'image/gif'
        ].includes(file.type)
        const isLt2M = file.size / 1024 / 1024 < 2
        if (!isPic) {
          this.$message.error('请上传规定的图片格式(png/jpg/jpeg/gif)')
          return false
        }
        if (!isLt2M) {
          this.$message.error(`上传图片大小不能超过2MB!`)
          return false
        }
        return isPic && isLt2M
      }
      if (this.uploadType === 1) {
        const isLt10M = file.size / 1024 / 1024 < 10
        const isVideo = [
          'video/mp4',
          'video/ogg',
          'video/flv',
          'video/avi',
          'video/wmv',
          'video/rmvb',
          'video/mov'
        ].indexOf(file.type)
        if (isVideo) {
          this.$message.error('请上传正确的视频格式')
          return false
        }
        if (!isLt10M) {
          this.$message.error('视频大小不能超过10MB')
          return false
        }
        return isVideo && isLt10M
      }
    },

    deleteClick(index) {
      this.attachUrls.splice(index, 1)
    }
  }
}
</script>

<style lang="scss" scoped>
.contagion {
  width: 100%;
  display: flex;
  box-sizing: border-box;
  background-color: #e3e3e3;
  overflow: auto;
  .left_box {
    min-width: 360px;
    height: 800px;
    overflow: auto;
    margin-right: 20px;
    box-sizing: border-box;
    background-color: #f5f5f5;
    overflow: hidden;
    .head {
      height: 35px;
      line-height: 35px;
      text-align: center;
      font-weight: bold;
      background-color: #f5f5f5;
    }
    .content {
      height: calc(100% - 36px);
      box-sizing: border-box;
      //height: 100%;
      padding-bottom: 40px;
      overflow: auto;
      .item {
        padding: 20px 30px 10px;
        box-sizing: border-box;
        min-height: 220px;
        background-color: #fff;
        margin-bottom: 20px;
        .title {
          display: flex;
          flex-direction: column;
          margin-bottom: 10px;
          .title_times {
            font-size: 12px;
          }
        }
        .desc {
          margin-bottom: 10px;
        }
        .operator {
          margin-top: 10px;
        }
        .video {
          width: 260px;
          height: 120px;
          video {
            width: 100%;
            height: 100%;
          }
        }
        .img {
          width: 100px;
          height: 100px;
          img {
            display: block;
            width: 100%;
            height: 100%;
          }
        }
      }
      .item:nth-last-child(2) {
        margin-bottom: 0;
      }
      .check_move {
        width: 100%;
        height: 40px;
        line-height: 40px;
        text-align: center;
        background-color: #f1f1f1;
      }
    }
  }
  .right_box {
    width: calc(100% - 36px);
    height: 100%;
    min-height: 800px;
    background-color: #fff;
    .head {
      height: 35px;
      line-height: 35px;
      text-align: center;
      font-weight: bold;
      background-color: #f5f5f5;
    }
    .right_content {
      padding: 30px;
      .text_box {
        margin-bottom: 40px;
      }
      .select_box {
        margin-bottom: 40px;
      }
      .up_load_box {
        display: flex;
        margin-bottom: 40px;
        flex-wrap: wrap;

        .avatar_uploader {
          position: relative;
          width: 150px;
          height: 150px;
          display: flex;
          margin-right: 5px;
          margin-bottom: 5px;
          border: 1px dashed #cdcdcd;
          ::v-deep .el-upload {
            width: 100%;
            height: 100%;
            display: flex;
            align-items: center;
            flex-direction: column;
            // border: 1px solid #e4e4e4;
            justify-content: center;
            .tips {
              font-size: 14px;
              color: #999;
              font-weight: bold;
            }
          }
        }
        .item_img {
          position: relative;
          width: 150px;
          height: 150px;
          margin-right: 5px;
          margin-bottom: 5px;
          border: 1px dashed #cdcdcd;
          #videoId {
            width: 260px;
            height: 150px;
            video {
              width: 100%;
              height: 100%;
            }
          }
          .close {
            display: flex;
            align-items: center;
            justify-content: center;
            width: 15px;
            height: 15px;
            font-size: 12px;
            background-color: red;
            color: #fff;
            cursor: pointer;
            position: absolute;
            top: 0;
            right: 0;
          }
        }
        .item_img2 {
          position: relative;
          width: 260px;
          height: 150px;
          margin-right: 5px;
          margin-bottom: 5px;
          border: 1px dashed #cdcdcd;
          #videoId {
            width: 260px;
            height: 150px;
            video {
              width: 100%;
              height: 100%;
            }
          }
          .close {
            position: absolute;
            top: 0;
            right: 0;
            padding: 2px 6px;
            cursor: pointer;
            font-size: 12px;
            background-color: red;
            color: #fff;
            z-index: 10;
          }
        }
        img {
          width: 100%;
          height: 100%;
        }
      }
    }
  }
}
</style>
