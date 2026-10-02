<template>
	<view class="container">
		// #ifdef MP-WEIXIN
		<heads titleText="班级信息"></heads>
		// #endif

		<!-- 班级摘要 -->
		<view class="card shadow-box">
			<view class="title">
				<view class="line"></view>
				班级信息
			</view>
			<view class="content">
				<view class="item">
					<view class="name">班级名称</view>
					<view class="value">{{fmt(info.className)}}</view>
				</view>
				<view class="item">
					<view class="name">年级名称</view>
					<view class="value">{{fmt(info.gradeName)}}</view>
				</view>
			</view>
		</view>

		<!-- 老师信息 -->
		<view class="card shadow-box">
			<view class="title">
				<view class="line"></view>
				老师信息
			</view>
			<view class="content">
				<view class="item">
					<view class="name">班主任</view>
					<view class="value">{{fmt(info.teacherName)}}</view>
				</view>
				<view class="item">
					<view class="name">联系电话</view>
					<view class="value phone" v-if="info.teacherPhone" @click="copyPhone(info.teacherPhone)">
						{{info.teacherPhone}}
						<image class="copy-icon" src="/static/images/common/copy.png" mode=""></image>
					</view>
					<view class="value" v-else>—</view>
				</view>
				<template v-if="info.subTeacherName||info.subTeacherPhone">
					<view class="item">
						<view class="name">副班主任</view>
						<view class="value">{{fmt(info.subTeacherName)}}</view>
					</view>
					<view class="item">
						<view class="name">联系电话</view>
						<view class="value phone" v-if="info.subTeacherPhone" @click="copyPhone(info.subTeacherPhone)">
							{{info.subTeacherPhone}}
							<image class="copy-icon" src="/static/images/common/copy.png" mode=""></image>
						</view>
						<view class="value" v-else>—</view>
					</view>
				</template>
			</view>
		</view>
	</view>
</template>

<script setup>
	import api from '@/api/index.js'
	import {
		ref,
		reactive
	} from 'vue'
	import {
		onLoad
	} from '@dcloudio/uni-app'
	import {
		toast
	} from "@/utils/common.js"

	/* 页面加载 */
	onLoad(() => {
		getClassInfo()
	})

	/* 获取班级信息 */
	let info = reactive({})

	function getClassInfo() {
		api.my._getClassInfo().then(res => {
			if (res.code == 200) {
				Object.assign(info, res.data)
			}

		}).catch(() => {})
	}

	function copyPhone(val) {
		uni.setClipboardData({
			data: val,
			success: function() {
				toast('复制成功！')
			}
		});
	}

	/* 展示层：缺值占位，避免空白行；不改动任何取数逻辑 */
	function fmt(v) {
		if (v === null || v === undefined || v === '') return '—'
		return v
	}
</script>

<style scoped lang="scss">
	.container {
		min-height: 100vh;
		background: $page-bg;
		padding: 24rpx $gutter 40rpx;

		.card {
			background: $card-bg;
			border-radius: $radius-card;
			padding: $card-padding;
		}

		.shadow-box {
			margin-bottom: 24rpx;

			&:last-child {
				margin-bottom: 0;
			}

			.title {
				font-size: $f30;
				color: $color1;
				font-weight: bold;
				line-height: 42rpx;
				display: flex;
				align-items: center;

				.line {
					height: 28rpx;
					width: 8rpx;
					border-radius: 4rpx;
					background: $theme-color;
					margin-right: 12rpx;
				}
			}

			.content {
				margin-top: 20rpx;
				padding-top: 4rpx;
				border-top: 1rpx solid $line-color;

				.item {
					display: flex;
					align-items: flex-start;
					justify-content: space-between;
					padding: 16rpx 0;

					.name {
						width: 160rpx;
						flex-shrink: 0;
						font-size: $f26;
						line-height: 40rpx;
						color: $color6;
					}

					.value {
						flex: 1;
						min-width: 0;
						font-size: $f28;
						line-height: 40rpx;
						color: $color1;
						text-align: right;
						word-break: break-all;

						&.phone {
							color: $theme-color;
						}

						.copy-icon {
							margin-left: 8rpx;
							width: 24rpx;
							height: 24rpx;
						}
					}
				}
			}
		}
	}
</style>
