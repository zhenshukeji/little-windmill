<template>
	<view class="tui-container">
		<view class="tui-upload-box">
			<view class="tui-image-item" v-for="(item,index) in imageList" :key="index">
				<image :src="item" class="tui-item-img" @tap.stop="previewImage(index)" mode="aspectFill"></image>
				<view v-if="!forbidDel" class="tui-img-del" @tap.stop="delImage(index)"></view>
				<view v-if="statusArr[index]!=1" class="tui-upload-mask">
					<view class="tui-upload-loading" v-if="statusArr[index]==2"></view>
					<text class="tui-tips">{{statusArr[index]==2?'上传中...':'上传失败'}}</text>
				</view>
			</view>
			<view v-if="isShowAdd" class="tui-upload-add" @tap="chooseImage">
				<image class="tui-upload-icon" src="/static//images/common/upload-img.png" mode=""></image>
			</view>
		</view>
	</view>
</template>

<script setup>
	import {
		ref,
		reactive,
		computed
	} from 'vue'
	import {baseData} from "@/utils/request.js"
	/* 子组件接受的参数 */
	const props = defineProps({
		//展示图片宽度
		width: {
			type: [Number, String],
			default: 220
		},
		//展示图片高度
		height: {
			type: [Number, String],
			default: 220
		},
		//初始化图片路径
		value: {
			type: Array,
			default () {
				return []
			}
		},
		//删除图片前是否弹框确认
		delConfirm: {
			type: Boolean,
			default: false
		},
		//禁用删除
		forbidDel: {
			type: Boolean,
			default: false
		},
		//禁用添加
		forbidAdd: {
			type: Boolean,
			default: false
		},
		//服务器接口地址。当接口地址为空时，直接返回本地图片地址
		serverUrl: {
			type: String,
			default: baseData.baseUrl+"/cdn/upload"
		},
		//限制数
		limit: {
			type: Number,
			default: 9
		},
		//original 原图，compressed 压缩图，默认二者都有
		sizeType: {
			type: Array,
			default () {
				return ['original', 'compressed']
			}
		},
		//album 从相册选图，camera 使用相机，默认二者都有。如需直接开相机或直接选相册，请只使用一个选项
		sourceType: {
			type: Array,
			default () {
				return ['album', 'remove']
			}
		},
		//可上传图片类型，默认为空，不限制  Array<String> ['jpg','png','gif']
		imageFormat: {
			type: Array,
			default () {
				return []
			}
		},
		//单张图片大小限制 MB 
		size: {
			type: Number,
			default: 4
		},
		//文件对应的key，默认为 file
		fileKeyName: {
			type: String,
			default: "file"
		},
		//HTTP 请求 Header, header 中不能设置 Referer。
		header: {
			type: Object,
			default () {
				return {}
			}
		},
		//HTTP 请求中其他额外的 form data
		formData: {
			type: Object,
			default () {
				return {}
			}
		},
	})
	// 接受的函数
	let emits = defineEmits(['complete', 'deleteImg'])


	/* 数据data */
	//图片地址
	const imageList = reactive([])
	//上传状态：1-上传成功 2-上传中 3-上传失败
	const statusArr = reactive([])

	/* 计算属性 */
	const isShowAdd = computed(() => {
		let isShow = true;
		if (props.forbidAdd || (props.limit && imageList.length >= props.limit)) {
			isShow = false;
		}
		return isShow
	})

	/* 方法 */
	//选择图片
	function chooseImage() {
		let _this = this;
		// 从本地相册选择图片或使用相机拍照。
		uni.chooseImage({
			count: props.limit - imageList.length,
			sizeType: props.sizeType,
			sourceType: props.sourceType,
			success: function(e) {
				console.log('选择图片成功！', e);
				let imageArr = [];
				for (let i = 0; i < e.tempFiles.length; i++) {
					let len = imageList.length;
					if (len >= props.limit) {
						toast(`最多可上传${props.limit}张图片`);
						break;
					}
					//过滤图片类型
					let path = e.tempFiles[i].path;

					if (props.imageFormat.length > 0) {
						let format = ""
						// #ifdef H5
						let type = e.tempFiles[i].type;
						format = type.split('/')[1]
						// #endif

						// #ifndef H5
						format = path.split(".")[(path.split(".")).length - 1];
						// #endif

						if (props.imageFormat.indexOf(format) == -1) {
							let text = `只能上传 ${props.imageFormat.join(',')} 格式图片！`
							toast(text);
							continue;
						}
					}

					//过滤超出大小限制图片
					let size = e.tempFiles[i].size;

					if (props.size * 1024 * 1024 < size) {
						let err = `单张图片大小不能超过：${_this.size}MB`
						toast(err);
						continue;
					}
					imageArr.push(path)
					imageList.push(path)
					statusArr.push("2")
				}
				change()

				let start = imageList.length - imageArr.length
				for (let j = 0; j < imageArr.length; j++) {
					let index = start + j
					//服务器地址
					if (props.serverUrl) {
						uploadImage(index, imageArr[j]).then(() => {
							console.log('statusArr', statusArr);
							change()
						}).catch(() => {
							change()
						})
					} else {
						//无服务器地址则直接返回成功
						statusArr[index] = "1"
						change()
					}
				}
			}
		})
	}
	/* 图片上传到服务器接口 */
	function uploadImage(index, url, serverUrl) {
		return new Promise((resolve, reject) => {
			uni.uploadFile({
				url: props.serverUrl || serverUrl,
				name: props.fileKeyName,
				header: props.header,
				formData: props.formData,
				filePath: url,
				success: function(res) {
					if (res.statusCode == 200) {

						//返回结果 此处需要按接口实际返回进行修改
						let d = JSON.parse(res.data.replace(/\ufeff/g, "") || "{}")
						//判断code，以实际接口规范判断
						if (d.code == 200) {
							console.log('上传到服务器成功！', res);
							console.log('d.url', d.url);
							console.log('imageList[index]', imageList[index]);
							// 上传成功 d.url 为上传后图片地址，以实际接口返回为准
							d.data.url && (imageList[index] = d.data.url)
							statusArr[index] = d.data.url ? "1" : "3"
						} else {
							// 上传失败
							console.log('上传到服务器失败了111', res);
							statusArr[index] = "3"
						}
						resolve(index)
					} else {
						console.log('上传到服务器失败了222', res);
						statusArr[index] = "3"
						reject(index)
					}
				},
				fail: function(res) {
					console.log('上传到服务器失败了333', res);
					statusArr[index] = "3"
					reject(index)
				}
			})
		})

	}


	/**
	 * 改变上传状态 ：1-上传成功 2-上传中 3-上传失败
	 **/
	function change() {
		let status = ~statusArr.indexOf("2") ? 2 : 1
		if (status != 2 && ~statusArr.indexOf("3")) {
			// 上传失败
			status = 3
		}
		console.log('图片状态为：', status);
		emits('complete', {
			status: status,
			imgArr: imageList,
		})
	}
	/* 删除图片 */
	function delImage(index) {
		console.log('删除图片');
		imageList.splice(index, 1)
		statusArr.splice(index, 1)
		emits('remove', {
			index: index
		})
		change()
	}
	/* 预览图片 */
	function previewImage(index) {
		if (!imageList.length) return;
		uni.previewImage({
			current: imageList[index],
			loop: true,
			urls: imageList
		})
	}
	/* toast */
	function toast(title) {
		uni.showToast({
			title: title,
			duration: 2000
		});
	}
</script>

<style lang="scss" scoped>
	.tui-icon-delete:before {
		content: "\e601";
	}

	.tui-icon-plus:before {
		content: "\e609";
	}

	.tui-upload-box {
		width: 100%;
		display: flex;
		flex-wrap: wrap;
	}

	// 点击添加上传图片
	.tui-upload-add {
		width: 180rpx;
		height: 180rpx;

		.tui-upload-icon {
			width: 100%;
			height: 100%;
			padding: 0;
		}
	}

	//上传后的图片显示样式
	.tui-image-item {
		position: relative;
		width: 180rpx;
		height: 180rpx;
		margin-right: 20rpx;
		margin-bottom: 20rpx;

		:nth-of-type(3n) {
			margin-right: 0;
		}

		.tui-item-img {
			width: 100%;
			height: 100%;
		}
	}

	.tui-img-del {
		width: 36rpx;
		height: 36rpx;
		position: absolute;
		right: -12rpx;
		top: -12rpx;
		background-color: #B3403F;
		border-radius: 50%;
		color: white;
		font-size: 34rpx;
		z-index: 200;
	}

	.tui-img-del::before {
		content: '';
		width: 16rpx;
		height: 1px;
		position: absolute;
		left: 10rpx;
		top: 18rpx;
		background-color: #fff;
	}

	.tui-upload-mask {
		width: 100%;
		height: 100%;
		position: absolute;
		left: 0;
		top: 0;
		display: flex;
		flex-direction: column;
		align-items: center;
		justify-content: center;
		padding: 40rpx 0;
		box-sizing: border-box;
		background-color: rgba(0, 0, 0, 0.6);
	}

	.tui-upload-loading {
		width: 28rpx;
		height: 28rpx;
		border-radius: 50%;
		border: 2px solid;
		border-color: #CFCFCF #CFCFCF #CFCFCF #fff;
		animation: tui-rotate 0.7s linear infinite;
	}

	@keyframes tui-rotate {
		0% {
			transform: rotate(0);
		}

		100% {
			transform: rotate(360deg);
		}
	}

	.tui-tips {
		font-size: 26rpx;
		color: #fff;
	}

	.tui-mask-btn {
		padding: 4rpx 16rpx;
		border-radius: 40rpx;
		text-align: center;
		font-size: 24rpx;
		color: #fff;
		border: 1px solid #fff;
		display: flex;
		align-items: center;
		justify-content: center;
		flex-shrink: 0;
		margin-top: 26rpx;
	}

	.tui-btn-hover {
		opacity: 0.8;
	}
</style>
