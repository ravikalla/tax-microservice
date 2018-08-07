package com.wellsfargo.taxie.wftfv.service;

import com.wellsfargo.taxie.wftfv.service.dto.WfrequestDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Wfrequest.
 */
public interface WfrequestService {

    /**
     * Save a wfrequest.
     *
     * @param wfrequestDTO the entity to save
     * @return the persisted entity
     */
    WfrequestDTO save(WfrequestDTO wfrequestDTO);

    /**
     * Get all the wfrequests.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<WfrequestDTO> findAll(Pageable pageable);


    /**
     * Get the "id" wfrequest.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<WfrequestDTO> findOne(Long id);

    /**
     * Delete the "id" wfrequest.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
