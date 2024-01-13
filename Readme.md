# Core classes of Openfeign
- org.springframework.cloud.openfeign.EnableFeignClients;
- org.springframework.cloud.openfeign.FeignClient;
- org.springframework.cloud.openfeign.FallbackFactory;
- org.springframework.cloud.openfeign.CircuitBreakerNameResolver
- org.springframework.cloud.openfeign.FeignClientProperties$FeignClientConfiguration
- feign.RetryableException
- feign.FeignException

# Openfeign Configuration
- /org/springframework/cloud/spring-cloud-openfeign-core/3.1.7/spring-cloud-openfeign-core-3.1.7.jar!/META-INF/additional-spring-configuration-metadata.json


# Core classes of Resilience4j Bulkhead
- io.github.resilience4j.bulkhead.annotation.Bulkhead@
- io.github.resilience4j.bulkhead.autoconfigure.BulkheadAutoConfiguration
- io.github.resilience4j.bulkhead.autoconfigure.BulkheadMetricsAutoConfiguration
- io.github.resilience4j.bulkhead.autoconfigure.ThreadPoolBulkheadMetricsAutoConfiguration
- 
# Core classes of Resilience4j CircuitBreaker
- io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker@
- io.github.resilience4j.circuitbreaker.CircuitBreaker
- io.github.resilience4j.circuitbreaker.CircuitBreakerConfig
- io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry
- io.github.resilience4j.circuitbreaker.CallNotPermittedException
- io.github.resilience4j.circuitbreaker.internal.CircuitBreakerStateMachine
- io.github.resilience4j.circuitbreaker.autoconfigure.CircuitBreakerAutoConfiguration
- io.github.resilience4j.circuitbreaker.autoconfigure.CircuitBreakerStreamEventsAutoConfiguration
- io.github.resilience4j.circuitbreaker.autoconfigure.CircuitBreakerMetricsAutoConfiguration
- io.github.resilience4j.circuitbreaker.autoconfigure.CircuitBreakersHealthIndicatorAutoConfiguration
- io.github.resilience4j.circuitbreaker.monitoring.endpoint.CircuitBreakerEndpoint

# Core classes of Resilience4j RateLimiter
- io.github.resilience4j.ratelimiter.annotation.RateLimiter@
- io.github.resilience4j.ratelimiter.RateLimiter
- io.github.resilience4j.ratelimiter.RateLimiterConfig
- io.github.resilience4j.ratelimiter.RateLimiterRegistry
- io.github.resilience4j.ratelimiter.RequestNotPermitted;

# Core classes of Resilience4j Retry
- io.github.resilience4j.retry.annotation.Retry@
- io.github.resilience4j.retry.Retry
- io.github.resilience4j.retry.RetryConfig
- io.github.resilience4j.retry.RetryRegistry
- io.github.resilience4j.retry.autoconfigure.RetryAutoConfiguration
- io.github.resilience4j.retry.autoconfigure.RetryMetricsAutoConfiguration
- 
# Core classes of Resilience4j TimeLimiter
- io.github.resilience4j.timelimiter.annotation.TimeLimiter@
- io.github.resilience4j.timelimiter.TimeLimiter
- io.github.resilience4j.timelimiter.TimeLimiterConfig
- io.github.resilience4j.timelimiter.TimeLimiterRegistry
- io.github.resilience4j.ratelimiter.autoconfigure.RateLimiterAutoConfiguration
- io.github.resilience4j.ratelimiter.autoconfigure.RateLimiterMetricsAutoConfiguration
- io.github.resilience4j.ratelimiter.autoconfigure.RateLimitersHealthIndicatorAutoConfiguration
- io.github.resilience4j.timelimiter.autoconfigure.TimeLimiterAutoConfiguration
- io.github.resilience4j.timelimiter.autoconfigure.TimeLimiterMetricsAutoConfiguration

- io.github.resilience4j.scheduled.threadpool.autoconfigure.ContextAwareScheduledThreadPoolAutoConfiguration


# Resilience4j Configuration
- /io/github/resilience4j/resilience4j-spring-boot2/1.7.0/resilience4j-spring-boot2-1.7.0.jar!/META-INF/spring-configuration-metadata.json
- /org/springframework/boot/spring-boot-actuator-autoconfigure/2.6.14/spring-boot-actuator-autoconfigure-2.6.14.jar!/META-INF/additional-spring-configuration-metadata.json

# 断路器（Circuit Breaker）
- 断路器有三个状态： 关闭（CLOSED）：正常情况，所有的请求都正常通过断路器，没有任何限制。 打开（OPEN）：在过去的请求或者时间中，如果故障或者慢的响应率大于或者等于一个配置的阈值，断路器就会打开。在这种情况下，所有的请求都会受到限制。 半开（HALF_OPEN）：在打开状态下经过可配置的等待时间后，断路器允许少量（数值可配置）的请求通过。若失败/慢响应超过阈值，断路器重新打开；低于阈值，则断路器进入关闭状态。
- sliding-window-type ：断路器的滑动窗口期类型可以基于“次数”（COUNT_BASED）或者“时间”（TIME_BASED）进行熔断，默认是COUNT_BASED。
- sliding-window-size ：若COUNT_BASED，则10次调用中有50%失败（即5次）打开熔断断路器；若为TIME_BASED则，此时有额外的两个设置属性，含义为：在*秒内（sliding-window-size）100%（slow-call-rate-threshold）的请求超过2秒（
  slow-call-duration-threshold）打开断路器。
- failure-rate-threshold ：设置50%的调用失败时打开断路器。
- permitted-number-of-calls-in-half-open-state ：运行断路器在HALF_OPEN状态下时进行3次调用，如果故障或慢速调用仍然高于阈值，断路器再次进入打开状态。
- minimum-number-of-calls ：在每个滑动窗口期，配置断路器计算错误率或者慢调用率的最小调用数。设置5意味着，在计算故障率之前，必须至少调用5次。如果只记录了4次，即使4次都失败了，断路器也不会进入到打开状态。
- wait-duration-in-open-state ：一旦断路器是打开状态，它会拒绝请求5秒钟，然后转入半开状态。

# 隔板（Bulkhead）
- 隔板（Bulkhead）可以用来限制对于下游服务的最大并发数量的限制
- max-concurrent-calls ：隔板允许的最大并发执行数量
- max-wait-duration ：当试图进入一个饱和的隔板时，线程应被阻断的最大时间
- Resilience4j的隔板支持两种类型： 默认的Semaphore：使用用户请求的线程，而不创建新的线程。 线程池：创建新的线程用来处理。配置如：
- resilience4j.thread-pool-bulkhead.instances.bulkheadDemo.max-thread-pool-size= 3
- resilience4j.thread-pool-bulkhead.instances.bulkheadDemo.core-thread-pool-size=2
- resilience4j.thread-pool-bulkhead.instances.bulkheadDemo.queue-capacity=1

# 限时器（TimeLimiter）
- 限时器用来限制在另外一个线程中执行的服务调用的时间。

# Format of Feign Client Name
- {Feign-Class-Name}#{Method-Name}

# Format of Circuit Breaker Name
- {Feign-Class-Name}{Method-Name}{Parameter-Types}

# Test
- http://localhost:8080/actuator/health
- http://localhost:8080/actuator/metrics
- http://localhost:8080/actuator/metrics/resilience4j.circuitbreaker.buffered.calls
- http://localhost:8080/actuator/metrics/resilience4j.circuitbreaker.calls
- http://localhost:8080/actuator/metrics/resilience4j.circuitbreaker.failure.rate
- http://localhost:8080/actuator/metrics/resilience4j.circuitbreaker.not.permitted.calls
- http://localhost:8080/actuator/metrics/resilience4j.circuitbreaker.slow.call.rate
- http://localhost:8080/actuator/metrics/resilience4j.circuitbreaker.slow.call
- http://localhost:8080/actuator/metrics/resilience4j.circuitbreaker.state
- http://localhost:8080/actuator/circuitbreakerevents
- http://localhost:8080/actuator/circuitbreakerevents/{name}/{eventType}
- http://localhost:8080/actuator/circuitbreakerevents/{name}
- http://localhost:8080/actuator/circuitbreakers
- http://localhost:8080/actuator/circuitbreakers/{name}
- http://localhost:8080/actuator/metrics/{requiredMetricName}
- http://localhost:8080/actuator/retries
- http://localhost:8080/actuator/retryevents
- http://localhost:8080/actuator/retryevents/{name}
- http://localhost:8080/actuator/retryevents/{name}/{eventType}
- http://localhost:8080/actuator/bulkheads
- http://localhost:8080/actuator/bulkheadevents
- http://localhost:8080/actuator/bulkheadevents/{name}
- http://localhost:8080/actuator/bulkheadevents/{name}/{eventType}
- http://localhost:8080/actuator/ratelimiters
- http://localhost:8080/actuator/ratelimiterevents
- http://localhost:8080/actuator/ratelimiterevents/{name}
- http://localhost:8080/actuator/ratelimiterevents/{name}/{eventType}
- http://localhost:8080/actuator/timelimiters
- http://localhost:8080/actuator/timelimiterevents
- http://localhost:8080/actuator/timelimiterevents/{name}
- http://localhost:8080/actuator/timelimiterevents/{name}/{eventType}
- curl http://localhost:8080/a/json?task=200
- curl http://localhost:8080/a/json?task=307[retry]
- curl http://localhost:8080/a/json?task=400
- curl http://localhost:8080/e/json?task=408[timeout]
- curl http://localhost:8080/e/json?task=200[timelimiter]
- curl http://localhost:8080/d/json?task=408[bulkhead]
- curl http://localhost:8080/a/json?task=500[circuitbreaker]



