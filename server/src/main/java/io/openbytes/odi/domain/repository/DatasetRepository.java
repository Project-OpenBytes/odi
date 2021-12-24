package io.openbytes.odi.domain.repository;

import io.openbytes.odi.domain.Dataset;

import java.util.Optional;

public interface DatasetRepository {
    /**
     * Create or update an dataset.
     *
     * @param dataset not null
     */
    void save(Dataset dataset);


    /**
     * Get an Dataset.
     *
     * @param id not null
     * @return not null
     */
    Optional<Dataset> get(String id);

    /**
     * Get an Dataset by name.
     *
     * @param name not null
     * @return not null
     */
    Optional<Dataset> getByName(String name);

}
