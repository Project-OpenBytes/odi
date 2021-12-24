package io.openbytes.odi.infrastructrue.repository;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@TableName("dataset")
@Getter
@Setter
public class DatasetPO {
    private String id;
    private String name;
    private String homepage;
    private String description;
    private String readmeLink;
    private Instant createdAt;
    private Instant updatedAt;
    private String ownerName;
    private String creatorUserId;
    private String creatorOrgId;
    private Integer viewCount;
    private Integer starCount;
    private Integer downloadCount;


    public DatasetPO(String id, String name, String homepage, String description, Instant createdAt, Instant updatedAt, String ownerName, String creatorUserId, String creatorOrgId) {
        this.id = id;
        this.name = name;
        this.homepage = homepage;
        this.description = description;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.ownerName = ownerName;
        this.creatorUserId = creatorUserId;
        this.creatorOrgId = creatorOrgId;
    }

    public DatasetPO() {
    }

}
