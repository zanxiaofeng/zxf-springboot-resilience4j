package zxf.springboot.ea.client;

import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.Map;

@Slf4j
public class PAClientFallback implements PAClient {
    private final Throwable cause;

    public PAClientFallback(Throwable cause) {
        log.info("::ctor");
        this.cause = cause;
    }

    @Override
    public Map<String, Object> serviceA(Integer task) {
        log.info("::serviceA, task={}, fallback={}", task, cause.getMessage());
        return Collections.singletonMap("value", "Fallback Value of A from EA: " + System.currentTimeMillis());
    }

    @Override
    public Map<String, Object> serviceB(Map<String, String> query) {
        log.info("::serviceB, task={}, fallback={}", query, cause.getMessage());
        return Collections.singletonMap("value", "Fallback Value of B from EA: " + System.currentTimeMillis());
    }

    @Override
    public Map<String, Object> serviceC(Integer task) {
        log.info("::serviceC, task={}, fallback={}", task, cause.getMessage());
        return Collections.singletonMap("value", "Fallback Value of C from EA: " + System.currentTimeMillis());
    }

    @Override
    public Map<String, Object> serviceD(Integer task) {
        log.info("::serviceD, task={}, fallback={}", task, cause.getMessage());
        return Collections.singletonMap("value", "Fallback Value of D from EA: " + System.currentTimeMillis());
    }

    @Override
    public Map<String, Object> serviceE(Integer task) {
        log.info("::serviceE, task={}, fallback={}", task, cause.getMessage());
        return Collections.singletonMap("value", "Fallback Value of E from EA: " + System.currentTimeMillis());
    }
}
