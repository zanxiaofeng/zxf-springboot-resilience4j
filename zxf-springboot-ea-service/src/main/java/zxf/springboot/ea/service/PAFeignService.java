package zxf.springboot.ea.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import zxf.springboot.ea.client.PAClient;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
public class PAFeignService {
    @Autowired
    private PAClient paClient;

    public PAFeignService() {
        log.info("::ctor");
    }

    public Map<String, Object> a(Integer task) {
        log.info("::a START, task={}", task);
        Map<String, Object> json = new HashMap<>();
        json.put("task", task);
        json.put("value", "Default Value in A Service of EA");
        json.put("downstream", paClient.serviceA(task));
        log.info("::a END, task={}", task);
        return json;
    }

    public Map<String, Object> b(Integer task) {
        log.info("::b START, task={}", task);
        Map<String, Object> json = new HashMap<>();
        json.put("task", task);
        json.put("value", "Default Value in B Service of EA");
        json.put("downstream", paClient.serviceB(Collections.singletonMap("task", task.toString())));
        log.info("::b END, task={}", task);
        return json;
    }

    public Map<String, Object> c(Integer task) {
        log.info("::c START, task={}", task);
        Map<String, Object> json = new HashMap<>();
        json.put("task", task);
        json.put("value", "Default Value in C Service of EA");
        json.put("downstream", paClient.serviceC(task));
        log.info("::c END, task={}", task);
        return json;
    }

    public Map<String, Object> d(Integer task) {
        log.info("::d START, task={}", task);
        Map<String, Object> json = new HashMap<>();
        json.put("task", task);
        json.put("value", "Default Value in D Service of EA");
        json.put("downstream", paClient.serviceD(task));
        log.info("::d END, task={}", task);
        return json;
    }

    @Async
    public CompletableFuture<Map<String, Object>> e(Integer task) {
        log.info("::e START, task={}", task);
        Map<String, Object> json = new HashMap<>();
        json.put("task", task);
        json.put("value", "Default Value in E Service of EA");
        json.put("downstream", paClient.serviceE(task));
        log.info("::e END, task={}", task);
        return CompletableFuture.completedFuture(json);
    }
}
