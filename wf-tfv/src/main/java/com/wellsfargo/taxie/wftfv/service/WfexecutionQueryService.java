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

import com.wellsfargo.taxie.wftfv.domain.Wfexecution;
import com.wellsfargo.taxie.wftfv.domain.*; // for static metamodels
import com.wellsfargo.taxie.wftfv.repository.WfexecutionRepository;
import com.wellsfargo.taxie.wftfv.service.dto.WfexecutionCriteria;

import com.wellsfargo.taxie.wftfv.service.dto.WfexecutionDTO;
import com.wellsfargo.taxie.wftfv.service.mapper.WfexecutionMapper;

/**
 * Service for executing complex queries for Wfexecution entities in the database.
 * The main input is a {@link WfexecutionCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link WfexecutionDTO} or a {@link Page} of {@link WfexecutionDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class WfexecutionQueryService extends QueryService<Wfexecution> {

    private final Logger log = LoggerFactory.getLogger(WfexecutionQueryService.class);

    private final WfexecutionRepository wfexecutionRepository;

    private final WfexecutionMapper wfexecutionMapper;

    public WfexecutionQueryService(WfexecutionRepository wfexecutionRepository, WfexecutionMapper wfexecutionMapper) {
        this.wfexecutionRepository = wfexecutionRepository;
        this.wfexecutionMapper = wfexecutionMapper;
    }

    /**
     * Return a {@link List} of {@link WfexecutionDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<WfexecutionDTO> findByCriteria(WfexecutionCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Wfexecution> specification = createSpecification(criteria);
        return wfexecutionMapper.toDto(wfexecutionRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link WfexecutionDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<WfexecutionDTO> findByCriteria(WfexecutionCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Wfexecution> specification = createSpecification(criteria);
        return wfexecutionRepository.findAll(specification, page)
            .map(wfexecutionMapper::toDto);
    }

    /**
     * Function to convert WfexecutionCriteria to a {@link Specification}
     */
    private Specification<Wfexecution> createSpecification(WfexecutionCriteria criteria) {
        Specification<Wfexecution> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Wfexecution_.id));
            }
            if (criteria.getBpm_instance_id() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getBpm_instance_id(), Wfexecution_.bpm_instance_id));
            }
            if (criteria.getBpm_wrkfl_task_id() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getBpm_wrkfl_task_id(), Wfexecution_.bpm_wrkfl_task_id));
            }
            if (criteria.getKey_value() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getKey_value(), Wfexecution_.key_value));
            }
            if (criteria.getSla() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSla(), Wfexecution_.sla));
            }
            if (criteria.getWfrequestId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getWfrequestId(), Wfexecution_.wfrequest, Wfrequest_.id));
            }
        }
        return specification;
    }

}
