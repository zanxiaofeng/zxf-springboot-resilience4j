package zxf.springboot.ea.rest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import zxf.springboot.ea.service.PAService;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Slf4j
@RestController
public class MyController {
    @Autowired
    private PAService paService;

    public MyController() {
        log.info("::ctor");
    }

    @GetMapping("/a/json")
    public Map<String, Object> a(@RequestParam Integer task) {
        log.info("::a, task={}", task);
        return paService.a(task);
    }

    @GetMapping("/b/json")
    public Map<String, Object> b(@RequestParam Integer task) {
        log.info("::b, task={}", task);
        return paService.b(task);
    }

    @GetMapping("/c/json")
    public Map<String, Object> c(@RequestParam Integer task) {
        log.info("::c, task={}", task);
        return paService.c(task);
    }

    @GetMapping("/d/json")
    public Map<String, Object> d(@RequestParam Integer task) {
        log.info("::d, task={}", task);
        return paService.d(task);
    }

    @GetMapping("/e/json")
    public CompletableFuture<Map<String, Object>> e(@RequestParam Integer task) {
        log.info("::e, task={}", task);
        return paService.e(task);
    }
}
