package zxf.springboot.ea.service;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zxf.springboot.ea.client.PANormalClient;

import java.util.Map;

@Slf4j
@Service
public class PAConfigService {
    @Autowired
    private PANormalClient paNormalClient;

    public PAConfigService() {
        log.info("::ctor");
    }

    @CircuitBreaker(name = "Config-Service", fallbackMethod = "configFallback")
    public Map<String, Object> config(Integer task) {
        log.info("::config, task={}", task);
        return paNormalClient.callDownstreamSync("/pa/a/json?task=" + task);
    }

    private Map<String, Object> configFallback(Integer task, Exception ex) {
        log.error("::configFallback, task={}", task, ex);
        return paNormalClient.callDownstreamSync("/pa/b/json?task=200");
    }
}
