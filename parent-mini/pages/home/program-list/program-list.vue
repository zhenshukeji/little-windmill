<template>
	<view class="program-page">
		// #ifdef MP-WEIXIN
		<heads titleText="教学计划"></heads>
		// #endif

		<view class="container">
			<view class="program-list">
				<view class="program-item" v-for="item in programList" :key="item.id">
					<view class="left-wrap">
						<view class="time">
							{{item.createTime}}
						</view>
						<view class="circle"></view>
						<view class="dotted"></view>
					</view>
					<view class="right-wrap">
						<view class="card">
							<view class="plan-date" @click="toProgramDetail(item.id)">
								{{item.date}}
							</view>
							<image v-if="item.planUrl" class="plan-img" :src="item.planUrl" mode="aspectFill"
								@click="showImgPop(item.planUrl)"></image>
						</view>
						<view class="desc-box">
							<view class="pub-time">
								编辑于 {{item.createTime}}
							</view>
							<view class="post-by">
								发布者：
								<image v-if="item.avatar" class="avatar" :src="item.avatar" mode=""></image>
								<image v-else class="avatar" src="/static/images/common/avatar.png" mode=""></image>
								{{item.createBy}}
							</view>
						</view>

					</view>

				</view>

			</view>
			<view class="empty" v-if="loaded && !programList.length">暂无教学计划</view>

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
		onMounted
	} from 'vue'
	import {
		onLoad,
		onHide
	} from '@dcloudio/uni-app'


	// popup.value.open('top')

	/* 页面加载 */
	let popup = ref()
	let loaded = ref(false) // 首次数据返回后置真，仅用于空态判定
	onLoad(() => {
		getProgramList()
	})
	onHide(() => {
		if(popup){
			popup.value.close()
		}
	})
	/* 获取计划列表 */
	let programList = reactive([])
	function getProgramList() {
		api.home._getProgramList().then(res => {
			console.log(3333, res);
			if (res.code == 200) {
				programList.length = 0
				programList.push(...res.data)

			}
		}).finally(() => {
			loaded.value = true
		})
	}

	//展示大图
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

	//关闭大图
	function toProgramDetail(id){
		uni.navigateTo({
			url:'/pages/home/program-detail/program-detail?id='+id
		})
	}

</script>

<style scoped lang="scss">
	.program-page {
		min-height: 100vh;
		background: $page-bg;
	}

	.container{
		padding: 24rpx $gutter 40rpx;

		.program-list{
			.program-item{
				display: flex;
				margin-bottom: 32rpx;

				&:last-child{
					margin-bottom: 0;
				}

				.left-wrap{
					width: 88rpx;
					flex-shrink: 0;
					font-size: $f24;
					color: $theme-color;
					display: flex;
					flex-direction: column;
					align-items: center;

					.time{
						text-align: center;
						line-height: 1.4;
						word-break: break-all;
					}

					.circle{
						width: 24rpx;
						height: 24rpx;
						background-color: $theme-color;
						border: 6rpx solid $theme-lighter;
						border-radius: 50%;
						margin-top: 20rpx;
						box-sizing: content-box;
						flex-shrink: 0;
					}
					.dotted{
						border-right: 1rpx dashed $theme-color;
						flex: 1;
						opacity: 0.5;
					}
				}
				.right-wrap{
					flex: 1;
					min-width: 0;

					.card{
						background: $card-bg;
						border-radius: $radius-card;
						padding: $card-padding;

						.plan-date{
							font-size: $f28;
							color: $color1;
							font-weight: bold;
							word-break: break-all;
						}

						.plan-img{
							display: block;
							width: 100%;
							height: 360rpx;
							margin-top: 24rpx;
							border-radius: 20rpx;
						}
					}

					.desc-box{
						display: flex;
						align-items: center;
						justify-content: space-between;
						font-size: $f24;
						color: $color6;
						margin-top: 20rpx;

						.pub-time{
							flex: 1;
							min-width: 0;
							word-break: break-all;
						}

						.post-by{
							display: flex;
							align-items: center;
							flex-shrink: 0;
							margin-left: 16rpx;

							.avatar{
								width: 38rpx;
								height: 38rpx;
								margin-right: 8rpx;
								border-radius: 50%;
							}
						}
					}
				}
			}
		}

		.empty {
			padding: 120rpx 0;
			text-align: center;
			font-size: $f26;
			color: $color6;
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
