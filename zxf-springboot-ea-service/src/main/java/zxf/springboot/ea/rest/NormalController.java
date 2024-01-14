package zxf.springboot.ea.rest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import zxf.springboot.ea.service.PAFeignService;
import zxf.springboot.ea.service.PANormalService;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Slf4j
@RestController
public class NormalController {
    @Autowired
    private PANormalService paNormalService;

    public NormalController() {
        log.info("::ctor");
    }

    @GetMapping("/normal/a/json")
    public Map<String, Object> a(@RequestParam Integer task) {
        log.info("::a, task={}", task);
        return paNormalService.a(task);
    }

    @GetMapping("/normal/b/json")
    public Map<String, Object> b(@RequestParam Integer task) {
        log.info("::b, task={}", task);
        return paNormalService.b(task);
    }

    @GetMapping("/normal/c/json")
    public Map<String, Object> c(@RequestParam Integer task) {
        log.info("::c, task={}", task);
        return paNormalService.c(task);
    }

    @GetMapping("/normal/d/json")
    public CompletableFuture<Map<String, Object>> d(@RequestParam Integer task) {
        log.info("::d, task={}", task);
        return paNormalService.d(task);
    }

    @GetMapping("/normal/e/json")
    public CompletableFuture<Map<String, Object>> e(@RequestParam Integer task) {
        log.info("::e, task={}", task);
        return paNormalService.e(task);
    }
}
