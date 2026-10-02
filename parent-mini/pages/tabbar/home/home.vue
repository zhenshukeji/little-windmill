<template>
	<view class="container">
		// #ifdef MP-WEIXIN
		<heads :showBack="false" titleText="风车智慧幼教" :leftAlign="true" :fontSize="36"></heads>
		// #endif
		<!-- 轮播图 -->
		<view class="uni-margin-wrap">
			<swiper class="swiper" :autoplay="autoplay" :current="currentIndex" circular :interval="interval"
				:duration="duration" @change="changeHandle">
				<swiper-item v-for="item in imgList" :key="item">
					<image :src="item"></image>
				</swiper-item>
			</swiper>
			<view class="indicator-box">
				<view v-for="(item,index) in imgList" :key="item" class="indicator-dot"
					:class="index==currentIndex?'active-dot':''"></view>

			</view>
		</view>
		<!-- 功能区 -->
		<view class="func-box">
			<view v-for="(item,index) in funcList" :key="item.name" @click="toFuncPage(index)" class="func-item">
				<image :src="item.imgUrl" mode=""></image>
				<view class="name">
					{{item.name}}
				</view>
			</view>
		</view>
		<!-- 班级圈 -->
		<view class="class-circle">
			<view class="box-title">园内动态</view>
			<view class="box-sub">本园家长可见</view>
			<view class="list">
				<view class="card" v-for="(item,index) in circleList" :key="item.dealId">
					<view class="top">
						<image class="avatar" :src="item.avatar||'/static/images/home/avatar.png'"></image>
						<view class="name">
							{{item.createBy}}
						</view>
						<view class="time">
							{{item.createTime}}
						</view>

					</view>
					<view class="content">
						{{item.textContent}}
					</view>
					<!-- 上传附件类型(0 图片 1 视频)；无附件时整块不渲染，避免空 video 黑块 -->
					<view v-if="item.uploadType==0 && item.attachUrl && item.attachUrl.length" class="pictrues">
						<template v-for="(item1,index1) in item.attachUrl" :key="item1">
							<image @click="previewImg(index,index1)" v-show="index1<9" :src="item1" mode="aspectFill">
							</image>
						</template>
					</view>
					<video v-else-if="item.uploadType==1 && item.attachUrl" id="myVideo" :src="item.attachUrl"></video>
				</view>

			</view>
		</view>

	</view>
</template>

<script setup>
	import api from '@/api/index.js'
	import {
		ref,
		reactive,
		onMounted
	} from 'vue'
	import {
		onLoad
	} from '@dcloudio/uni-app'
	// 轮播图配置项
	let show = ref(false)
	let indicatorDots = ref(true)
	let interval = ref(2000)
	let duration = ref(500)
	let autoplay = ref(true)
	let currentIndex = ref(0)
	/* 功能区 */
	const funcList = reactive([{
			name: '园区概况',
			imgUrl: '/static/images/home/func1.png',
			path: "/pages/home/school-survey/school-survey"

		},
		{
			name: '教学计划',
			imgUrl: '/static/images/home/func6.png',
			path: "/pages/home/program-list/program-list"
		},
	])

	function toFuncPage(index) {
		console.log('功能区跳转', index);
		if (funcList[index].path) {
			console.log(funcList[index]);
			uni.navigateTo({
				url: funcList[index].path
			})
		}
	}
	
	/* 页面加载 */
	onLoad((options) => {
		getBannerList()
		getMy()
	})
	
	
	/* 轮播图 */
	const imgList = reactive([])
	function getBannerList(){
		api.home._getBanner().then(res => {
			if(res.data.imgUrl){
				imgList.length = 0
				imgList.push(...res.data.imgUrl)
			}

		}).catch(() => {})
	}
	/* 我的信息 */
	function getMy(){
		api.home._getMy().then(res => {
			if(res.code==200){
				// 在缓存中存入学生信息
				uni.setStorageSync('studentInfo', res.data)
			}

		}).catch(() => {})
	}
	//处理轮播图指示器
	function changeHandle(e) {
		currentIndex.value = e.target.current
	}

	/* 班级圈列表 */
	const circleList = reactive([])
	api.home.getCircle().then(res => {
		if (res.code == 200) {
			// res.data.length ? circleList.push(...res.data) : ''
			// console.log('circleList:', res.data);
			const list = res.data || []
			list.forEach(item => {
				if (item.uploadType == 0) {
					item.attachUrl?item.attachUrl = item.attachUrl.split(','):''
				}
			})
			circleList.push(...list)
			console.log('circleList:', circleList);
		}
	}).catch(() => {})
	// 预览图片
	function previewImg(fatherIndex, index) {
		uni.previewImage({
			urls: circleList[fatherIndex].attachUrl,
			current: index
		});
	}
	
</script>

<style scoped lang="scss">
	.container {
		min-height: 100vh;
		background: $page-bg;
		padding: 0 32rpx;

		// 轮播图
		.uni-margin-wrap {
			position: relative;
			margin-top: 16rpx;

			.swiper {
				height: 250rpx;
				border-radius: 32rpx;
				overflow: hidden;
			}

			image {
				display: block;
				width: 100%;
				height: 100%;
			}

			.indicator-box {
				display: flex;
				justify-content: center;
				margin-top: 16rpx;

				.indicator-dot {
					width: 10rpx;
					height: 10rpx;
					border-radius: 50%;
					background: #CFCFCF;
					margin-right: 10rpx;

					&:last-child {
						margin-right: 0;
					}

					&.active-dot {
						width: 24rpx;
						background: $theme-color;
						border-radius: 5rpx;
					}
				}
			}
		}

		// 功能区（八宫格，共 8 项 2 行）
		.func-box {
			display: flex;
			flex-wrap: wrap;
			margin-top: 36rpx;

			.func-item {
				width: 25%;
				margin-bottom: 28rpx;
				display: flex;
				flex-direction: column;
				align-items: center;

				&:nth-last-child(-n+4) {
					margin-bottom: 0;
				}

				image {
					width: 80rpx;
					height: 80rpx;
					display: block;
				}

				.name {
					margin-top: 10rpx;
					font-size: 24rpx;
					color: $color1;
					white-space: nowrap;
				}
			}
		}

		// 待办事项
		.todo-box {
			margin-top: 44rpx;
		}

		.card {
			background: $card-bg;
			border-radius: $radius-card;
			padding: $card-padding;
		}

		.todo-box .card {
			padding: 0 $card-padding;
		}

		.todo-box .item {
			display: flex;
			align-items: center;
			padding: 28rpx 0;
			border-bottom: 1rpx solid $line-color;

			&:last-child {
				border-bottom: 0;
			}

			.icon {
				width: 60rpx;
				height: 60rpx;
				margin-right: 20rpx;
				flex-shrink: 0;
			}

			.content {
				flex: 1;
				font-size: 28rpx;
				line-height: 1.5;
				color: $color1;
			}

			.pill {
				flex-shrink: 0;
				margin-left: 20rpx;
				padding: 10rpx 22rpx;
				font-size: 24rpx;
				color: $theme-color;
				background: $theme-lighter;
				border-radius: $radius-tag;
			}
		}

		// 班级圈
		.class-circle {
			margin-top: 44rpx;
			margin-bottom: 60rpx;

			.card {
				margin-bottom: 24rpx;
			}

			.top {
				display: flex;
				align-items: center;

				.avatar {
					width: 72rpx;
					height: 72rpx;
					border-radius: 50%;
					flex-shrink: 0;
				}

				.name {
					flex: 1;
					font-size: 28rpx;
					color: $color1;
					padding: 0 16rpx;
					overflow: hidden;
				}

				.time {
					font-size: 24rpx;
					color: $color6;
					flex-shrink: 0;
				}
			}

			.content {
				margin-top: 16rpx;
				font-size: 28rpx;
				line-height: 1.7;
				color: $color1;
			}

			.pictrues {
				margin-top: 16rpx;
				display: flex;
				flex-wrap: wrap;

				image {
					width: 100%;
					height: 380rpx;
					border-radius: 16rpx;
					margin-bottom: 12rpx;
					background: #f5f5f5;
				}
			}

			#myVideo {
				width: 380rpx;
				height: 380rpx;
				margin-top: 16rpx;
				border-radius: 16rpx;
			}
		}

		.box-title {
			color: $color1;
			font-size: 32rpx;
			line-height: 44rpx;
			font-weight: bold;
			margin-bottom: 20rpx;
		}
	}
</style>
