# springboot-demo
记录实现高并发的解决方案
day01
1.springboot整合druid多数据源和log4j2
  （1）实现数据源，主数据库（写），从数据库（读），读写分离的方案之一
  （2）mysql主从复制，保持数据一致性
  （3）使用druid的监控功能
  （4）log4j2日志介绍和配置说明
  
2.redis哨兵模式的实现,实现高可用
	主库写，从库读
	
day02
(1)两次md5,第一次客户端明文+固定盐值
第二次客户端第一次前端传过来的+随机盐值
项目里面模拟的是登录查询的过程
整个过程分析如下：首先，第一次用户注册的时候输入框输入明文，然后前端再加固定盐值，
第二次前端传过来第一次加密的+后端随机生成的盐值，该盐值要存入数据库

查询的时候，第一次客户端明文+固定盐值，传入后端，第二次后端去查询数据库注册时候后端随机生成的盐值，
把第一次前端传过来的加密的+数据库查询的随机盐值成密码，然后和数据库里面的密码对比是否一致

前端后端都加密所以是两次md5

 
