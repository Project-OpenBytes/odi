package io.openbytes.odi.infrastructrue.repository.mysql;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.io.Serializable;
import java.util.Optional;

public interface BaseOptionalMapper<T> extends BaseMapper<T> {

    default Optional<T> selectByIdOptional(Serializable id) {
        return Optional.ofNullable(selectById(id));
    }

    default Optional<T> selectOneOptional(Wrapper<T> queryWrapper) {
        return Optional.ofNullable(selectOne(queryWrapper));
    }

}
