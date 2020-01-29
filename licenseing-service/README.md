有3个不同的Spring/Netflix客户端库，服务消费者可以使用它们来和Ribbon进行交互。从最低级别到最高级别如下：

1. Spring DiscoveryClient
2. 启用了 RestTemplate 的 Spring DiscoveryClient
3. Netflix Feign 客户端

----

客户端弹性模式
1. 客户端负载均衡(clien load balance)
2. 断路器模式(circuit breaker)
3. 后备模式(fallback)
4. 舱壁模式(bulkhead)