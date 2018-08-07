package com.wellsfargo.taxie.wftfv.service;

import com.wellsfargo.taxie.wftfv.service.dto.WfexecutionDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Wfexecution.
 */
public interface WfexecutionService {

    /**
     * Save a wfexecution.
     *
     * @param wfexecutionDTO the entity to save
     * @return the persisted entity
     */
    WfexecutionDTO save(WfexecutionDTO wfexecutionDTO);

    /**
     * Get all the wfexecutions.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<WfexecutionDTO> findAll(Pageable pageable);


    /**
     * Get the "id" wfexecution.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<WfexecutionDTO> findOne(Long id);

    /**
     * Delete the "id" wfexecution.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
