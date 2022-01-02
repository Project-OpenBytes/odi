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

    /*
    file downloaded url
     */
    private String url;

    public DatasetFile(String name, String fullPath, Long size, String url) {
        this.name = name;
        this.fullPath = fullPath;
        this.size = size;
        this.url = url;
    }

}
