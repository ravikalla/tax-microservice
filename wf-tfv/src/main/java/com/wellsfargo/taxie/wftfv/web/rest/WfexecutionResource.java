package com.wellsfargo.taxie.wftfv.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.wellsfargo.taxie.wftfv.service.WfexecutionService;
import com.wellsfargo.taxie.wftfv.web.rest.errors.BadRequestAlertException;
import com.wellsfargo.taxie.wftfv.web.rest.util.HeaderUtil;
import com.wellsfargo.taxie.wftfv.web.rest.util.PaginationUtil;
import com.wellsfargo.taxie.wftfv.service.dto.WfexecutionDTO;
import com.wellsfargo.taxie.wftfv.service.dto.WfexecutionCriteria;
import com.wellsfargo.taxie.wftfv.service.WfexecutionQueryService;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Wfexecution.
 */
@RestController
@RequestMapping("/api")
public class WfexecutionResource {

    private final Logger log = LoggerFactory.getLogger(WfexecutionResource.class);

    private static final String ENTITY_NAME = "wfexecution";

    private final WfexecutionService wfexecutionService;

    private final WfexecutionQueryService wfexecutionQueryService;

    public WfexecutionResource(WfexecutionService wfexecutionService, WfexecutionQueryService wfexecutionQueryService) {
        this.wfexecutionService = wfexecutionService;
        this.wfexecutionQueryService = wfexecutionQueryService;
    }

    /**
     * POST  /wfexecutions : Create a new wfexecution.
     *
     * @param wfexecutionDTO the wfexecutionDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new wfexecutionDTO, or with status 400 (Bad Request) if the wfexecution has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/wfexecutions")
    @Timed
    public ResponseEntity<WfexecutionDTO> createWfexecution(@Valid @RequestBody WfexecutionDTO wfexecutionDTO) throws URISyntaxException {
        log.debug("REST request to save Wfexecution : {}", wfexecutionDTO);
        if (wfexecutionDTO.getId() != null) {
            throw new BadRequestAlertException("A new wfexecution cannot already have an ID", ENTITY_NAME, "idexists");
        }
        WfexecutionDTO result = wfexecutionService.save(wfexecutionDTO);
        return ResponseEntity.created(new URI("/api/wfexecutions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /wfexecutions : Updates an existing wfexecution.
     *
     * @param wfexecutionDTO the wfexecutionDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated wfexecutionDTO,
     * or with status 400 (Bad Request) if the wfexecutionDTO is not valid,
     * or with status 500 (Internal Server Error) if the wfexecutionDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/wfexecutions")
    @Timed
    public ResponseEntity<WfexecutionDTO> updateWfexecution(@Valid @RequestBody WfexecutionDTO wfexecutionDTO) throws URISyntaxException {
        log.debug("REST request to update Wfexecution : {}", wfexecutionDTO);
        if (wfexecutionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        WfexecutionDTO result = wfexecutionService.save(wfexecutionDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, wfexecutionDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /wfexecutions : get all the wfexecutions.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of wfexecutions in body
     */
    @GetMapping("/wfexecutions")
    @Timed
    public ResponseEntity<List<WfexecutionDTO>> getAllWfexecutions(WfexecutionCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Wfexecutions by criteria: {}", criteria);
        Page<WfexecutionDTO> page = wfexecutionQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/wfexecutions");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /wfexecutions/:id : get the "id" wfexecution.
     *
     * @param id the id of the wfexecutionDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the wfexecutionDTO, or with status 404 (Not Found)
     */
    @GetMapping("/wfexecutions/{id}")
    @Timed
    public ResponseEntity<WfexecutionDTO> getWfexecution(@PathVariable Long id) {
        log.debug("REST request to get Wfexecution : {}", id);
        Optional<WfexecutionDTO> wfexecutionDTO = wfexecutionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(wfexecutionDTO);
    }

    /**
     * DELETE  /wfexecutions/:id : delete the "id" wfexecution.
     *
     * @param id the id of the wfexecutionDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/wfexecutions/{id}")
    @Timed
    public ResponseEntity<Void> deleteWfexecution(@PathVariable Long id) {
        log.debug("REST request to delete Wfexecution : {}", id);
        wfexecutionService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
