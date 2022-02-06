import request from '@/router/axios'

export function searchList(query) {
  return request({
    url: '/ware/wareInfo/list',
    method: 'get',
    params: query
  })
}

export function getAll() {
    return request({
      url: '/ware/wareInfo/listAll',
      method: 'get'
    })
  }

export function addObj(obj) {
  return request({
    url: '/ware/wareInfo',
    method: 'post',
    data: obj
  })
}

export function getObj(id) {
  return request({
    url: '/ware/wareInfo/' + id,
    method: 'get'
  })
}

export function delObj(id) {
  return request({
    url: '/ware/wareInfo/' + id,
    method: 'delete'
  })
}
export function delObjBatch(ids) {
    return request({
      url: '/ware/wareInfo/deleteBatch',
      method: 'delete',
      data: ids
    })
}

export function putObj(obj) {
  return request({
    url: '/ware/wareInfo',
    method: 'put',
    data: obj
  })
}
