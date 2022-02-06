import request from '@/router/axios'

export function fetchMemberLevelList(query) {
  return request({
    url: '/member/memberLevel/page',
    method: 'get',
    params: query
  })
}

export function getMemberLevelSearch(query) {
    return request({
      url: '/member/memberLevel/page/search',
      method: 'get',
      params: query
    })
  }

export function addMemberLevel(obj) {
  return request({
    url: '/member/memberLevel',
    method: 'post',
    data: obj
  })
}

export function getMemberLevel(id) {
  return request({
    url: '/member/memberLevel/' + id,
    method: 'get'
  })
}

export function delMemberLevel(id) {
  return request({
    url: '/member/memberLevel/' + id,
    method: 'delete'
  })
}

export function delBatchLevel(ids) {
    return request({
      url: '/member/memberLevel/deleteBatch',
      method: 'delete',
      data: ids
    })
}

export function putMemberLevel(obj) {
  return request({
    url: '/member/memberLevel',
    method: 'put',
    data: obj
  })
}
