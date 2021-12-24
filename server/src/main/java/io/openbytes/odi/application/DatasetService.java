package io.openbytes.odi.application;

import io.openbytes.odi.domain.Dataset;
import io.openbytes.odi.domain.repository.DatasetRepository;
import io.openbytes.odi.interfaces.vo.DatasetVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Optional;

@Slf4j(topic = "metricsLog")
@Service
public class DatasetService {

    @Resource
    private DatasetRepository datasetRepository;

    public Optional<DatasetVO> getByName(String name) {
        Optional<Dataset> optionalDataset = datasetRepository.getByName(name);
        if (!optionalDataset.isPresent()) {
            return Optional.empty();
        }

        DatasetVO datasetVO = assembleVo(optionalDataset.get());
        return Optional.of(datasetVO);
    }

   public Optional<DatasetVO> getById(String id) {
        Optional<Dataset> optionalDataset = datasetRepository.get(id);
        if (optionalDataset.isEmpty()) {
            return Optional.empty();
        }

        DatasetVO datasetVO = assembleVo(optionalDataset.get());
        return Optional.of(datasetVO);
    }


    private DatasetVO assembleVo(Dataset dataset) {
        return DatasetVO.fromDO(dataset);
    }


}
