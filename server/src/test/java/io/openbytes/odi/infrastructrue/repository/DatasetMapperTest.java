package io.openbytes.odi.infrastructrue.repository;

import com.baomidou.mybatisplus.test.autoconfigure.MybatisPlusTest;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;


@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("local")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) //自动 setup & destroy db
@MybatisPlusTest
public class DatasetMapperTest {

    
    @Resource
    DatasetMapper datasetMapper;

//    @Test
//    public void insert() {
//        System.out.println("----------------------");
//        DatasetPO dataset = new DatasetPO();
//        datasetMapper.insert(dataset);
//    }


}
