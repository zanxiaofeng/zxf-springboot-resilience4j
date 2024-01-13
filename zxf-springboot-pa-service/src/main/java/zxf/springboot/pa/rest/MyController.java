package zxf.springboot.pa.rest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpServerErrorException;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import static org.springframework.http.HttpStatus.*;

@Slf4j
@RestController
@RequestMapping("/pa")
public class MyController {
    private AtomicInteger retryCounter = new AtomicInteger();

    public MyController() {
        log.info("::ctor");
    }

    @GetMapping("/a/json")
    public ResponseEntity<Map<String, Object>> a(@RequestParam Integer task) throws InterruptedException {
        log.info("::a, task={}", task);
        return result(task, "A");
    }

    @GetMapping("/b/json")
    public ResponseEntity<Map<String, Object>> b(@RequestParam Integer task) throws InterruptedException {
        log.info("::b, task={}", task);
        return result(task, "B");
    }

    @GetMapping("/c/json")
    public ResponseEntity<Map<String, Object>> c(@RequestParam Integer task) throws InterruptedException {
        log.info("::c, task={}", task);
        return result(task, "C");
    }

    @GetMapping("/d/json")
    public ResponseEntity<Map<String, Object>> d(@RequestParam Integer task) throws InterruptedException {
        log.info("::d, task={}", task);
        return result(task, "D");
    }

    @GetMapping("/e/json")
    public ResponseEntity<Map<String, Object>> e(@RequestParam Integer task) throws InterruptedException {
        log.info("::e, task={}", task);
        return result(task, "E");
    }

    private ResponseEntity<Map<String, Object>> result(@RequestParam Integer task, String service) throws InterruptedException {
        HttpStatus httpStatus = HttpStatus.resolve(task);
        switch (httpStatus) {
            case TEMPORARY_REDIRECT:
                if (retryCounter.incrementAndGet() % 2 == 0) {
                    return ResponseEntity.status(TEMPORARY_REDIRECT).build();
                }
                return ResponseEntity.ok().body(Collections.singletonMap("value", service + " Retry Value from PA: " + System.currentTimeMillis()));
            case BAD_REQUEST:
                return ResponseEntity.status(BAD_REQUEST).build();
            case REQUEST_TIMEOUT:
                Thread.sleep(1000 * 10);
                return ResponseEntity.ok().body(Collections.singletonMap("value", service + " Timeout Value from PA: " + System.currentTimeMillis()));
            case INTERNAL_SERVER_ERROR:
                return ResponseEntity.status(INTERNAL_SERVER_ERROR).build();
            default:
                return ResponseEntity.ok().body(Collections.singletonMap("value", service + " Value from PA: " + System.currentTimeMillis()));
        }
    }
}
