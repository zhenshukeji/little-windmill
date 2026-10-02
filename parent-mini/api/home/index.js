import {
	request
} from '@/utils/request.js'

export default {
	getOpenid(data) {
		return request('/wx/auth/loginByWx', data, 'post')
	},
	
	/**
	 * 获取我的
	 */
	_getMy(data) {
		return request("/my", data)
	},
	
	/**
	 * 获取banner轮播图地址
	 */
	_getBanner(data) {
		return request("/wx/banner", {
			params: data
		})
	},
	/**
	 * 首页待办事项
	/**
	 * 获取班级圈列表
	 */
	getCircle(data) {
		return request("/wx/circle", {
			params: data
		})
	},
	/**
	 * 获取学校概况
	 */
	getSurvey(data) {
		return request("/wx/survey", {
			params: data
		})
	},
	/**
	 * 获取出勤列表
	/**
	 * 根据日期获取学生出勤详情
	/**
	 * 获取公共教育列表
	 */
	/**
	 * 获取微课件列表
	 */
	/**
	 * 获取作业列表
	 */
	/**
	 * 通过id获取作业详情
	 */
	
	/**
	 * 获取教学计划列表
	 */
	_getProgramList(data) {
		return request("/wx/program/list",data)
	},
	/**
	 * 获取教学计划详情
	 */
	_getProgramDetail(data) {
		return request("/wx/program/listDetail/"+data)
	},
	/**
	 * 获取通讯录列表
	 */
	/**
	 * 获取园长信箱信息
	 */
	/**
	 * 获取园长信箱详情
	 */
	/**
	 * 新增园长信箱
	 */
}
