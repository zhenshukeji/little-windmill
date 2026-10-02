import request from '@/utils/request'

// 列表分页查询班级圈
export function list(query) {
  return request({
    url: '/kg/work/educational/circle/list',
    method: 'get',
    params: query
  })
}


// 发布班级圈动态
export function addPublish(data) {
  return request({
    url: '/kg/work/educational/circle/publish',
    method: 'POST',
    data: data
  })
}

// 发布班级圈动态
export function delPublish(data) {
  return request({
    url: '/kg/work/educational/circle',
    method: 'delete',
    data: data
  })
}
