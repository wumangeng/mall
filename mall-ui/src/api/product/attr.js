
import request from '@/router/axios'

export function fetchList(query) {
  return request({
    url: '/product/attr/page',
    method: 'get',
    params: query
  })
}

export function getListByCatelogId(obj) {
    return request({
      url: '/product/attr/pageByCatelogId',
      method: 'post',
      data: obj
    })
}

export function searchList(obj) {
    return request({
      url: '/product/attr/page/search',
      method: 'post',
      data: obj
    })
}


export function getBSAttrList(bs,catelogId,obj) {
    return request({
      url: '/product/attr/'+bs+'/list/'+ catelogId,
      method: 'get',
      params: obj
    })
}



export function addObj(obj) {
  return request({
    url: '/product/attr',
    method: 'post',
    data: obj
  })
}

export function getAttrByAttrGroupId(attrGroupId) {
  return request({
    url: '/product/attr/getAttrByAttrGroupId/' + attrGroupId,
    method: 'get'
  })
}

export function getAttrById(id) {
    return request({
      url: '/product/attr/' + id,
      method: 'get'
    })
  }

export function removeRelation(attrId,attrGroupId) {
    return request({
      url: '/product/attr/deleteRelation/' + attrId+'/'+attrGroupId,
      method: 'delete'
    })
}

export function delObj(id) {
  return request({
    url: '/product/attr/' + id,
    method: 'delete'
  })
}
export function delBatch(ids) {
    return request({
      url: '/product/attr/deleteBatch' ,
      method: 'delete',
      data: ids
    })
  }

export function putObj(obj) {
  return request({
    url: '/product/attr',
    method: 'put',
    data: obj
  })
}


export function baseAttrListSpu(spuId) {
    return request({
      url: '/product/attrValue/getProductAttrValueBySpuId/'+spuId,
      method: 'get'
    })
}

export function updateSpuAttr(spuId,obj) {
    return request({
      url: '/product/attr/update/'+spuId,
      method: 'put',
      data: obj
    })
  }

