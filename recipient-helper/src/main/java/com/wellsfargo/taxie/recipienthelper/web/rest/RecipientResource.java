package com.wellsfargo.taxie.recipienthelper.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.wellsfargo.taxie.recipienthelper.service.RecipientService;
import com.wellsfargo.taxie.recipienthelper.web.rest.errors.BadRequestAlertException;
import com.wellsfargo.taxie.recipienthelper.web.rest.util.HeaderUtil;
import com.wellsfargo.taxie.recipienthelper.service.dto.RecipientDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Recipient.
 */
@RestController
@RequestMapping("/api")
public class RecipientResource {

    private final Logger log = LoggerFactory.getLogger(RecipientResource.class);

    private static final String ENTITY_NAME = "recipient";

    private final RecipientService recipientService;

    public RecipientResource(RecipientService recipientService) {
        this.recipientService = recipientService;
    }

    /**
     * POST  /recipients : Create a new recipient.
     *
     * @param recipientDTO the recipientDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new recipientDTO, or with status 400 (Bad Request) if the recipient has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/recipients")
    @Timed
    public ResponseEntity<RecipientDTO> createRecipient(@Valid @RequestBody RecipientDTO recipientDTO) throws URISyntaxException {
        log.debug("REST request to save Recipient : {}", recipientDTO);
        if (recipientDTO.getId() != null) {
            throw new BadRequestAlertException("A new recipient cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RecipientDTO result = recipientService.save(recipientDTO);
        return ResponseEntity.created(new URI("/api/recipients/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /recipients : Updates an existing recipient.
     *
     * @param recipientDTO the recipientDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated recipientDTO,
     * or with status 400 (Bad Request) if the recipientDTO is not valid,
     * or with status 500 (Internal Server Error) if the recipientDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/recipients")
    @Timed
    public ResponseEntity<RecipientDTO> updateRecipient(@Valid @RequestBody RecipientDTO recipientDTO) throws URISyntaxException {
        log.debug("REST request to update Recipient : {}", recipientDTO);
        if (recipientDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        RecipientDTO result = recipientService.save(recipientDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, recipientDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /recipients : get all the recipients.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of recipients in body
     */
    @GetMapping("/recipients")
    @Timed
    public List<RecipientDTO> getAllRecipients() {
        log.debug("REST request to get all Recipients");
        return recipientService.findAll();
    }

    /**
     * GET  /recipients/:id : get the "id" recipient.
     *
     * @param id the id of the recipientDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the recipientDTO, or with status 404 (Not Found)
     */
    @GetMapping("/recipients/{id}")
    @Timed
    public ResponseEntity<RecipientDTO> getRecipient(@PathVariable Long id) {
        log.debug("REST request to get Recipient : {}", id);
        Optional<RecipientDTO> recipientDTO = recipientService.findOne(id);
        return ResponseUtil.wrapOrNotFound(recipientDTO);
    }

    /**
     * DELETE  /recipients/:id : delete the "id" recipient.
     *
     * @param id the id of the recipientDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/recipients/{id}")
    @Timed
    public ResponseEntity<Void> deleteRecipient(@PathVariable Long id) {
        log.debug("REST request to delete Recipient : {}", id);
        recipientService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
