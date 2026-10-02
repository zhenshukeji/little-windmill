<template>
	<view class="container">
		// #ifdef MP-WEIXIN
		<heads :showBack="false" titleText="我的"></heads>
		// #endif

		<view class="content">
			<!-- 幼儿信息卡 -->
			<view class="profile">
				<image class="avatar" v-if="studentInfo.imgUrl" :src="studentInfo.imgUrl" mode=""></image>
				<image class="avatar" v-else src="/static/images/common/avatar.png" mode=""></image>
				<view class="info">
					<view class="name">
						{{studentInfo.studentName}}
					</view>
					<view class="class">
						{{studentInfo.className}}
					</view>
				</view>
				<view class="switch" @click="showActionSheet=true">切换 ⇄</view>
			</view>

			<!-- 我的服务 -->
			<view class="list-wrap">
				<view class="list-item" @click="toMyInfo">
					<image class="icon" src="/static/images/my/icon1.png"></image>
					<view class="txt">
						我的资料
					</view>
					<image class="arrow" src="/static/images/common/arrow.png"></image>
				</view>
				<view class="list-item" @click="toMyClass">
					<image class="icon" src="/static/images/my/icon2.png"></image>
					<view class="txt">
						班级信息
					</view>
					<image class="arrow" src="/static/images/common/arrow.png"></image>
				</view>
			<!-- 	<view class="list-item" @click="showActionSheet=true">
					<image class="icon" src="/static/images/my/icon4.png"></image>
					<view class="txt">
						切换账号
					</view>
					<image class="arrow" src="/static/images/common/arrow.png"></image>
				</view> -->
			</view>

			<!-- 退出登录（既有功能，保留） -->
			<view class="logout" @click="logout()">
				退出登录
			</view>

			<!-- 品牌陪伴语 -->
			<view class="care">风车智慧幼教 · 陪伴成长，连接家园</view>
		</view>

		<!-- 登录弹窗 -->
		<tui-actionsheet :show="showActionSheet" :item-list="studentList" tips="请选择一个儿童登录" @click="chooseStudent"
			@cancel="showActionSheet =false">
		</tui-actionsheet>

	</view>
</template>

<script setup>
	import api from '@/api/index.js'
	import {
		ref,
		reactive,
	} from 'vue'
	import {
		onLoad
	} from '@dcloudio/uni-app'

	// 学生列表
	let studentList = uni.getStorageSync('studentList')
	// 显示学生列表
	let showActionSheet = ref(false)

	/* 页面加载 */
	let studentInfo = reactive({})
	onLoad(() => {
		Object.assign(studentInfo,uni.getStorageSync('studentInfo'))
	})

	// 选择学生
	function chooseStudent(item) {
		api.login.studentLogin({
			studentId: item.studentId
		}).then(res => {
			showActionSheet.value = false
			if (res.code === 200) {
				 getMy()
				 uni.reLaunch({
				 	url: '/pages/tabbar/my/my'
				 });
			}
		}).catch(() => {})
	}

function logout() {
	uni.showModal({
		title: '提示',
		content: '是否退出登录？',
		success: function (res) {
			if (res.confirm) {
				uni.clearStorage()
				uni.reLaunch({
					url: '/pages/login/login'
				});
			}
		}
	})
}

	/* 我的信息 */
	function getMy(){
		api.home._getMy().then(res => {
			if(res.code==200){
				// 在缓存中存入学生信息
				uni.setStorageSync('studentInfo', res.data)
				Object.assign(studentInfo,uni.getStorageSync('studentInfo'))
			}

		}).catch(() => {})
	}
	/* 跳转到我的资料 */
	function toMyInfo(){
		uni.navigateTo({
			url:'/pages/my/my-info/my-info'
		})
	}
	/* 跳转到我的班级 */
	function toMyClass(){
		uni.navigateTo({
			url:'/pages/my/my-class/my-class'
		})
	}
</script>

<style scoped lang="scss">
	.content {
		min-height: 100vh;
		background: $page-bg;
		padding: 24rpx 32rpx 60rpx;
	}

	// 幼儿信息卡
	.profile {
		display: flex;
		align-items: center;
		background: $theme-light;
		border-radius: $radius-card;
		padding: 32rpx $card-padding;

		.avatar {
			width: 112rpx;
			height: 112rpx;
			border-radius: 50%;
			flex-shrink: 0;
			background: #FFFFFF;
		}

		.info {
			flex: 1;
			margin: 0 20rpx;
			overflow: hidden;

			.name {
				font-size: 36rpx;
				font-weight: bold;
				color: $color1;
				white-space: nowrap;
				overflow: hidden;
				text-overflow: ellipsis;
			}

			.class {
				margin-top: 10rpx;
				font-size: 24rpx;
				color: $color6;
				white-space: nowrap;
				overflow: hidden;
				text-overflow: ellipsis;
			}
		}

		.switch {
			flex-shrink: 0;
			padding: 12rpx 22rpx;
			font-size: 24rpx;
			color: $theme-color;
			background: rgba(255, 255, 255, 0.72);
			border-radius: $radius-tag;
		}
	}

	// 我的服务
	.list-wrap {
		margin-top: 32rpx;
		background: $card-bg;
		border-radius: $radius-card;
		padding: 0 $card-padding;

		.list-item {
			display: flex;
			align-items: center;
			padding: 28rpx 0;
			border-bottom: 1rpx solid $line-color;

			&:last-child {
				border-bottom: 0;
			}

			.icon {
				width: 64rpx;
				height: 64rpx;
				flex-shrink: 0;
			}

			.txt {
				flex: 1;
				margin: 0 20rpx;
				font-size: 28rpx;
				color: $color1;
			}

			.arrow {
				width: 32rpx;
				height: 32rpx;
				flex-shrink: 0;
			}
		}
	}

	// 退出登录
	.logout {
		margin-top: 32rpx;
		height: $btn-height;
		line-height: $btn-height;
		text-align: center;
		font-size: 28rpx;
		color: $color6;
		background: $card-bg;
		border-radius: $radius-card;
	}

	.care {
		margin-top: 48rpx;
		text-align: center;
		font-size: 24rpx;
		color: $color6;
	}
</style>
