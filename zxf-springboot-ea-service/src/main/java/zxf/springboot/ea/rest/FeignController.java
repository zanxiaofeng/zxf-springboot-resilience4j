package zxf.springboot.ea.rest;

import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import zxf.springboot.ea.service.PAFeignService;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Slf4j
@RestController
public class FeignController {
    @Autowired
    private PAFeignService paFeignService;

    public FeignController() {
        log.info("::ctor");
    }

    @GetMapping("/feign/a/json")
    public Map<String, Object> a(@RequestParam Integer task) {
        log.info("::a, task={}", task);
        return paFeignService.a(task);
    }

    @GetMapping("/feign/b/json")
    public Map<String, Object> b(@RequestParam Integer task) {
        log.info("::b, task={}", task);
        return paFeignService.b(task);
    }

    @GetMapping("/feign/c/json")
    public Map<String, Object> c(@RequestParam Integer task) {
        log.info("::c, task={}", task);
        return paFeignService.c(task);
    }

    @GetMapping("/feign/d/json")
    public Map<String, Object> d(@RequestParam Integer task) {
        log.info("::d, task={}", task);
        return paFeignService.d(task);
    }

    //Time Limiter Name: ServiceE(TimeLimiter must use with CompletableFuture and CompletableFuture must be run in another thread)
    @TimeLimiter(name = "ServiceE")
    @GetMapping("/feign/e/json")
    public CompletableFuture<Map<String, Object>> e(@RequestParam Integer task) {
        log.info("::e, task={}", task);
        return paFeignService.e(task);
    }
}
