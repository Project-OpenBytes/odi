package io.openbytes.odi.interfaces.controller.api;

import io.openbytes.odi.BizException;
import io.openbytes.odi.CodeAndMessage;
import io.openbytes.odi.interfaces.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.Data;
import lombok.Getter;
import lombok.experimental.Accessors;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.time.Instant;

@Api(tags = "【Web】Health")
@RequestMapping("/health")
@RestController
public class HealthController {

    @Resource
    protected HttpServletResponse response;

    @Value("${spring.application.name}")
    private String appName;


    private Health newHealth() {
        return new Health().setServerTime(Instant.now()).setMessage(String.format("<%s> is ok", appName)).setApiVersion(new APIVersionInfo());
    }

    @ApiOperation("health check")
    @GetMapping("/check")
    public Result<Health> check() {
        return Result.ok(newHealth());
    }

    @ApiOperation("health error")
    @GetMapping("/error")
    public Result<Void> error() {
        response.setStatus(500);
        throw new BizException(CodeAndMessage.doFailed, "throw by http");
    }

    @Getter
    static private class APIVersionInfo {
        private final String branch;
        private final String commitId;
        private final String commitBody;
        private final String buildTime;

        public APIVersionInfo() {
            branch = System.getenv("BRANCH");
            commitId = System.getenv("COMMIT_ID");
            commitBody = System.getenv("COMMIT_BODY");
            buildTime = System.getenv("BUILD_TIME");
        }
    }

    @Accessors(chain = true)
    @Data
    static class Health {
        private APIVersionInfo apiVersion;

        private String message;

        private Instant serverTime;

    }

}