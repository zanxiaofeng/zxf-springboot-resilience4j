package zxf.springboot.ea.rest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import zxf.springboot.ea.service.PAConfigService;

import java.util.Map;

@Slf4j
@RestController
public class ConfigController {
    @Autowired
    private PAConfigService paConfigService;

    @GetMapping("/config")
    public Map<String, Object> config(@RequestParam Integer task) {
        log.info("::config, task={}", task);
        return paConfigService.config(task);
    }
}
