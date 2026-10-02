import request from '@/utils/request'

// 获取教学计划 列表
export function list(query) {
  return request({
    url: '/kg/work/educational/program/list',
    method: 'get',
    params: query
  })
}

// 获取班级
export function classroom() {
  return request({
    url: '/kg/work/educational/program/classroom',
    method: 'get'
  })
}

// 添加计划
export function addProgram(data) {
  return request({
    url: '/kg/work/educational/program',
    method: 'POST',
    data: data
  })
}


// 删除
export function delProgram(data) {
  return request({
    url: '/kg/work/educational/program',
    method: 'DELETE',
    data: data
  })
}
