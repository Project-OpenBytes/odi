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

package io.openbytes.odi.infrastructrue;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.Getter;

import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 *
 */
@Getter
public class ODIPage<R> {

    private Long current;

    private Long size;

    private Long total;

    private Long pages;

    private List<R> records;

    /**
     * convert `page of mysql result` to `page of vo`;
     *
     * @param page     method result of mapper.selectPageXXX();
     * @param function e.g. VO.newInstance();
     * @param <T>      T of page;
     * @param <R>      R of VO;
     * @return graviti page;
     */
    public static <T, R> ODIPage<R> buildResult(IPage<T> page, Function<T, R> function) {
        ODIPage<R> odiPage = new ODIPage<>();
        odiPage.current = page.getCurrent();
        odiPage.size = page.getSize();
        odiPage.total = page.getTotal();
        odiPage.pages = page.getPages();
        odiPage.records = page.getRecords().stream().map(function).collect(Collectors.toList());
        return odiPage;
    }


    /**
     * convert `page of mysql result` to `page of vo`;
     *
     * @param page     method result of mapper.selectPageXXX();
     * @param function e.g. VO.newInstance();
     * @param <T>      T of page;
     * @param <R>      R of VO;
     * @return graviti page;
     */
    public static <T, R> ODIPage<R> buildResult(ODIPage<T> page, Function<T, R> function) {
        ODIPage<R> odiPage = new ODIPage<>();
        odiPage.current = page.getCurrent();
        odiPage.size = page.getSize();
        odiPage.total = page.getTotal();
        odiPage.pages = page.getPages();
        odiPage.records = page.getRecords().stream().map(function).collect(Collectors.toList());
        return odiPage;
    }


    public static <R> ODIPage<R> buildResult() {
        ODIPage<R> odiPage = new ODIPage<>();
        odiPage.records = Collections.emptyList();
        odiPage.total = 0L;
        return odiPage;
    }

}