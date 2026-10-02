<template>
	<view class="container">
		<!-- 品牌区：风车标志 + 产品名（§3 登录页标题） -->
		<view class="head">
			<image class="logo" src="/static/images/logo-windmill.png" mode="aspectFit"></image>
			<view class="head_title">风车智慧幼教</view>
			<view class="head_desc">陪伴成长，连接家园</view>
		</view>

		<view class="form_box">
			<view class="form_item">
				<input v-model="mobile" type="tel" maxlength="11" placeholder="请输入手机号" class="mobile"
					placeholder-style="color:#65766C" />
			</view>
			<view class="form_item">
				<input v-model="code" maxlength="6" placeholder="请输入验证码" class="code"
					placeholder-style="color:#65766C" />
				<view class="code_btn" :class="{ disabled: !canSend }" @tap="handleSendCode">
					<text v-if="!counting">获取验证码</text>
					<text v-else class="wait">{{ timeDown }}s 后重发</text>
				</view>
			</view>

		</view>
		<!-- 底部按钮 -->
		<view class="login_btn" @tap="phoneLogin">登录</view>
		<!-- 微信登录 -->
		<button open-type="getPhoneNumber" @getphonenumber="getPhoneNumber" class="wx_login_btn">
			<image src="/static/images/wechat.png" class="icon" />
			<text>微信手机号登录</text>
		</button>

		<tui-actionsheet :show="showActionSheet" :item-list="studentList" tips="请选择一个儿童登录" @click="chooseStudent"
			@cancel="showActionSheet =false">
		</tui-actionsheet>
	</view>
</template>

<script setup>
	import {
		ref,
		reactive,
		computed,
		onUnmounted
	} from 'vue'
	import api from '@/api/index.js'
	import check from '@/utils/check.js'
	import utils from '@/utils/util.js'
	// 账号登录
	let accountLogin = ref(false)
	// 手机号
	let mobile = ref('')

	// 验证码
	let code = ref('')
	// 倒计时中（true = 按钮显示剩余秒数且不可点）
	let counting = ref(false)
	// 倒计时剩余秒数
	let timeDown = ref(60)
	// 倒计时定时器
	let timer = null
	// 学生列表
	let studentList = reactive([])
	// 显示学生列表
	let showActionSheet = ref(false)

	// 手机号合法且不在倒计时中才允许发送
	const canSend = computed(() => check.isValidPhone(mobile.value) && !counting.value)

	// 启动 60s 倒计时；重复调用会先清掉旧定时器，避免叠加
	function startCountdown() {
		counting.value = true
		timeDown.value = 60
		if (timer) clearInterval(timer)
		timer = setInterval(() => {
			timeDown.value--
			if (timeDown.value <= 0) {
				counting.value = false
				timeDown.value = 60
				clearInterval(timer)
				timer = null
			}
		}, 1000)
	}

	// 停止倒计时并复位（登录态变化 / 页面卸载时调用）
	function resetCountdown() {
		if (timer) {
			clearInterval(timer)
			timer = null
		}
		counting.value = false
		timeDown.value = 60
	}

	onUnmounted(resetCountdown)

	// 获取绑定手机号码
	async function getPhoneNumber(e) {
		console.log('调用', e)
		try {
			if (e.detail.errMsg !== "getPhoneNumber:ok") {
				accountLogin.value = true
				mobile.value = ''
				code.value = ''
				resetCountdown()
				return;
			}
			// 登录接口
			let loginRes = await wx.login()
			let res = await api.login.wxPhoneLogin({
				openidCode: loginRes.code,
				phoneCode: e.detail.code
			})
			if (res.code === 200) {
				studentList.length = 0
				res.data.studentList.forEach(item => {
					studentList.push({
						...item,
						text: item.studentName
					})
				})
				showActionSheet.value = true
				// 在缓存中存入token
				uni.setStorageSync('token', res.data.token)
			}

		} catch (error) {

		}
	}

	// 手机号验证码登录
	async function phoneLogin() {
		if (!mobile.value) {
			utils.Toast('手机号不能为空')
			return false;
		}
		if (!code.value) {
			utils.Toast('验证码不能为空')
			return false;
		}
		let loginRes = await wx.login()
		api.login.phoneLogin({
			openidCode: loginRes.code,
			phoneNum: mobile.value,
			verificationCode: code.value
		}).then(res => {
			if (res.code === 200) {
				studentList.length = 0
				res.data.studentList.forEach(item => {
					studentList.push({
						...item,
						text: item.studentName
					})
				})
				showActionSheet.value = true
				// 在缓存中存入token
				uni.setStorageSync('token', res.data.token)
				// studentList存入缓存
				uni.setStorageSync('studentList', studentList)
			}
		}).catch(() => {})
	}

	// 发送验证码
	async function handleSendCode() {
		// 倒计时中：忽略点击，防止重复发送
		if (counting.value) {
			return false;
		}
		if (!mobile.value) {
			utils.Toast('手机号不能为空')
			return false;
		}

		if (!check.isValidPhone(mobile.value)) {
			utils.Toast('请输入正确的手机号')
			return false;
		}
		// 获取验证码
		api.login.sendCaptchaCode({
			phoneNum: mobile.value
		}).then(res => {
			if (res.code === 200) {
				utils.Toast('已发送')
				startCountdown()
			}
		}).catch(() => {})
	}

	// 选择学生
	function chooseStudent(item) {
		api.login.studentLogin({
			studentId: item.studentId
		}).then(res => {
			if (res.code === 200) {
				uni.switchTab({
					url: '/pages/tabbar/home/home'
				})
			}
		}).catch(() => {})
	}
</script>

<style lang="scss">
	.container {
		box-sizing: border-box;
		min-height: 100vh;
		padding: 0 $gutter;
		background-color: $page-bg;
	}

	.head {
		padding-top: 140rpx;
		text-align: center;

		.logo {
			width: 168rpx;
			height: 168rpx;
		}

		.head_title {
			margin-top: 28rpx;
			font-size: $f36;
			font-weight: bold;
			color: $color1;
		}

		.head_desc {
			margin-top: 14rpx;
			font-size: $f26;
			color: $color6;
		}
	}

	.form_box {
		width: 100%;
		margin-top: 72rpx;
		padding: $card-padding;
		box-sizing: border-box;
		background: $card-bg;
		border-radius: $radius-card;

		.form_item {
			position: relative;
			display: flex;
			align-items: center;
			padding: 24rpx 0;
			border-bottom: 1rpx solid $line-color;

			&:last-child {
				border-bottom: 0;
				padding-bottom: 8rpx;
			}

			.mobile,
			.code {
				font-size: $f28;
				color: $color1;
			}

			.mobile {
				width: 100%;
			}

			.code {
				flex: 1;
				min-width: 0;
				padding-right: 200rpx;
			}

			.code_btn {
				position: absolute;
				right: 0;
				top: 50%;
				transform: translate(0, -50%);
				z-index: 2;
				font-size: $f28;
				color: $theme-color;

				// 手机号未填写或不合法 / 倒计时中：降为次要色，仍可点击以给出提示
				&.disabled {
					color: $color6;
				}
			}

			.wait {
				color: $color6;
			}
		}
	}

	// 主按钮：手机号验证码登录
	.login_btn {
		width: 100%;
		height: $btn-height;
		line-height: $btn-height;
		margin-top: 64rpx;
		text-align: center;
		font-size: 32rpx;
		font-weight: bold;
		color: #ffffff;
		background-color: $theme-color;
		border-radius: $radius-btn;
	}

	// 次按钮：微信手机号登录（浅绿底 + 主色文字，层级低于主按钮）
	.wx_login_btn {
		display: flex;
		align-items: center;
		justify-content: center;
		width: 100%;
		height: $btn-height;
		line-height: $btn-height;
		margin-top: 24rpx;
		padding: 0;
		text-align: center;
		font-size: $f28;
		color: $theme-color;
		background-color: $theme-lighter;
		border: none;
		border-radius: $radius-btn;

		.icon {
			margin-right: 16rpx;
			width: 44rpx;
			height: 44rpx;
		}
	}

	button::after {
		border: none;
	}
</style>
