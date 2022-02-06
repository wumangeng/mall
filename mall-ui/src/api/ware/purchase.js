
import request from '@/router/axios'

export function donePurchase(obj) {
    return request({
      url: '/ware/purchase/donePurchase',
      method: 'post',
      data: obj
    })
}

export function receive(obj) {
    return request({
      url: '/ware/purchase/receive',
      method: 'post',
      data: obj
    })
}

export function unreceive(obj) {
    return request({
      url: '/ware/purchase/unreceive',
      method: 'get',
      params: obj
    })
}

export function merge(obj) {
    return request({
      url: '/ware/purchase/merge',
      method: 'post',
      data: obj
    })
}

export function fetchList(query) {
  return request({
    url: '/ware/purchase/page',
    method: 'get',
    params: query
  })
}

export function getUserList() {
    return request({
      url: '/admin/user/list',
      method: 'get'
    })
  }

export function addPurchase(obj) {
  return request({
    url: '/ware/purchase',
    method: 'post',
    data: obj
  })
}

export function getPurchase(id) {
  return request({
    url: '/ware/purchase/' + id,
    method: 'get'
  })
}

export function delPurchase(id) {
  return request({
    url: '/ware/purchase/' + id,
    method: 'delete'
  })
}

export function delObjBatch(ids) {
    return request({
      url: '/ware/purchase/deleteBatch',
      method: 'delete',
      data: ids
    })
}

export function putPurchase(obj) {
  return request({
    url: '/ware/purchase',
    method: 'put',
    data: obj
  })
}
