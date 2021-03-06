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

package io.openbytes.odi.application;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.openbytes.odi.BizException;
import io.openbytes.odi.CodeAndMessage;
import io.openbytes.odi.domain.Dataset;
import io.openbytes.odi.domain.common.HttpURL;
import io.openbytes.odi.domain.repository.DatasetRepository;
import io.openbytes.odi.infrastructrue.ODIPage;
import io.openbytes.odi.interfaces.ro.CreateDatasetRO;
import io.openbytes.odi.interfaces.ro.ListDatasetRO;
import io.openbytes.odi.interfaces.vo.DatasetVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j(topic = "metricsLog")
@Service
public class DatasetService {

    @Resource
    private DatasetRepository datasetRepository;

    @Resource
    private TokenService tokenService;

    public Optional<DatasetVO> getByName(String name) {
        Optional<Dataset> optionalDataset = datasetRepository.getByName(name);
        if (!optionalDataset.isPresent()) {
            return Optional.empty();
        }

        DatasetVO datasetVO = toVo(optionalDataset.get());
        return Optional.of(datasetVO);
    }

    public Optional<DatasetVO> getById(String id) {
        Optional<Dataset> optionalDataset = datasetRepository.get(id);
        if (optionalDataset.isEmpty()) {
            return Optional.empty();
        }

        DatasetVO datasetVO = toVo(optionalDataset.get());
        return Optional.of(datasetVO);
    }


    /**
     * create a dataset, this should be called by sync service or directly by rest api?
     *
     * @param ro
     */
    public void create(CreateDatasetRO ro) {
        // get user
        String creatorUserId = Optional.ofNullable(ro.creatorUserId).orElseGet(() -> tokenService.getUserByToken(ro.odiToken).getId());
        Dataset dataset = new Dataset(ro.name, new HttpURL(ro.homepage), ro.description, new HttpURL(ro.readmeLink), ro.ownerName, creatorUserId, null);
        // check name exist
        datasetRepository.getByName(ro.name).ifPresent((x) -> {
            throw new BizException(CodeAndMessage.doSuccess, String.format("dataset with %s exists", ro.name));
        });
        datasetRepository.save(dataset);
    }

    public ODIPage<DatasetVO> listDatasetsByQuery(ListDatasetRO queryRO) {
        Page<Dataset> datasetPage = new Page<>();

        if (queryRO.getOrderFields() != null) {
            datasetPage.setOrders(queryRO.getOrderFields().entrySet().stream().map(x -> new OrderItem(x.getKey(), x.getValue())).collect(Collectors.toList()));
        }

        datasetPage.setSize(queryRO.getSize()).setCurrent(queryRO.getIndex());
        return ODIPage.buildResult(datasetRepository.listPageByNameAndTag(datasetPage, queryRO.getKeyword()), DatasetVO::from);
    }

    private DatasetVO toVo(Dataset dataset) {
        return DatasetVO.from(dataset);
    }

}
