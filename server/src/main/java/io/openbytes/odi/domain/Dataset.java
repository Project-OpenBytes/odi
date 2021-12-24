package io.openbytes.odi.domain;

import cn.hutool.core.util.RandomUtil;
import io.openbytes.odi.domain.common.HttpURL;
import lombok.Getter;
import lombok.ToString;

import java.time.Instant;

/**
 * private String id;
 * private String name;
 * private String homepage;
 * private String description;
 * private String readmeLink;
 * private Instant createdAt;
 * private Instant updatedAt;
 * private String ownerName;
 * private String creatorUserId;
 * private String creatorOrgId;
 * private Integer viewCount;
 * private Integer starCount;
 * private Integer downloadCount;
 */
@Getter
@ToString
public class Dataset {
    private final static int ID_LENGTH = 14;

    private final String id;

    private String name;

    private HttpURL homepage;

    private String description;

    private HttpURL readmeLink;

    private Instant createdAt;

    private Instant updatedAt;

    private String OwnerName;

    private String creatorUserId;

    private String creatorOrgId;

    private Integer viewCount;

    private Integer starCount;

    private Integer downloadCount;

    public Dataset(String name, HttpURL homepage, String description, HttpURL readmeLink, Instant createdAt, Instant updatedAt,
                   String ownerName, String creatorUserId, String creatorOrgId) {
        this.id = RandomUtil.randomString(ID_LENGTH).toUpperCase();
        this.name = name;
        this.homepage = homepage;
        this.description = description;
        this.readmeLink = readmeLink;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.OwnerName = ownerName;
        this.creatorUserId = creatorUserId;
        this.creatorOrgId = creatorOrgId;
    }
}
