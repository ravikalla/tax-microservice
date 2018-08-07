package com.wellsfargo.taxie.recipienthelper.service;

import com.wellsfargo.taxie.recipienthelper.service.dto.RecipientDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Recipient.
 */
public interface RecipientService {

    /**
     * Save a recipient.
     *
     * @param recipientDTO the entity to save
     * @return the persisted entity
     */
    RecipientDTO save(RecipientDTO recipientDTO);

    /**
     * Get all the recipients.
     *
     * @return the list of entities
     */
    List<RecipientDTO> findAll();


    /**
     * Get the "id" recipient.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<RecipientDTO> findOne(Long id);

    /**
     * Delete the "id" recipient.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
