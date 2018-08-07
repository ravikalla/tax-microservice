package com.wellsfargo.taxie.recipienthelper.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.wellsfargo.taxie.recipienthelper.domain.Taxform;
import com.wellsfargo.taxie.recipienthelper.service.TaxformService;
import com.wellsfargo.taxie.recipienthelper.web.rest.errors.BadRequestAlertException;
import com.wellsfargo.taxie.recipienthelper.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Taxform.
 */
@RestController
@RequestMapping("/api")
public class TaxformResource {

    private final Logger log = LoggerFactory.getLogger(TaxformResource.class);

    private static final String ENTITY_NAME = "taxform";

    private final TaxformService taxformService;

    public TaxformResource(TaxformService taxformService) {
        this.taxformService = taxformService;
    }

    /**
     * POST  /taxforms : Create a new taxform.
     *
     * @param taxform the taxform to create
     * @return the ResponseEntity with status 201 (Created) and with body the new taxform, or with status 400 (Bad Request) if the taxform has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/taxforms")
    @Timed
    public ResponseEntity<Taxform> createTaxform(@RequestBody Taxform taxform) throws URISyntaxException {
        log.debug("REST request to save Taxform : {}", taxform);
        if (taxform.getId() != null) {
            throw new BadRequestAlertException("A new taxform cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Taxform result = taxformService.save(taxform);
        return ResponseEntity.created(new URI("/api/taxforms/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /taxforms : Updates an existing taxform.
     *
     * @param taxform the taxform to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated taxform,
     * or with status 400 (Bad Request) if the taxform is not valid,
     * or with status 500 (Internal Server Error) if the taxform couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/taxforms")
    @Timed
    public ResponseEntity<Taxform> updateTaxform(@RequestBody Taxform taxform) throws URISyntaxException {
        log.debug("REST request to update Taxform : {}", taxform);
        if (taxform.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Taxform result = taxformService.save(taxform);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, taxform.getId().toString()))
            .body(result);
    }

    /**
     * GET  /taxforms : get all the taxforms.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of taxforms in body
     */
    @GetMapping("/taxforms")
    @Timed
    public List<Taxform> getAllTaxforms() {
        log.debug("REST request to get all Taxforms");
        return taxformService.findAll();
    }

    /**
     * GET  /taxforms/:id : get the "id" taxform.
     *
     * @param id the id of the taxform to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the taxform, or with status 404 (Not Found)
     */
    @GetMapping("/taxforms/{id}")
    @Timed
    public ResponseEntity<Taxform> getTaxform(@PathVariable Long id) {
        log.debug("REST request to get Taxform : {}", id);
        Optional<Taxform> taxform = taxformService.findOne(id);
        return ResponseUtil.wrapOrNotFound(taxform);
    }

    /**
     * DELETE  /taxforms/:id : delete the "id" taxform.
     *
     * @param id the id of the taxform to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/taxforms/{id}")
    @Timed
    public ResponseEntity<Void> deleteTaxform(@PathVariable Long id) {
        log.debug("REST request to delete Taxform : {}", id);
        taxformService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
