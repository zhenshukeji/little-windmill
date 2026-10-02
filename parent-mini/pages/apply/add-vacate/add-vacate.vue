<template>
	<view class="container">
		// #ifdef MP-WEIXIN
		<heads titleText="请假申请"></heads>
		// #endif
		<!-- 学生信息 -->
		<view class="student">
			<image v-if="studentInfo.imgUrl" :src="studentInfo.imgUrl" class="avatar"></image>
			<image v-else src="/static/images/common/avatar.png" class="avatar"></image>
			<view class="name">
				{{ studentInfo.studentName }}
			</view>
		</view>
		<!-- 表单信息 -->
		<view class="form-wrap">
			<form @submit="formSubmit">
				<view class="card select-box">
					<view class="form-item">
						<view class="title">请假类型</view>
						<view class="radios">
							<radio-group @change="radioChange">
								<label class="radio">
									<radio color="#27846F" :value="0" />病假
								</label>
								<label class="radio">
									<radio color="#27846F" :value="1" />事假
								</label>
							</radio-group>
						</view>
					</view>
					<view class="form-item">
						<view class="title">开始时间</view>
						<view class="pick" @click="selectTime(1)">
							<text :class="{ placeholder: !beginTime }">{{ beginTime || '请选择' }}</text>
							<image src="/static/images/apply/arrow.png"></image>
						</view>
					</view>
					<view class="form-item">
						<view class="title">结束时间</view>
						<view class="pick" @click="selectTime(2)">
							<text :class="{ placeholder: !endTime }">{{ endTime || '请选择' }}</text>
							<image src="/static/images/apply/arrow.png"></image>
						</view>
					</view>
				</view>
				<view class="card textarea-card">
					<view class="group-title">
						<view class="line"></view>
						请假事由
					</view>
					<view class="textarea-box" scroll-y="true">
						<textarea @blur="bindTextAreaBlur" focus maxlength="200" placeholder-style="color:#65766C"
							placeholder="请输入请假事由（必填）" />
					</view>
				</view>
				<view class="submit-bar">
					<button class="submit-btn" form-type="submit">提交申请</button>
				</view>
			</form>
		</view>

		<!-- 日期选择器 -->
		<!-- setDateTime="2022-09-08 12:30" 可以设置默认时间-->
		<k-date-picker :isShow="showPicker" @confirm="confirm" @cancel="showPicker = false">
		</k-date-picker>



	</view>
</template>

<script setup>
import api from '@/api/index.js'
import {
	ref,
	reactive,
	onMounted,
} from 'vue'
import {
	onLoad
} from '@dcloudio/uni-app'
import { empty } from '@/utils/validation.js'
import { toast } from '@/utils/common.js'


let showPicker = ref(false)
let pickerType = ref(1)
let beginTime = ref()
let endTime = ref()
let vacateType = ref()
let vacateReason = ref()

/* 页面加载 */
let studentInfo = reactive({})
onLoad(() => {
	Object.assign(studentInfo, uni.getStorageSync('studentInfo'))
})

const dateTime = ref() //日期选择器
onMounted(() => {
	if (dateTime.value) {
		dateTime.value.show()
	}

})

function radioChange(e) {
	vacateType.value = Number(e.detail.value)
}

function bindTextAreaBlur(e) {
	// console.log('失去焦点',e)
	vacateReason.value = e.detail.value
}

function confirm(e) {
	console.log('confirm!!!', e);
	if (pickerType.value == 1) {
		beginTime.value = e.result
	}
	if (pickerType.value == 2) {
		endTime.value = e.result
	}
	showPicker.value = false
}
//1-开始 2-结束
function selectTime(index) {
	showPicker.value = true
	pickerType.value = index

}

// 表单提交事件
function formSubmit() {
	if (empty(vacateType.value)) {
		toast('请填写请假类型！')
		return
	}
	if (empty(beginTime.value)) {
		toast('请选择开始时间！')
		return
	}
	if (empty(endTime.value)) {
		toast('请选择结束时间！')
		return
	}
	if (empty(vacateReason.value)) {
		toast('请填写请假事由！')
		return
	}

	console.log(9999999, vacateType.value, typeof vacateType.value);
	addApply()
}

// 新建请假申请
function addApply() {
	console.log('新建请假申请');
	const params = {
		beginTime: beginTime.value,
		endTime: endTime.value,
		vacateReason: vacateReason.value,
		vacateType: vacateType.value
	}
	api.apply._addVacateApply(params).then(res => {
		console.log(7777, res);
		if (res.code == 200) {
			setTimeout(() => {
				uni.navigateBack({
					delta: 1,
					animationType: 'pop-out',
					animationDuration: 200,
					success() {
					},
					fail() {
						uni.switchTab({
							url: '/pages/tabbar/home/home'
						});
					}
				});
			}, 2000)
		}
	}).catch(() => {})
}

// api.home.getBacklog().then(res => {
// 	if (res.code == 200) {

// 	}

// })
</script>

<style scoped lang="scss">
.container {
	min-height: 100vh;
	background: $page-bg;
	padding: 24rpx 32rpx 200rpx;

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
}

.student {
	margin-bottom: 24rpx;
	display: flex;
	align-items: center;
	justify-content: center;
	flex-direction: column;

	.avatar {
		width: 130rpx;
		height: 130rpx;
		border-radius: 50%;
		border: 4rpx solid $theme-light;
	}

	.name {
		font-size: $f28;
		line-height: 1.5;
		color: $color1;
		margin-top: 16rpx;
		word-break: break-all;
	}
}

.form-wrap {
	.select-box {
		.form-item {
			display: flex;
			align-items: center;
			justify-content: space-between;
			min-height: 88rpx;
			font-size: $f28;
			color: $color6;
			border-bottom: 1rpx solid $line-color;

			&:last-child {
				border-bottom: 0;
			}

			.title {
				flex-shrink: 0;
				margin-right: 24rpx;
				font-size: $f28;
				line-height: 1.5;
				color: $color6;
			}

			.radios {
				color: $color1;

				label:first-child {
					margin-right: 50rpx;
				}

				radio {
					transform: scale(0.7)
				}
			}

			.pick {
				display: flex;
				align-items: center;
				flex: 1;
				min-width: 0;
				justify-content: flex-end;
				font-size: $f28;
				line-height: 1.5;
				color: $color1;
				text-align: right;

				text {
					flex: 1;
					min-width: 0;
					word-break: break-all;

					&.placeholder {
						color: $color6;
					}
				}

				image {
					width: 30rpx;
					height: 30rpx;
					margin-left: 8rpx;
					flex-shrink: 0;
				}
			}
		}
	}

	.textarea-card {
		margin-top: 24rpx;

		.textarea-box {
			margin-top: 20rpx;
			height: 280rpx;

			textarea {
				width: 100%;
				height: 100%;
				font-size: $f28;
				line-height: 1.6;
				color: $color1;
			}
		}
	}

	.submit-bar {
		position: fixed;
		left: 32rpx;
		right: 32rpx;
		bottom: 40rpx;
		// 有安全区的机型优先用安全区，避免被 Home 条压住
		bottom: calc(24rpx + env(safe-area-inset-bottom));
		z-index: 5;

		.submit-btn {
			width: 100%;
			height: $btn-height;
			line-height: $btn-height;
			text-align: center;
			background: $theme-color;
			color: $color2;
			font-size: $f30;
			font-weight: bold;
			border-radius: $radius-btn;
			border: 0;
			box-shadow: 0 8rpx 20rpx rgba(39, 132, 111, 0.18);
		}
	}

}



//picker
</style>
