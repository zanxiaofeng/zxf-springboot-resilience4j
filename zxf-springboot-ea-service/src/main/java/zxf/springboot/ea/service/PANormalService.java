package zxf.springboot.ea.service;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zxf.springboot.ea.client.PANormalClient;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
public class PANormalService {
    @Autowired
    private PANormalClient paNormalClient;

    public PANormalService() {
        log.info("::ctor");
    }

    //Circuit Breaker Name: Normal-ServiceA
    @CircuitBreaker(name = "Normal-ServiceA")
    public Map<String, Object> a(Integer task) {
        log.info("::a START, task={}", task);
        Map<String, Object> json = new HashMap<>();
        json.put("task", task);
        json.put("value", "Default Value in A Service of EA");
        json.put("downstream", paNormalClient.callDownstreamSync("/pa/a/json?task=" + task));
        log.info("::a END, task={}", task);
        return json;
    }

    //Retry Name: Normal-ServiceB
    @Retry(name = "Normal-ServiceB")
    public Map<String, Object> b(Integer task) {
        log.info("::b START, task={}", task);
        Map<String, Object> json = new HashMap<>();
        json.put("task", task);
        json.put("value", "Default Value in B Service of EA");
        json.put("downstream", paNormalClient.callDownstreamSync("/pa/b/json?task=" + task));
        log.info("::b END, task={}", task);
        return json;
    }

    //Rate Limiter Name: Normal-ServiceC
    @RateLimiter(name = "Normal-ServiceC")
    public Map<String, Object> c(Integer task) {
        log.info("::c START, task={}", task);
        Map<String, Object> json = new HashMap<>();
        json.put("task", task);
        json.put("value", "Default Value in C Service of EA");
        json.put("downstream", paNormalClient.callDownstreamSync("/pa/c/json?task=" + task));
        log.info("::c END, task={}", task);
        return json;
    }

    @Retry(name = "Normal-ServiceD")
    @CircuitBreaker(name = "Normal-ServiceD")
    @TimeLimiter(name = "Normal-ServiceD")
    @RateLimiter(name = "Normal-ServiceD")
    @Bulkhead(name = "Normal-ServiceD")
    public CompletableFuture<Map<String, Object>> d(Integer task) {
        log.info("::d START, task={}", task);

        CompletableFuture<Map<String, Object>> result = paNormalClient.callDownstreamAsync("/pa/d/json?task=" + task)
                .thenApply((response) -> {
                    Map<String, Object> json = new HashMap<>();
                    json.put("task", task);
                    json.put("value", "Default Value in D Service of EA");
                    json.put("downstream", response);
                    return json;
                });

        log.info("::d END, task={}", task);
        return result;
    }

    //Time Limiter Name: Normal-ServiceE(TimeLimiter must use with CompletableFuture and CompletableFuture must be run in another thread)
    @TimeLimiter(name = "Normal-ServiceE")
    public CompletableFuture<Map<String, Object>> e(Integer task) {
        log.info("::e START, task={}", task);

        CompletableFuture<Map<String, Object>> result = paNormalClient.callDownstreamAsync("/pa/e/json?task=" + task)
                .thenApply((response) -> {
                    Map<String, Object> json = new HashMap<>();
                    json.put("task", task);
                    json.put("value", "Default Value in E Service of EA");
                    json.put("downstream", response);
                    return json;
                });

        log.info("::e END, task={}", task);
        return result;
    }
}
