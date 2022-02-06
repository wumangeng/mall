
import request from '@/router/axios'

export function fetchDetailList(query) {
  return request({
    url: '/ware/purchaseDetail/page',
    method: 'get',
    params: query
  })
}

export function searchDetailList(query) {
    return request({
      url: '/ware/purchaseDetail/searchPage',
      method: 'get',
      params: query
    })
}

export function addDetail(obj) {
  return request({
    url: '/ware/purchaseDetail',
    method: 'post',
    data: obj
  })
}

export function getDetail(id) {
  return request({
    url: '/ware/purchaseDetail/' + id,
    method: 'get'
  })
}

export function delDetail(id) {
  return request({
    url: '/ware/purchaseDetail/' + id,
    method: 'delete'
  })
}

export function delDetailBatch(ids) {
    return request({
      url: '/ware/purchaseDetail/deleteBatch',
      method: 'delete',
      data: ids
    })
  }

export function putDetail(obj) {
  return request({
    url: '/ware/purchaseDetail',
    method: 'put',
    data: obj
  })
}
