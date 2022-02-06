import request from '@/router/axios'

export function fetchWareSkuList(query) {
  return request({
    url: '/ware/wareSku/page',
    method: 'get',
    params: query
  })
}

export function searchWareSkuList(query) {
    return request({
      url: '/ware/wareSku/list',
      method: 'get',
      params: query
    })
}

export function addWareSku(obj) {
  return request({
    url: '/ware/wareSku',
    method: 'post',
    data: obj
  })
}

export function getWareSku(id) {
  return request({
    url: '/ware/wareSku/' + id,
    method: 'get'
  })
}

export function delWareSku(id) {
  return request({
    url: '/ware/wareSku/' + id,
    method: 'delete'
  })
}

export function delBatchWareSku(ids) {
    return request({
      url: '/ware/wareSku/delete',
      method: 'delete',
      data: ids
    })
  }

export function putWareSku(obj) {
  return request({
    url: '/ware/wareSku',
    method: 'put',
    data: obj
  })
}
