import {
	request
} from '@/utils/request.js'

export default {
	/*
	 获取微信手机号登录
	*/
	wxPhoneLogin(data) {
		return request('/wx/auth/loginByWx', data, 'post')
	},
	/*
		 发送验证码
		*/
	sendCaptchaCode(data) {
		return request('/wx/auth/captcha', data, 'post')
	},
	/*
		 学生登录
		*/
	studentLogin(data) {
		return request('/wx/auth/change', data, 'post')
	},
	/*
	 手机号登录
	*/
	phoneLogin(data) {
		return request('/wx/auth/loginByPhone', data, 'post')
	},
	
}
