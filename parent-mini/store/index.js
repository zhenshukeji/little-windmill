import {
	createStore
} from 'vuex'
import api from '@/api/home/index.js'
import {
	request
} from '@/utils/request.js'
const store = createStore({
	state: {
		hasLogin: false,
		userInfo: {},
		openid: ''
	},
	mutations: {
		// 登录
		login(state) {
			state.hasLogin = true;
		},
		// 登出
		logout(state) {
			state.hasLogin = false
			state.openid = null
		},
		// 获取openid
		SET_OPENID(state, value) {
			state.openid = value;
		},
		// 获取用户信息
		SET_USER_DATA(state, value) {
			state.userInfo = value;
		},

	},

	actions: {
		// 获取openid
		getUserOpenId: async function({
			commit,
			state
		}) {
			return await new Promise((resolve, reject) => {
				if (state.openid) {
					resolve(state.openid)
				} else {
					uni.login({
						success(res) {
							if (res.code) {
								api.getOpenid({
									code: res.code
								}).then(res => {
									commit('SET_OPENID', res.data)
									resolve(res.data)
								}).catch(err => {
									reject(err)
								})
							} else {
								reject('登录失败：' + res.errMsg)
							}
						},
						fail(err) {
							reject(err)
						}
					})
				}
			})
		},
	}
})

export default store
