package io.openbytes.odi.interfaces.controller.api;

import io.openbytes.odi.application.DatasetService;
import io.openbytes.odi.interfaces.Result;
import io.openbytes.odi.interfaces.vo.DatasetVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Optional;

@Api(tags = "【web】Dataset")
@RequestMapping("/api/dataset")
@RestController
public class DatasetController {

    @Resource
    private DatasetService service;


    @ApiOperation(value = "Get an Dataset by name", notes = "When name not exist, return empty.")
    @GetMapping("/v1/get-by-name")
    public Result<Optional<DatasetVO>> getByName(String name) {
        if (name == null) {
            name = "";
        }

        return Result.ok(service.getByName(name));
    }
}
