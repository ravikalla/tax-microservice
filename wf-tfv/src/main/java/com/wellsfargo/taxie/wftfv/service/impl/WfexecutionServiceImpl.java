package com.wellsfargo.taxie.wftfv.service.impl;

import com.wellsfargo.taxie.wftfv.service.WfexecutionService;
import com.wellsfargo.taxie.wftfv.domain.Wfexecution;
import com.wellsfargo.taxie.wftfv.repository.WfexecutionRepository;
import com.wellsfargo.taxie.wftfv.service.dto.WfexecutionDTO;
import com.wellsfargo.taxie.wftfv.service.mapper.WfexecutionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Optional;
/**
 * Service Implementation for managing Wfexecution.
 */
@Service
@Transactional
public class WfexecutionServiceImpl implements WfexecutionService {

    private final Logger log = LoggerFactory.getLogger(WfexecutionServiceImpl.class);

    private final WfexecutionRepository wfexecutionRepository;

    private final WfexecutionMapper wfexecutionMapper;

    public WfexecutionServiceImpl(WfexecutionRepository wfexecutionRepository, WfexecutionMapper wfexecutionMapper) {
        this.wfexecutionRepository = wfexecutionRepository;
        this.wfexecutionMapper = wfexecutionMapper;
    }

    /**
     * Save a wfexecution.
     *
     * @param wfexecutionDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public WfexecutionDTO save(WfexecutionDTO wfexecutionDTO) {
        log.debug("Request to save Wfexecution : {}", wfexecutionDTO);
        Wfexecution wfexecution = wfexecutionMapper.toEntity(wfexecutionDTO);
        wfexecution = wfexecutionRepository.save(wfexecution);
        return wfexecutionMapper.toDto(wfexecution);
    }

    /**
     * Get all the wfexecutions.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<WfexecutionDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Wfexecutions");
        return wfexecutionRepository.findAll(pageable)
            .map(wfexecutionMapper::toDto);
    }


    /**
     * Get one wfexecution by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<WfexecutionDTO> findOne(Long id) {
        log.debug("Request to get Wfexecution : {}", id);
        return wfexecutionRepository.findById(id)
            .map(wfexecutionMapper::toDto);
    }

    /**
     * Delete the wfexecution by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Wfexecution : {}", id);
        wfexecutionRepository.deleteById(id);
    }
}
