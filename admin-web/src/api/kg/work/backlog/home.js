import request from '@/utils/request'

// 获取首页数据概况
export function dataSummary() {
  return request({
    url: '/kg/home',
    method: 'get'
  })
}

// 获取班级
export function getClassroom() {
  return request({
    url: '/kg/home/class/list',
    method: 'get'
  })
}

// 获取班级考勤数据
export function getClassChecking(id) {
  return request({
    url: '/kg/home/' + id,
    method: 'get'
  })
}
