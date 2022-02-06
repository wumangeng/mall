import request from '@/router/axios'

// SpuInfo
export function searchList(query) {
    return request({
      url: '/product/spuinfo/searchPage',
      method: 'get',
      params: query
    })
}

export function fetchSpuInfoList(query) {
    return request({
      url: '/product/spuinfo/page',
      method: 'get',
      params: query
    })
}

export function upSpu(spuId) {
    return request({
      url: '/product/spuinfo/upSpu/'+spuId,
      method: 'post'
    })
}



//       skuInfo
export function fetchSkuInfoList(query) {
    return request({
      url: '/product/skuInfo/list',
      method: 'get',
      params: query
    })
}

