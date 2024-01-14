package zxf.springboot.ea.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Component
public class PANormalClient {
    @Value("${pa-service.url}")
    private String baseUrl;

    @Async
    public CompletableFuture<Map<String, Object>> callDownstreamAsync(String path) {
        log.info("::callDownstreamAsync START, path={}", path);
        Map<String, Object> downstream = callDownstreamSync(path);
        CompletableFuture<Map<String, Object>> result = CompletableFuture.completedFuture(downstream);
        log.info("::callDownstreamAsync END, path={}", path);
        return result;
    }

    public Map<String, Object> callDownstreamSync(String path) {
        try {
            log.info("::callDownstreamSync START, path={}", path);
            Map<String, Object> result = new RestTemplate().getForObject(URI.create(baseUrl + path), Map.class);
            log.info("::callDownstreamSync END, path={}, result={}", path, result);
            return result;
        } catch (Throwable ex) {
            log.error("Exception when call downstream api.", ex);
            throw ex;
        }
    }
}
