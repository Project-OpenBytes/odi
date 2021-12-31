package io.openbytes.odi.interfaces.controller.api;

import io.openbytes.odi.application.DatasetService;
import io.openbytes.odi.application.DatasetFileService;
import io.openbytes.odi.domain.DatasetFile;
import io.openbytes.odi.interfaces.Result;
import io.openbytes.odi.interfaces.vo.DatasetVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

@Api(tags = "[web] Dataset")
@RequestMapping("/api/v1/datasets")
@RestController
public class DatasetController {

    @Resource
    private DatasetService service;

    @Resource
    private DatasetFileService datasetFileService;


    @ApiOperation(value = "Get an dataset by id", notes = "When id not exist, return empty.")
    @GetMapping("/{id}")
    public Result<Optional<DatasetVO>> getById(String id) {
        if (id == null) {
            id = "";
        }

        return Result.ok(service.getById(id));
    }


    /**
     *
     * @param dataset the dataset name you want to query.
     * @param marker marker is for page for query, if given marker, server will query files after this marker
     * @param maxKeys   maxKeys is the current page size you want to query.
     * @return
     */
    @ApiOperation(value = "Get dataset files", notes = "Get dataset files.")
    @GetMapping("/{dataset}/files")
    public Result<List<DatasetFile>> list(@PathVariable String dataset, @RequestParam(name = "marker", required = false) String marker, @RequestParam(name = "maxKeys", defaultValue = "10", required = false) int maxKeys) {
        if (dataset == null) {
            dataset = "";
        }

        return Result.ok(datasetFileService.list(dataset, marker, maxKeys));
    }

}
