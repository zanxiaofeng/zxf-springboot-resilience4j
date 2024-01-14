package zxf.springboot.ea.infra;

import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import io.github.resilience4j.ratelimiter.RequestNotPermitted;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.concurrent.TimeoutException;

@Slf4j
@RestControllerAdvice
public class Resilience4jExceptionHandler {
    @ExceptionHandler({CallNotPermittedException.class})
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    public String handleCallNotPermittedException(CallNotPermittedException ex) {
        log.error("Handle CallNotPermittedException exception for circuitbreaker", ex);
        return "调用不被允许";
    }

//    @ExceptionHandler({ BulkheadFullException.class })
//    @ResponseStatus(HttpStatus.BANDWIDTH_LIMIT_EXCEEDED)
//    public String handleBulkheadFullException(BulkheadFullException ex) {
//        log.error("Handle BulkheadFullException exception for bulkhead", ex);
//        return "隔板已满";
//    }

    @ExceptionHandler({RequestNotPermitted.class})
    @ResponseStatus(HttpStatus.TOO_MANY_REQUESTS)
    public String handleRequestNotPermitted(RequestNotPermitted ex) {
        log.error("Handle RequestNotPermitted exception for ratelimiter", ex);
        return "请求不被允许";
    }

    @ExceptionHandler({TimeoutException.class})
    @ResponseStatus(HttpStatus.REQUEST_TIMEOUT)
    public String handleTimeoutException(TimeoutException ex) {
        log.error("Handle TimeoutException exception for timelimiter", ex);
        return "访问超时";
    }
}
