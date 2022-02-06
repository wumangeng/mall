import request from '@/router/axios'

export function queryList(obj) {
  return request({
    url: '/product/attrGroup/search',
    method: 'get',
    params: obj
  })
}

export function addObj(obj) {
  return request({
    url: '/product/attrGroup',
    method: 'post',
    data: obj
  })
}

export function getAttrGroupById(id) {
  return request({
    url: '/product/attrGroup/' + id,
    method: 'get'
  })
}

export function getAttrGroupList(catelogId) {
    return request({
      url: '/product/attrGroup/getAttrGroupByCatelogId/' + catelogId,
      method: 'get'
    })
  }

export function delObj(id) {
  return request({
    url: '/product/attrGroup/' + id,
    method: 'delete'
  })
}

export function putObj(obj) {
  return request({
    url: '/product/attrGroup',
    method: 'put',
    data: obj
  })
}


export function getAttrGroupWithAttrs(catelogId) {
    return request({
      url: '/product/attrGroup/'+catelogId+'/withattr',
      method: 'get'
    })
}


