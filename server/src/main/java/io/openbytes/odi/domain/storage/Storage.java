package io.openbytes.odi.domain.storage;

import java.util.List;
import java.util.Map;

public interface Storage {
    /**
     * list odi dataset files
     *
     * @param prefix  prefix you want to query, eg: abc/
     * @param marker
     * @param maxKeys
     * @return todo we should update resp, add isTruncated info...
     */
    ListFilesResponse ListDatasetFiles(String prefix, String marker, int maxKeys);

    /**
     * get file url
     *
     * @param key
     * @return url
     */
    String GetUrl(String key);

    /**
     * get file urls
     *
     * @param keys
     * @return
     */
    Map<String, String> GetUrls(List<String> keys);
}
