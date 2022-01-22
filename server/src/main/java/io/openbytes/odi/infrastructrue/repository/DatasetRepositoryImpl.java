/*
 * Copyright 2021 The OpenBytes Team. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.openbytes.odi.infrastructrue.repository;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.openbytes.odi.domain.Dataset;
import io.openbytes.odi.domain.common.HttpURL;
import io.openbytes.odi.domain.repository.DatasetRepository;
import io.openbytes.odi.infrastructrue.ODIPage;
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

    @Override
    public ODIPage<Dataset> listPageByNameAndTag(Page<Dataset> page, String keyword) {
        IPage<DatasetPO> datasetPage = datasetMapper.selectListPageByName(toPage(page), keyword);
        return ODIPage.buildResult(datasetPage, this::fromPO);
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
                dataset.getDescription(), dataset.getOwnerName(),
                dataset.getCreatorUserId(), dataset.getCreatorOrgId());
    }


    private Page<DatasetPO> toPage(Page<Dataset> fromPage) {
        Page<DatasetPO> datasetVOPage = new Page<>();
        BeanUtil.copyProperties(fromPage, datasetVOPage, false);
        return datasetVOPage;
    }

    private Dataset fromPO(DatasetPO po) {
        HttpURL homepage = po.getHomepage() == null ? null : new HttpURL(po.getHomepage());
        HttpURL readmeLink = po.getReadmeLink() == null ? null : new HttpURL(po.getReadmeLink());
        return new Dataset(po.getId(), po.getName(), homepage, po.getDescription(), readmeLink,
                po.getInsertTime(), po.getUpdateTime(), po.getOwnerName(), po.getCreatorUserId(), po.getCreatorOrgId());
    }

}
