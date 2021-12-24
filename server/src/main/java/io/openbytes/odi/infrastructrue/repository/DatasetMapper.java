package io.openbytes.odi.infrastructrue.repository;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import io.openbytes.odi.infrastructrue.repository.mysql.BaseOptionalMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.Optional;

@Mapper
public interface DatasetMapper extends BaseOptionalMapper<DatasetPO> {

    @Select("select * from dataset")
    DatasetPO selectById(String id);

    default Optional<DatasetPO> selectOneByNameOptional(String name) {
        LambdaQueryWrapper<DatasetPO> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(DatasetPO::getName, name)
                .last("limit 1");
        return selectOneOptional(queryWrapper);
    }
}