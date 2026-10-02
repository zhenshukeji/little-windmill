import {
	request
} from '@/utils/request.js'

export default {
	/**
	 * 获取我的资料
	 */
	_getMyInfo(data) {
		return request('/my/info', data)
	},
	/**
	 * 修改我的资料
	 */
	_putMyInfo(data) {
		return request('/my/info', data,'put')
	},
	/**
	 * 获取我的班级
	 */
	_getClassInfo(data) {
		return request("/my/class", data)
	},
	

	
	
	
}
