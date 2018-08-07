package com.wellsfargo.taxie.recipienthelper.service.impl;

import com.wellsfargo.taxie.recipienthelper.service.TaxformService;
import com.wellsfargo.taxie.recipienthelper.domain.Taxform;
import com.wellsfargo.taxie.recipienthelper.repository.TaxformRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Optional;
/**
 * Service Implementation for managing Taxform.
 */
@Service
@Transactional
public class TaxformServiceImpl implements TaxformService {

    private final Logger log = LoggerFactory.getLogger(TaxformServiceImpl.class);

    private final TaxformRepository taxformRepository;

    public TaxformServiceImpl(TaxformRepository taxformRepository) {
        this.taxformRepository = taxformRepository;
    }

    /**
     * Save a taxform.
     *
     * @param taxform the entity to save
     * @return the persisted entity
     */
    @Override
    public Taxform save(Taxform taxform) {
        log.debug("Request to save Taxform : {}", taxform);        return taxformRepository.save(taxform);
    }

    /**
     * Get all the taxforms.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<Taxform> findAll() {
        log.debug("Request to get all Taxforms");
        return taxformRepository.findAll();
    }


    /**
     * Get one taxform by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Taxform> findOne(Long id) {
        log.debug("Request to get Taxform : {}", id);
        return taxformRepository.findById(id);
    }

    /**
     * Delete the taxform by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Taxform : {}", id);
        taxformRepository.deleteById(id);
    }
}
