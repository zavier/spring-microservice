有3个不同的Spring/Netflix客户端库，服务消费者可以使用它们来和Ribbon进行交互。从最低级别到最高级别如下：

1. Spring DiscoveryClient
2. 启用了 RestTemplate 的 Spring DiscoveryClient
3. Netflix Feign 客户端