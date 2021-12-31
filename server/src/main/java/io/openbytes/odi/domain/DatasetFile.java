package io.openbytes.odi.domain;

import lombok.Getter;
import lombok.ToString;

/**
 * this is the abstraction of remote object info.
 */

@Getter
@ToString
public class DatasetFile {

    /**
     * eg: c.txt
     */
    private String name;

    /**
     * eg: a/b/c.txt
     */
    private String fullPath;

    private Long size;

    //todo need to add a function to set this
    private String url;

    public DatasetFile(String name, String fullPath, Long size) {
        this.name = name;
        this.fullPath = fullPath;
        this.size = size;
    }
}
