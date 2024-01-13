package zxf.springboot.ea.service;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
public class PANormalService {
    @Value("${pa-service.url}")
    private String baseUrl;

    public PANormalService() {
        log.info("::ctor");
    }

    //Circuit Breaker Name: Normal-ServiceA
    @CircuitBreaker(name = "Normal-ServiceA")
    public Map<String, Object> a(Integer task) {
        log.info("::a, task={}", task);
        Map<String, Object> json = new HashMap<>();
        json.put("task", task);
        json.put("value", "Default Value in A Service of EA");
        json.put("downstream", callDownstream(baseUrl + "/pa/a/json?task=" + task));
        return json;
    }

    //Retry Name: Normal-ServiceB
    @Retry(name = "Normal-ServiceB")
    public Map<String, Object> b(Integer task) {
        log.info("::b, task={}", task);
        Map<String, Object> json = new HashMap<>();
        json.put("task", task);
        json.put("value", "Default Value in B Service of EA");
        json.put("downstream", callDownstream(baseUrl + "/pa/b/json?task=" + task));
        return json;
    }

    //Rate Limiter Name: Normal-ServiceC
    @RateLimiter(name = "Normal-ServiceC")
    public Map<String, Object> c(Integer task) {
        log.info("::c, task={}", task);
        Map<String, Object> json = new HashMap<>();
        json.put("task", task);
        json.put("value", "Default Value in C Service of EA");
        json.put("downstream", callDownstream(baseUrl + "/pa/c/json?task=" + task));
        return json;
    }

    public Map<String, Object> d(Integer task) {
        log.info("::d, task={}", task);
        Map<String, Object> json = new HashMap<>();
        json.put("task", task);
        json.put("value", "Default Value in D Service of EA");
        json.put("downstream", callDownstream(baseUrl + "/pa/d/json?task=" + task));
        return json;
    }

    //Time Limiter Name: Normal-ServiceE
    @Async
    @TimeLimiter(name = "Normal-ServiceE")
    public CompletableFuture<Map<String, Object>> e(Integer task) {
        log.info("::d, task={}", task);
        Map<String, Object> json = new HashMap<>();
        json.put("task", task);
        json.put("value", "Default Value in E Service of EA");
        json.put("downstream", callDownstream(baseUrl + "/pa/e/json?task=" + task));
        return CompletableFuture.completedFuture(json);
    }

    private Map<String, Object> callDownstream(String path) {
        return new RestTemplate().getForObject(URI.create(path), Map.class);
    }
}
