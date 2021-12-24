package io.openbytes.odi.infrastructrue.repository;

import io.openbytes.odi.domain.Dataset;
import io.openbytes.odi.domain.common.HttpURL;
import io.openbytes.odi.domain.repository.DatasetRepository;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.Optional;

@Repository
public class DatasetRepositoryImpl implements DatasetRepository {
    @Resource
    private DatasetMapper datasetMapper;

    @Override
    public void save(Dataset dataset) {
        datasetMapper.insert(toPO(dataset));
    }

    @Override
    public Optional<Dataset> get(String id) {
        return assemble(datasetMapper.selectByIdOptional(id));
    }

    @Override
    public Optional<Dataset> getByName(String name) {
        return assemble(datasetMapper.selectOneByNameOptional(name));
    }


    private Optional<Dataset> assemble(Optional<DatasetPO> optionalDatasetPO) {
        if (optionalDatasetPO.isEmpty()) {
            return Optional.empty();
        }

        DatasetPO po = optionalDatasetPO.get();
        return Optional.of(fromPO(po));

    }

    private DatasetPO toPO(Dataset dataset) {
        return new DatasetPO(dataset.getId(), dataset.getName(), dataset.getHomepage().toString(),
                dataset.getDescription(), dataset.getCreatedAt(), dataset.getUpdatedAt(), dataset.getOwnerName(),
                dataset.getCreatorUserId(), dataset.getCreatorOrgId());
    }

    private Dataset fromPO(DatasetPO po) {
        HttpURL homepage = po.getHomepage() == null ? null : new HttpURL(po.getHomepage());
        HttpURL readmeLink = po.getReadmeLink() == null ? null : new HttpURL(po.getReadmeLink());
        return new Dataset(po.getId(), homepage, po.getDescription(), readmeLink,
                po.getCreatedAt(), po.getUpdatedAt(), po.getOwnerName(), po.getCreatorUserId(), po.getCreatorOrgId());
    }
}
