import request from '@/router/axios'

export function listWithTree () {
  return request({
    url: '/product/category/list/tree',
    method: 'get'
  })
}

export function getCategoryById (catId) {
  return request({
    url: '/product/category/getCategoryById/'+catId,
    method: 'get'
  })
}


export function deleteBatch (catIdList) {
  return request({
    url: '/product/category/deleteBatch',
    method: 'post',
    data: catIdList
  })
}

export function saveCategory (category) {
  return request({
    url: '/product/category/addCategory',
    method: 'post',
    data: category
  })
}


export function editCategory (category) {
  return request({
    url: '/product/category/updateCategory',
    method: 'post',
    data: category
  })
}

