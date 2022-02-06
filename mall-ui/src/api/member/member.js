import request from '@/router/axios'

export function fetchMemberList(query) {
  return request({
    url: '/member/member/page',
    method: 'get',
    params: query
  })
}

export function getMemberSearch(query) {
    return request({
      url: '/member/member/page/search',
      method: 'get',
      params: query
    })
  }

export function addMember(obj) {
  return request({
    url: '/member/member',
    method: 'post',
    data: obj
  })
}

export function getMember(id) {
  return request({
    url: '/member/member/' + id,
    method: 'get'
  })
}

export function delMember(id) {
  return request({
    url: '/member/member/' + id,
    method: 'delete'
  })
}

export function putMember(obj) {
  return request({
    url: '/member/member',
    method: 'put',
    data: obj
  })
}

export function upload(obj) {
    const form =new FormData();
    form.append("file",obj);
    return request({
      url: '/resources/upload/uploadMemberHeader',
      method: 'post',
      data: form
    })
}
