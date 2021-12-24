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
@RequestMapping("/api/v1/datasets")
@RestController
public class DatasetController {

    @Resource
    private DatasetService service;


    @ApiOperation(value = "Get an Dataset by id", notes = "When id not exist, return empty.")
    @GetMapping("/{id}")
    public Result<Optional<DatasetVO>> getById(String id) {
        if (id == null) {
            id = "";
        }

        return Result.ok(service.getById(id));
    }

}
