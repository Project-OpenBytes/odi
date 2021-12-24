package io.openbytes.odi.infrastructrue.repository;

import io.openbytes.odi.infrastructrue.repository.mysql.BaseOptionalMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface DatasetMapper extends BaseOptionalMapper<DatasetPO> {

    @Select("select * from dataset")
    DatasetPO selectById(String id);
}