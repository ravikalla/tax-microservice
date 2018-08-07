package com.wellsfargo.taxie.wftfv.service.impl;

import com.wellsfargo.taxie.wftfv.service.WfrequestService;
import com.wellsfargo.taxie.wftfv.domain.Wfrequest;
import com.wellsfargo.taxie.wftfv.repository.WfrequestRepository;
import com.wellsfargo.taxie.wftfv.service.dto.WfrequestDTO;
import com.wellsfargo.taxie.wftfv.service.mapper.WfrequestMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Optional;
/**
 * Service Implementation for managing Wfrequest.
 */
@Service
@Transactional
public class WfrequestServiceImpl implements WfrequestService {

    private final Logger log = LoggerFactory.getLogger(WfrequestServiceImpl.class);

    private final WfrequestRepository wfrequestRepository;

    private final WfrequestMapper wfrequestMapper;

    public WfrequestServiceImpl(WfrequestRepository wfrequestRepository, WfrequestMapper wfrequestMapper) {
        this.wfrequestRepository = wfrequestRepository;
        this.wfrequestMapper = wfrequestMapper;
    }

    /**
     * Save a wfrequest.
     *
     * @param wfrequestDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public WfrequestDTO save(WfrequestDTO wfrequestDTO) {
        log.debug("Request to save Wfrequest : {}", wfrequestDTO);
        Wfrequest wfrequest = wfrequestMapper.toEntity(wfrequestDTO);
        wfrequest = wfrequestRepository.save(wfrequest);
        return wfrequestMapper.toDto(wfrequest);
    }

    /**
     * Get all the wfrequests.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<WfrequestDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Wfrequests");
        return wfrequestRepository.findAll(pageable)
            .map(wfrequestMapper::toDto);
    }


    /**
     * Get one wfrequest by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<WfrequestDTO> findOne(Long id) {
        log.debug("Request to get Wfrequest : {}", id);
        return wfrequestRepository.findById(id)
            .map(wfrequestMapper::toDto);
    }

    /**
     * Delete the wfrequest by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Wfrequest : {}", id);
        wfrequestRepository.deleteById(id);
    }
}
