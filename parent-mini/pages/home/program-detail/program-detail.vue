<template>
	<view class="detail-page">
		// #ifdef MP-WEIXIN
		<heads :titleText="'教学计划'" :backType="2" :background="'transparent'" :fontSize="36" :titleColor="'#FFFFFF'">
		</heads>
		// #endif

		<!-- banner -->
		<view class="banner-wrap">
		</view>

		<!-- 内容 -->
		<view class="container">
			<view class="title">
				{{programDetail.date}} {{programDetail.className}}
			</view>
			<view class="info">
				<view class="posted">
					<image v-if="programDetail.avatar" class="avatar" :src="programDetail.avatar" mode=""></image>
					<image v-else class="avatar" src="/static/images/common/avatar.png" mode=""></image>
					{{programDetail.createBy}}
				</view>
				<view class="time">
					{{programDetail.createTime}}
				</view>

			</view>

			<image v-if="programDetail.planUrl" class="plan-img" :src="programDetail.planUrl" mode="widthFix"
				@click="showImgPop(programDetail.planUrl)"></image>
			<view v-else class="img-empty">暂无计划图</view>

		</view>

		<!-- 大图 -->
		<uni-popup ref="popup" type="center" :animation="false">
			<view class="pop-content">
				<image class="plan-img" :src="showImgUrl" mode="widthFix"></image>
				<image @click="closePop" class="close" src="/static/images/common/close.png" mode=""></image>
			</view>
		</uni-popup>

	</view>
</template>

<script setup>
	import api from '@/api/index.js'
	import {
		ref,
		reactive,

	} from 'vue'
	import {
		onLoad,
		onHide
	} from '@dcloudio/uni-app'

	/* 页面加载 */
	let programId = ref()
	onLoad((options) => {
		programId.value = options.id
		getProgramDetail(programId.value)
	})

	onHide(() => {
		if(popup){
			popup.value.close()
		}
	})

	/* 获取计划列表 */
	let programDetail = reactive({})
	function getProgramDetail(id) {
		api.home._getProgramDetail(id).then(res => {
			console.log(3333, res);
			if (res.code == 200) {
				Object.assign(programDetail,res.data)

			}
		}).catch(() => {})
	}

	//展示大图
	let popup = ref()
	let showImgUrl = ref()
	function showImgPop(url){
		showImgUrl.value = url
		if(popup){
			console.log('popup',popup.value);
			popup.value.open()
		}
	}
	//关闭大图
	function closePop(){
		if(popup){
			console.log('popup',popup.value);
			popup.value.close()
		}
	}


	// api.home.getBacklog().then(res => {
	// 	if (res.code == 200) {
			
	// 	}
	
	// })

</script>

<style scoped lang="scss">
	.detail-page {
		position: relative;
		min-height: 100vh;
		background: $page-bg;

		.banner-wrap {
			position: absolute;
			top: 0;
			left: 0;
			width: 100%;
			height: 320rpx;
			background: $theme-color;
		}

		.container{
			position: relative;
			z-index: 2;
			margin: 30rpx $gutter 40rpx;
			background: $card-bg;
			border-radius: $radius-card;
			padding: $card-padding;

			.title{
				font-size: 32rpx;
				line-height: 1.4;
				color: $color1;
				border-left: 8rpx solid $theme-color;
				padding-left: 16rpx;
				font-weight: bold;
				word-break: break-all;
			}

			.info{
				display: flex;
				align-items: center;
				justify-content: space-between;
				font-size: $f24;
				color: $color6;
				margin-top: 24rpx;

				.posted{
					display: flex;
					align-items: center;
					flex: 1;
					min-width: 0;

					.avatar{
						width: 38rpx;
						height: 38rpx;
						margin-right: 8rpx;
						border-radius: 50%;
						flex-shrink: 0;
					}
				}

				.time{
					flex-shrink: 0;
					margin-left: 16rpx;
				}
			}

			.plan-img{
				display: block;
				width: 100%;
				margin-top: 30rpx;
				border-radius: 20rpx;
			}

			.img-empty{
				margin-top: 30rpx;
				padding: 120rpx 0;
				text-align: center;
				font-size: $f26;
				color: $color6;
				background: $theme-lighter;
				border-radius: 20rpx;
			}
		}
	}

	// 弹层
	.pop-content{
		margin: auto;
		text-align: center;
		.plan-img{
			width: 100vw;
		}
		.close{
			width: 70rpx;
			height: 70rpx;
			margin: 50rpx auto 0;
		}
	}

</style>
