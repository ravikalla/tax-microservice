package com.wellsfargo.taxie.recipienthelper.service;

import com.wellsfargo.taxie.recipienthelper.domain.Taxform;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Taxform.
 */
public interface TaxformService {

    /**
     * Save a taxform.
     *
     * @param taxform the entity to save
     * @return the persisted entity
     */
    Taxform save(Taxform taxform);

    /**
     * Get all the taxforms.
     *
     * @return the list of entities
     */
    List<Taxform> findAll();


    /**
     * Get the "id" taxform.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<Taxform> findOne(Long id);

    /**
     * Delete the "id" taxform.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
