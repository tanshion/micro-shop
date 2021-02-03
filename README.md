# micro-shop

微商店是一个基于spring boot、spring security、mybatis、redis的轻量级、前后端分离、防范xss攻击、拥有分布式锁，为生产环境多实例完全准备，数据库为b2b2c设计，拥有完整sku和下单流程的完全开源商城


## 前言

`micro-shop`项目致力于为中小企业打造一个完整、易于维护的开源的电商系统，采用现阶段流行技术实现。后台管理系统包含商品管理、订单管理、运费模板、规格管理、会员管理、运营管理、内容管理、统计报表、权限管理、设置等模块。


## 技术选型

| 技术                   | 版本   | 说明                                    |
| ---------------------- | ------ | --------------------------------------- |
| Spring Boot            | 2.3.5  | MVC核心框架                             |
| Spring Security oauth2 | 2.3.4  | 认证和授权框架                          |
| MyBatis                | 3.5.6  | ORM框架                                 |
| MyBatisPlus            | 3.4.2  | 基于mybatis，使用lambda表达式的         |
| Swagger-UI             | 2.9.2  | 文档生产工具                            |
| Hibernator-Validator   | 6.0.17 | 验证框架                                |
| redisson               | 3.10.6 | 对redis进行封装、集成分布式锁等         |
| druid                  | 1.2.2  | 数据库连接池                            |
| log4j2                 | 2.11.2 | 更快的log日志工具                       |
| lombok                 | 1.18.8 | 简化对象封装工具                        |
| hutool                 | 4.5.0  | 更适合国人的java工具集                  |

## 开发工具

<a href="https://www.jetbrains.com/?from=micro-shop" target="_blank"><img src="https://s3.ax1x.com/2021/02/03/yKYjW8.png" width="150px" /></a>

## License

The Apache Software License, Version 2.0

Copyright  [2016]  [Anumbrella]

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.

