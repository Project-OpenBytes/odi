package io.openbytes.odi.application;

import cn.hutool.core.util.StrUtil;
import io.openbytes.odi.domain.storage.ListFilesResponse;
import io.openbytes.odi.domain.storage.Storage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j(topic = "metricsLog")
@Service
public class DatasetFileService {

    private final Storage storage;

    public DatasetFileService(Storage storage) {
        this.storage = storage;
    }

    public Optional<ListFilesResponse> list(String datasetName, String marker, int maxKeys) {
        // todo check dataset
        if (StrUtil.isEmpty(datasetName)) {
            return Optional.of(ListFilesResponse.emptyResponse());
        }

        // append suffix
        datasetName = StrUtil.addSuffixIfNot(datasetName, "/");
        return Optional.of(storage.ListDatasetFiles(datasetName, marker, maxKeys));
    }
}
