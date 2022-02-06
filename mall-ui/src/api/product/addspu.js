import request from '@/router/axios'

export function getSaleAttrList(obj,catelogId) {
    return request({
      url: '/product/attr/sale/list/'+ catelogId,
      method: 'get',
      params: obj
    })
}

export function fetchMemberLevelList(query) {
    return request({
      url: '/member/memberLevel/page',
      method: 'get',
      params: query
    })
}

export function getAttrGroupWithAttrs(catelogId) {
    return request({
      url: '/product/attrGroup/'+catelogId+'/withattr',
      method: 'get'
    })
}

export function addSpu(obj) {
    return request({
      url: '/product/spuinfo',
      method: 'post',
      data: obj
    })
  }
