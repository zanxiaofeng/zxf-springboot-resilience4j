package zxf.springboot.ea.infra;

import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import io.github.resilience4j.ratelimiter.RequestNotPermitted;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.concurrent.TimeoutException;

@RestControllerAdvice
public class Resilience4jExceptionHandler {
    @ExceptionHandler({CallNotPermittedException.class})
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    public String handleCallNotPermittedException() {
        return "调用不被允许";
    }

//    @ExceptionHandler({ BulkheadFullException.class })
//    @ResponseStatus(HttpStatus.BANDWIDTH_LIMIT_EXCEEDED)
//    public String handleBulkheadFullException() {
//        return "隔板已满";
//    }

    @ExceptionHandler({ RequestNotPermitted.class })
    @ResponseStatus(HttpStatus.TOO_MANY_REQUESTS)
    public String handleRequestNotPermitted() {
        return "请求不被允许";
    }

    @ExceptionHandler({ TimeoutException.class })
    @ResponseStatus(HttpStatus.REQUEST_TIMEOUT)
    public String handleTimeoutException() {
        return "访问超时";
    }
}
