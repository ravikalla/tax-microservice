package com.wellsfargo.taxie.wftfv.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.wellsfargo.taxie.wftfv.service.WfrequestService;
import com.wellsfargo.taxie.wftfv.web.rest.errors.BadRequestAlertException;
import com.wellsfargo.taxie.wftfv.web.rest.util.HeaderUtil;
import com.wellsfargo.taxie.wftfv.web.rest.util.PaginationUtil;
import com.wellsfargo.taxie.wftfv.service.dto.WfrequestDTO;
import com.wellsfargo.taxie.wftfv.service.dto.WfrequestCriteria;
import com.wellsfargo.taxie.wftfv.service.WfrequestQueryService;
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
 * REST controller for managing Wfrequest.
 */
@RestController
@RequestMapping("/api")
public class WfrequestResource {

    private final Logger log = LoggerFactory.getLogger(WfrequestResource.class);

    private static final String ENTITY_NAME = "wfrequest";

    private final WfrequestService wfrequestService;

    private final WfrequestQueryService wfrequestQueryService;

    public WfrequestResource(WfrequestService wfrequestService, WfrequestQueryService wfrequestQueryService) {
        this.wfrequestService = wfrequestService;
        this.wfrequestQueryService = wfrequestQueryService;
    }

    /**
     * POST  /wfrequests : Create a new wfrequest.
     *
     * @param wfrequestDTO the wfrequestDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new wfrequestDTO, or with status 400 (Bad Request) if the wfrequest has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/wfrequests")
    @Timed
    public ResponseEntity<WfrequestDTO> createWfrequest(@Valid @RequestBody WfrequestDTO wfrequestDTO) throws URISyntaxException {
        log.debug("REST request to save Wfrequest : {}", wfrequestDTO);
        if (wfrequestDTO.getId() != null) {
            throw new BadRequestAlertException("A new wfrequest cannot already have an ID", ENTITY_NAME, "idexists");
        }
        WfrequestDTO result = wfrequestService.save(wfrequestDTO);
        return ResponseEntity.created(new URI("/api/wfrequests/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /wfrequests : Updates an existing wfrequest.
     *
     * @param wfrequestDTO the wfrequestDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated wfrequestDTO,
     * or with status 400 (Bad Request) if the wfrequestDTO is not valid,
     * or with status 500 (Internal Server Error) if the wfrequestDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/wfrequests")
    @Timed
    public ResponseEntity<WfrequestDTO> updateWfrequest(@Valid @RequestBody WfrequestDTO wfrequestDTO) throws URISyntaxException {
        log.debug("REST request to update Wfrequest : {}", wfrequestDTO);
        if (wfrequestDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        WfrequestDTO result = wfrequestService.save(wfrequestDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, wfrequestDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /wfrequests : get all the wfrequests.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of wfrequests in body
     */
    @GetMapping("/wfrequests")
    @Timed
    public ResponseEntity<List<WfrequestDTO>> getAllWfrequests(WfrequestCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Wfrequests by criteria: {}", criteria);
        Page<WfrequestDTO> page = wfrequestQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/wfrequests");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /wfrequests/:id : get the "id" wfrequest.
     *
     * @param id the id of the wfrequestDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the wfrequestDTO, or with status 404 (Not Found)
     */
    @GetMapping("/wfrequests/{id}")
    @Timed
    public ResponseEntity<WfrequestDTO> getWfrequest(@PathVariable Long id) {
        log.debug("REST request to get Wfrequest : {}", id);
        Optional<WfrequestDTO> wfrequestDTO = wfrequestService.findOne(id);
        return ResponseUtil.wrapOrNotFound(wfrequestDTO);
    }

    /**
     * DELETE  /wfrequests/:id : delete the "id" wfrequest.
     *
     * @param id the id of the wfrequestDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/wfrequests/{id}")
    @Timed
    public ResponseEntity<Void> deleteWfrequest(@PathVariable Long id) {
        log.debug("REST request to delete Wfrequest : {}", id);
        wfrequestService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
