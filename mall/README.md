<p align="center">
 <img src="https://img.shields.io/badge/Pig-3.3-success.svg" alt="Build Status">
 <img src="https://img.shields.io/badge/Spring%20Cloud-2020-blue.svg" alt="Coverage Status">
 <img src="https://img.shields.io/badge/Spring%20Boot-2.5-blue.svg" alt="Downloads">
 <img src="https://img.shields.io/github/license/pig-mesh/pig"/>
</p>


## 系统说明

- 基于 Spring Cloud 2020 、Spring Boot 2.5、 OAuth2 的 RBAC **权限管理系统**
- 基于数据驱动视图的理念封装 element-ui，即使没有 vue 的使用经验也能快速上手
- 提供对常见容器化支持 Docker、Kubernetes、Rancher2 支持
- 提供 lambda 、stream api 、webflux 的生产实践

### 核心依赖

| 依赖                   | 版本           |
| ---------------------- | ------------- |
| Spring Boot            | 2.5.4         |
| Spring Cloud           | 2020.0.3      |
| Spring Cloud Alibaba   | 2021.1        |
| Spring Security OAuth2 | 2.3.6         |
| Mybatis Plus           | 3.4.3.2         |
| hutool                 | 5.7.9         |
| Avue                   | 2.6.18        |

### 模块说明

```lua
pig-ui  -- https://gitee.com/log4j/pig-ui

mall
├── mall-auth -- 授权服务提供[3000]
└── mall-common -- 系统公共模块
     ├── mall-common-bom -- 全局依赖管理控制
     ├── mall-common-core -- 公共工具类核心包
     ├── mall-common-datasource -- 动态数据源包
     ├── mall-common-job -- xxl-job 封装
     ├── mall-common-log -- 日志服务
     ├── mall-common-mybatis -- mybatis 扩展封装
     ├── mall-common-security -- 安全工具类
     ├── mall-common-swagger -- 接口文档
     ├── mall-common-feign -- feign 扩展封装
     └── mall-common-test -- oauth2.0 单元测试扩展封装
├── mall-register -- Nacos Server[8848]
├── mall-gateway -- Spring Cloud Gateway网关[9999]

└── mall-upms -- 通用用户权限管理模块
     └── mall-upms-api -- 通用用户权限管理系统公共api模块
     └── mall-upms-biz -- 通用用户权限管理系统业务处理模块[4000]
     
└── mall-coupon -- mall 优惠券管理模块
     └── mall-coupon-api -- 优惠券api模块
     └── mall-coupon-biz -- 优惠券业务处理模块[7001]
     
└── mall-member -- mall 会员模块
     └── mall-member-api -- 会员api模块
     └── mall-member-biz --会员业务处理模块[7002]
     
└── mall-order -- mall 订单模块
     └── mall-order-api -- 订单api模块
     └── mall-order-biz --订单业务处理模块[7003]
     
└── mall-product -- mall 产品（商品）模块
     └── mall-product-api -- 商品api模块
     └── mall-product-biz --商品业务处理模块[7004]
     
└── mall-resources -- mall 资源（图片，es）模块
     └── mall-resources-api -- 订单api模块
     └── mall-resources-biz --订单业务处理模块[7007]
     
└── mall-ware -- mall 仓储模块
     └── mall-ware-api -- 仓储api模块
     └── mall-ware-biz -- 仓储业务处理模块[7005]
     
└── mall-visual
     └── mall-monitor -- 服务监控 [5001]
     ├── mall-codegen -- 图形化代码生成 [5002]
     ├── mall-sentinel-dashboard -- 流量高可用 [5003]
     └── mall-xxl-job-admin -- 分布式定时任务管理台 [5004]
```
缓存数据一致性解决方案（失效模式）：
```
1、设置缓存过期时间
2、读写数据采用分布式锁(klock)的读写锁
```
### Docker 运行

```
# 下载并运行服务端代码
git clone https://gitee.com/log4j/pig.git

cd pig && mvn clean install && docker-compose up -d

# 下载并运行前端UI
git clone https://gitee.com/log4j/pig-ui.git

cd pig-ui && npm install -g cnpm --registry=https://registry.npm.taobao.org

npm run build:docker && docker-compose up -d
```

### 快速构架微服务

```bash
<!-- mall-gen archetype -->
# 在空文件夹执行以下命令，注意 windows 下  \ 修改成 ^
mvn archetype:generate \
       -DgroupId=com.pig4cloud \
       -DartifactId=demo \
       -Dversion=1.0.0-SNAPSHOT \
       -Dpackage=com.pig4cloud.pig.demo \
       -DarchetypeGroupId=com.pig4cloud.archetype \
       -DarchetypeArtifactId=pig-gen \
       -DarchetypeVersion=3.3.2 \
       -DarchetypeCatalog=local
```

### 开源协议

pig 开源软件遵循 [Apache 2.0 协议](https://www.apache.org/licenses/LICENSE-2.0.html)。
允许商业使用，但务必保留类作者、Copyright 信息。
