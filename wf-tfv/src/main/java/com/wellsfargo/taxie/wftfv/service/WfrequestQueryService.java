package com.wellsfargo.taxie.wftfv.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import com.wellsfargo.taxie.wftfv.domain.Wfrequest;
import com.wellsfargo.taxie.wftfv.domain.*; // for static metamodels
import com.wellsfargo.taxie.wftfv.repository.WfrequestRepository;
import com.wellsfargo.taxie.wftfv.service.dto.WfrequestCriteria;

import com.wellsfargo.taxie.wftfv.service.dto.WfrequestDTO;
import com.wellsfargo.taxie.wftfv.service.mapper.WfrequestMapper;

/**
 * Service for executing complex queries for Wfrequest entities in the database.
 * The main input is a {@link WfrequestCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link WfrequestDTO} or a {@link Page} of {@link WfrequestDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class WfrequestQueryService extends QueryService<Wfrequest> {

    private final Logger log = LoggerFactory.getLogger(WfrequestQueryService.class);

    private final WfrequestRepository wfrequestRepository;

    private final WfrequestMapper wfrequestMapper;

    public WfrequestQueryService(WfrequestRepository wfrequestRepository, WfrequestMapper wfrequestMapper) {
        this.wfrequestRepository = wfrequestRepository;
        this.wfrequestMapper = wfrequestMapper;
    }

    /**
     * Return a {@link List} of {@link WfrequestDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<WfrequestDTO> findByCriteria(WfrequestCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Wfrequest> specification = createSpecification(criteria);
        return wfrequestMapper.toDto(wfrequestRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link WfrequestDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<WfrequestDTO> findByCriteria(WfrequestCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Wfrequest> specification = createSpecification(criteria);
        return wfrequestRepository.findAll(specification, page)
            .map(wfrequestMapper::toDto);
    }

    /**
     * Function to convert WfrequestCriteria to a {@link Specification}
     */
    private Specification<Wfrequest> createSpecification(WfrequestCriteria criteria) {
        Specification<Wfrequest> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Wfrequest_.id));
            }
            if (criteria.getCoe_rcpnt_id() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCoe_rcpnt_id(), Wfrequest_.coe_rcpnt_id));
            }
            if (criteria.getWrkfl_execution_id() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getWrkfl_execution_id(), Wfrequest_.wrkfl_execution_id));
            }
            if (criteria.getCurrent_task_id() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCurrent_task_id(), Wfrequest_.current_task_id));
            }
            if (criteria.getCurrent_task_stat() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCurrent_task_stat(), Wfrequest_.current_task_stat));
            }
            if (criteria.getRequest_stat() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getRequest_stat(), Wfrequest_.request_stat));
            }
            if (criteria.getStart_dt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getStart_dt(), Wfrequest_.start_dt));
            }
            if (criteria.getEnd_dt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEnd_dt(), Wfrequest_.end_dt));
            }
        }
        return specification;
    }

}
