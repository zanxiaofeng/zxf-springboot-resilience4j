package zxf.springboot.ea.client;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@FeignClient(value = "pa-service", url = "${pa-service.url}", fallbackFactory = PAClientFallbackFactory.class)
public interface PAClient {
    //Feign Client Name: PAClient#serviceA(Integer)
    //Circuit Breaker Name: PAClientserviceAInteger
    @GetMapping("/pa/a/json")
    Map<String, Object> serviceA(@RequestParam Integer task);

    //Feign Client Name: PAClient#serviceB(Map)
    //Circuit Breaker Name: PAClientserviceBMap
    //Retry Name: ServiceB
    @GetMapping("/pa/b/json")
    @Retry(name = "ServiceB")
    Map<String, Object> serviceB(@SpringQueryMap Map<String, String> query);

    //Feign Client Name: PAClient#serviceC(Integer)
    //Circuit Breaker Name: PAClient-ServiceC
    //Rate Limiter Name: ServiceC
    @GetMapping("/pa/c/json")
    @RateLimiter(name = "ServiceC")
    @CircuitBreaker(name = "PAClient-ServiceC")
    Map<String, Object> serviceC(@RequestParam Integer task);

    //Feign Client Name: PAClient#serviceD(Integer)
    //Circuit Breaker Name: PAClientserviceDInteger
    //Bulkhead Name: Do not support
    @GetMapping("/pa/d/json")
    @Bulkhead(name = "ServiceD")
    Map<String, Object> serviceD(@RequestParam Integer task);

    //Feign Client Name: PAClient#serviceE(Integer)
    //Circuit Breaker Name: PAClientserviceEInteger
    //Rate Limiter Name: ServiceE
    @GetMapping("/pa/e/json")
    @RateLimiter(name = "ServiceE")
    Map<String, Object> serviceE(@RequestParam Integer task);
}
