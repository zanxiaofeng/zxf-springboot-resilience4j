package zxf.springboot.ea.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class PAClientFallbackFactory implements FallbackFactory<PAClient> {
    @Override
    public PAClient create(Throwable cause) {
        log.error("Exception when call downstream api and will fallback.", cause);
        return new PAClientFallback(cause);
    }
}
