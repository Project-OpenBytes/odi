package io.openbytes.odi.application;

import cn.hutool.core.util.StrUtil;
import io.openbytes.odi.domain.DatasetFile;
import io.openbytes.odi.domain.storage.Storage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Slf4j(topic = "metricsLog")
@Service
public class DatasetFileService {

    private final Storage storage;

    public DatasetFileService(Storage storage) {
        this.storage = storage;
    }

    public List<DatasetFile> list(String datasetName, String marker, int maxKeys) {
        // todo check dataset
        if (StrUtil.isEmpty(datasetName)) {
            return Collections.emptyList();
        }

        // append suffix
        datasetName = StrUtil.addSuffixIfNot(datasetName, "/");
        return storage.ListDatasetFiles(datasetName, marker, maxKeys);
    }
}
