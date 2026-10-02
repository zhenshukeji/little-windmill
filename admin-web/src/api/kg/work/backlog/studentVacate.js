import request from '@/utils/request'

// 儿童列表
export function studentList(query) {
  return request({
    url: '/kg/work/backlog/studentVacate/pending/list',
    method: 'get',
    params: query
  })
}

// 学生请假详情
export function vacateDetail(id) {
  return request({
    url: '/kg/work/backlog/studentVacate/' + id,
    method: 'get',
  })
}

// 学生请假申请同意或不同意
export function vacateApprove(query) {
  return request({
    url: '/kg/work/backlog/studentVacate',
    method: 'put',
    data: query
  })
}

// 申请历史
export function historyList(query) {
  return request({
    url: '/kg/work/backlog/studentVacate/history/list',
    method: 'get',
    params: query
  })
}

