import request from '@/router/axios'

export function fetchList(query) {
  return request({
    url: '/product/brand/page',
    method: 'get',
    params: query
  })
}

export function searchList(obj) {
  return request({
    url: '/product/brand/searchPage',
    method: 'post',
    data: obj
  })
}

export function addObj(obj) {
  return request({
    url: '/product/brand',
    method: 'post',
    data: obj
  })
}

export function getObj(id) {
  return request({
    url: '/product/brand/' + id,
    method: 'get'
  })
}

export function delObj(id) {
  return request({
    url: '/product/brand/' + id,
    method: 'delete'
  })
}

export function putObj(obj) {
  return request({
    url: '/product/brand',
    method: 'put',
    data: obj
  })
}

export function upload(obj) {
  const form =new FormData();
  form.append("file",obj);
  return request({
    url: '/resources/upload/uploadBrandLogo',
    method: 'post',
    data: form
  })
}
//分类关联
export function getCategoryBrandRelationList(brandId) {
  return request({
    url: '/product/categorybrandrelation/catelog/list/'+brandId,
    method: 'get'
  })
}

export function getRelationBrandsList(catId) {
    return request({
      url: '/product/categorybrandrelation/brands/list/'+catId,
      method: 'get'
    })
  }

export function addRelation(obj) {
  return request({
    url: '/product/categorybrandrelation',
    method: 'post',
    data: obj
  })
}

export function delRelation(id) {
  return request({
    url: '/product/categorybrandrelation/' + id,
    method: 'delete'
  })
}