package zxf.springboot.pa.rest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
        log.info("::a START, task={}", task);
        ResponseEntity<Map<String, Object>> response = result(task, "A");
        log.info("::a END, task={}", task);
        return response;
    }

    @GetMapping("/b/json")
    public ResponseEntity<Map<String, Object>> b(@RequestParam Integer task) throws InterruptedException {
        log.info("::b START, task={}", task);
        ResponseEntity<Map<String, Object>> response = result(task, "B");
        log.info("::b END, task={}", task);
        return response;
    }

    @GetMapping("/c/json")
    public ResponseEntity<Map<String, Object>> c(@RequestParam Integer task) throws InterruptedException {
        log.info("::c START, task={}", task);
        ResponseEntity<Map<String, Object>> response = result(task, "C");
        log.info("::c END, task={}", task);
        return response;
    }

    @GetMapping("/d/json")
    public ResponseEntity<Map<String, Object>> d(@RequestParam Integer task) throws InterruptedException {
        log.info("::d START, task={}", task);
        ResponseEntity<Map<String, Object>> response = result(task, "D");
        log.info("::d END, task={}", task);
        return response;
    }

    @GetMapping("/e/json")
    public ResponseEntity<Map<String, Object>> e(@RequestParam Integer task) throws InterruptedException {
        log.info("::e START, task={}", task);
        ResponseEntity<Map<String, Object>> response = result(task, "E");
        log.info("::e END, task={}", task);
        return response;
    }

    private ResponseEntity<Map<String, Object>> result(Integer task, String service) throws InterruptedException {
        HttpStatus httpStatus = HttpStatus.resolve(task);
        switch (httpStatus) {
            case OK:
                return ResponseEntity.ok().body(Collections.singletonMap("value", service + " Value from PA: " + System.currentTimeMillis()));
            case BAD_REQUEST:
                return ResponseEntity.status(BAD_REQUEST).build();
            case REQUEST_TIMEOUT:
                Thread.sleep(1000 * 10);
                return ResponseEntity.ok().body(Collections.singletonMap("value", service + " Timeout Value from PA: " + System.currentTimeMillis()));
            case SERVICE_UNAVAILABLE:
                if (retryCounter.incrementAndGet() % 2 == 0) {
                    return ResponseEntity.status(SERVICE_UNAVAILABLE).build();
                }
                return ResponseEntity.ok().body(Collections.singletonMap("value", service + " Retry Value from PA: " + System.currentTimeMillis()));
            default:
                return ResponseEntity.status(INTERNAL_SERVER_ERROR).build();
        }
    }
}
