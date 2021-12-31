package io.openbytes.odi.domain.storage;

import io.openbytes.odi.domain.DatasetFile;

import java.util.List;

public interface Storage {
    /**
     * list odi dataset files
     * @param prefix prefix you want to query, eg: abc/
     * @param marker
     * @param maxKeys
     * @return todo we should update resp, add isTruncated info...
     */
    List<DatasetFile> ListDatasetFiles(String prefix, String marker, int maxKeys);
}
