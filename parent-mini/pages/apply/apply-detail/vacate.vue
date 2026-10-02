<template>
	<view class="container">
		// #ifdef MP-WEIXIN
		<heads titleText="申请详情"></heads>
		// #endif

		<!-- 摘要：类型 + 状态 -->
		<view class="card detail-box">
			<view class="head">
				<view class="title">
					请假类型：{{detailData.vacateType==1?'事假':'病假'}}
				</view>
				<view v-if="detailData.status==1" class="statu status1 bg1">
					已同意
				</view>
				<view v-else-if="detailData.status==2" class="statu status2 bg2">
					拒绝
				</view>
				<view v-else-if="detailData.status==3" class="statu status3 bg3">
					撤回
				</view>
				<view v-else class="statu status0 bg0">
					待处理
				</view>
			</view>
			<!-- 请假事由与时间段 -->
			<view class="reason-box">
				<image src="/static/images/apply/qingjia.png"></image>
				<view class="text">
					<view class="reason">
						{{fmt(detailData.vacateReason)}}
					</view>
					<view class="time">
						{{fmt(detailData.beginTime)}} - {{fmt(detailData.endTime)}}
					</view>
				</view>
			</view>
		</view>

		<!-- 审批说明：无审批内容时不显示空卡 -->
		<view class="card reply-card" v-if="hasReply">
			<view class="group-title">
				<view class="line"></view>
				审批说明
			</view>
			<view class="reply-box">
				<view class="doctor">
					<image :src="detailData.approveByHead||'/static/images/home/avatar.png'"></image>
					<view class="text">
						<view class="name">
							{{fmt(detailData.approveName)}}
						</view>
						<view class="time">
							{{fmt(detailData.approveTime)}}
						</view>
					</view>
				</view>
				<view class="reply-content" v-if="detailData.approveOpinion">
					{{detailData.approveOpinion}}
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
		computed
	} from 'vue'
	import {
		onLoad
	} from '@dcloudio/uni-app'
	
	//传进来的id
	const id = ref()
	
	//详情数据
	let detailData = reactive({})
	
	// 是否已有审批内容：全部为空时不渲染审批卡，避免空白块
	const hasReply = computed(() => {
		return !!(detailData.approveName || detailData.approveTime || detailData.approveOpinion)
	})

	/* 展示层：缺值占位，避免出现空白行；不改动任何取数逻辑 */
	function fmt(v) {
		if (v === null || v === undefined || v === '') return '—'
		return v
	}
	
	onLoad((option) => {
		id.value = option.id
		// console.log('id.value',id.value);
		if(id.value){
			api.apply._getVacateDetail(id.value).then(res => {
				if (res.code == 200) {
					detailData = Object.assign(detailData,res.data)
					console.log('detailData',detailData);
				}

			}).catch(() => {})
		}
	})
	
	
	// api.home.getBacklog().then(res => {
	// 	if (res.code == 200) {
			
	// 	}
	
	// })
	
</script>

<style scoped lang="scss">
	.container{
		min-height: 100vh;
		background: $page-bg;
		padding: 24rpx $gutter 40rpx;

		.card {
			background: $card-bg;
			border-radius: $radius-card;
			padding: $card-padding;
		}

		.group-title {
			display: flex;
			align-items: center;
			font-size: $f30;
			line-height: 1.4;
			color: $color1;
			font-weight: bold;
			padding-bottom: 20rpx;
			border-bottom: 1rpx solid $line-color;

			.line {
				width: 8rpx;
				height: 28rpx;
				border-radius: 4rpx;
				background: $theme-color;
				margin-right: 12rpx;
				flex-shrink: 0;
			}
		}

		.detail-box{
			.head{
				display: flex;
				justify-content: space-between;
				align-items: center;

				.title{
					flex: 1;
					min-width: 0;
					display: flex;
					align-items: center;
					color: $color1;
					font-size: $f30;
					line-height: 1.4;
					font-weight: bold;
					word-break: break-all;

					&:before{
						content: '';
						display: block;
						width: 8rpx;
						height: 28rpx;
						border-radius: 4rpx;
						background: $theme-color;
						margin-right: 12rpx;
						flex-shrink: 0;
					}
				}

				.statu{
					flex-shrink: 0;
					margin-left: 16rpx;
					font-size: $f24;
					line-height: 1.5;
					padding: 4rpx 18rpx;
					border-radius: $radius-tag;
				}
			}

			.reason-box{
				margin-top: 24rpx;
				padding-top: 20rpx;
				border-top: 1rpx solid $line-color;
				display: flex;
				align-items: flex-start;

				image {
					width: 80rpx;
					height: 80rpx;
					flex-shrink: 0;
				}

				.text {
					flex: 1;
					min-width: 0;
					margin-left: 18rpx;

					.reason {
						font-size: $f28;
						line-height: 1.6;
						color: $color1;
						font-weight: bold;
						word-break: break-all;
					}

					.time {
						margin-top: 8rpx;
						color: $color6;
						font-size: $f26;
						line-height: 1.5;
						font-weight: normal;
						word-break: break-all;
					}
				}
			}
		}

		.reply-card {
			margin-top: 24rpx;

			.reply-box{
				.doctor{
					display: flex;
					align-items: center;
					margin: 20rpx 0 16rpx;

					image {
						width: 80rpx;
						height: 80rpx;
						border-radius: 50%;
						flex-shrink: 0;
					}

					.text {
						flex: 1;
						min-width: 0;
						margin-left: 18rpx;

						.name {
							font-size: $f28;
							line-height: 1.5;
							color: $color1;
							font-weight: bold;
							word-break: break-all;
						}

						.time {
							margin-top: 4rpx;
							color: $color6;
							font-size: $f26;
							line-height: 1.5;
							font-weight: normal;
						}
					}
				}

				.reply-content{
					font-size: $f28;
					line-height: 1.6;
					color: $color1;
					word-break: break-all;
				}
			}
		}

	}
	
</style>
