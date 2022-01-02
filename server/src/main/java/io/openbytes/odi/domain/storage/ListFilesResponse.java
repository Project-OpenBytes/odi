package io.openbytes.odi.domain.storage;

import io.openbytes.odi.domain.DatasetFile;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Setter
@Getter
@ToString
public class ListFilesResponse {
    /**
     * whether there are more files after this response.
     */
    private boolean truncated;

    /**
     * this page files list.
     */
    private List<DatasetFile> files;

    public static ListFilesResponse emptyResponse() {
        return new ListFilesResponse();
    }
}
