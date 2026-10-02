import {
	request
} from '@/utils/request.js'

export default {
	/**
	 * 获取请假申请列表
	 */
	_getVacateList(data) {
		return request('/vacate/apply/list', data, 'post')
	},

	/**
	 * 新建请假申请
	 */
	_addVacateApply(data) {
		return request("/vacate/apply", data, 'post')
	},

	/**
	 * 获取请假申请详情
	 */
	_getVacateDetail(data) {
		return request("/vacate/apply/"+data)
	},
}
