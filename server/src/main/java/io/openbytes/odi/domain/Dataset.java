package io.openbytes.odi.domain;

import io.openbytes.odi.domain.common.HttpURL;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.time.Instant;

@Getter
@ToString
public class Dataset {
    //private final String id;

    private String name;

    private HttpURL homepage;

    private Instant createdAt;

    private Instant updatedAt;

    public Dataset() {

    }

}
