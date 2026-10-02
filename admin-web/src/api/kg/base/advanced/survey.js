import request from '@/utils/request'

// 查询园区概况 列表
export function listSurvey(query) {
  return request({
    url: '/kg/base/advanced/survey',
    method: 'get',
    params: query
  })
}

// 修改园区概况
export function updateSurvey(data) {
  return request({
    url: '/kg/base/advanced/survey',
    method: 'put',
    data: data
  })
}

