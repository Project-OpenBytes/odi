package io.openbytes.odi.interfaces.vo;

import io.openbytes.odi.domain.Dataset;

import java.time.Instant;

public class DatasetVO {
    public String id;
    public String name;
    public String homepage;
    public String description;
    public String readmeLink;
    public Instant createdAt;
    public Instant updatedAt;
    public String ownerName;
    public String creatorUserId;
    public String creatorOrgId;
    public Integer viewCount;
    public Integer starCount;
    public Integer downloadCount;

    public static DatasetVO fromDO(Dataset dataset) {
        DatasetVO vo = new DatasetVO();
        vo.id = dataset.getId();
        vo.name = dataset.getName();
        if (dataset.getHomepage() != null) {
            vo.homepage = dataset.getHomepage().getUrl();
        }
        vo.description = dataset.getDescription();
        if (dataset.getReadmeLink() != null) {
            vo.readmeLink = dataset.getReadmeLink().getUrl();
        }
        vo.createdAt = dataset.getCreatedAt();
        vo.updatedAt = dataset.getUpdatedAt();
        vo.ownerName = dataset.getOwnerName();
        vo.creatorUserId = dataset.getCreatorUserId();
        vo.creatorOrgId = dataset.getCreatorOrgId();
        return vo;
    }
}
