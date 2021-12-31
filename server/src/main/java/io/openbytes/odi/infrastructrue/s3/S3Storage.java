package io.openbytes.odi.infrastructrue.s3;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ListObjectsRequest;
import com.amazonaws.services.s3.model.ObjectListing;
import io.openbytes.odi.domain.DatasetFile;
import io.openbytes.odi.domain.storage.Storage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class S3Storage implements Storage {

    @Resource
    private AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Override
    public List<DatasetFile> ListDatasetFiles(String prefix, String marker, int maxKeys) {
        ObjectListing objectListing = amazonS3.listObjects(
                new ListObjectsRequest().withBucketName(bucket)
                        .withPrefix(prefix)
                        .withMarker(marker)
                        .withMaxKeys(maxKeys)
        );

        return objectListing.getObjectSummaries().parallelStream().
                map(objectSummary -> new DatasetFile(objectSummary.getKey(), objectSummary.getKey(), objectSummary.getSize())).collect(Collectors.toList());
    }
}
