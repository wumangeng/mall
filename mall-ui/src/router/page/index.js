import Layout from '@/page/index/'
export default [{
        path: '/login',
        name: '登录页',
        component: () =>
            import ( /* webpackChunkName: "page" */ '@/page/login/index'),
        meta: {
            keepAlive: true,
            isTab: false,
            isAuth: false
        }
    },
    {
        path: '/404',
        component: () =>
            import ( /* webpackChunkName: "page" */ '@/components/error-page/404'),
        name: '404',
        meta: {
            keepAlive: true,
            isTab: false,
            isAuth: true
        }
    },
    {
        path: '/403',
        component: () =>
            import ( /* webpackChunkName: "page" */ '@/components/error-page/403'),
        name: '403',
        meta: {
            keepAlive: true,
            isTab: false,
            isAuth: false
        }
    },
    {
        path: '/500',
        component: () =>
            import ( /* webpackChunkName: "page" */ '@/components/error-page/500'),
        name: '500',
        meta: {
            keepAlive: true,
            isTab: false,
            isAuth: false
        }
    },
    {
        path: '/',
        name: '主页',
        redirect: '/wel'
    },
    {
        path: '/myiframe',
        component: Layout,
        redirect: '/myiframe',
        children: [
          {
            path: ":routerPath",
            name: 'iframe',
            component: () =>
                import ( /* webpackChunkName: "page" */ '@/components/iframe/main'),
            props: true
          },
          {
            path: "/product/maintain/attrupdate",
            name: '规格',
            component: () =>
                import ( /* webpackChunkName: "page" */ '@/views/product/maintain/attrupdate'),
            props: true
          },
          {
            path: "/product/ware/sku",
            name: '商品库存',
            component: () =>
                import ( /* webpackChunkName: "page" */ '@/views/product/ware/sku'),
            props: true
          },
        ]
    },
    {
      path: '*',
      redirect: '/404',
    }
]

