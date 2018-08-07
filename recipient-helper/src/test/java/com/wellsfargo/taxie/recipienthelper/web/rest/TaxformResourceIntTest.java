package com.wellsfargo.taxie.recipienthelper.web.rest;

import com.wellsfargo.taxie.recipienthelper.RecipienthelperApp;

import com.wellsfargo.taxie.recipienthelper.domain.Taxform;
import com.wellsfargo.taxie.recipienthelper.repository.TaxformRepository;
import com.wellsfargo.taxie.recipienthelper.service.TaxformService;
import com.wellsfargo.taxie.recipienthelper.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;


import static com.wellsfargo.taxie.recipienthelper.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the TaxformResource REST controller.
 *
 * @see TaxformResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RecipienthelperApp.class)
public class TaxformResourceIntTest {

    private static final Integer DEFAULT_COE_RCPNT_ID = 1;
    private static final Integer UPDATED_COE_RCPNT_ID = 2;

    private static final String DEFAULT_SOR_RCPNT_ID = "AAAAAAAAAA";
    private static final String UPDATED_SOR_RCPNT_ID = "BBBBBBBBBB";

    private static final Integer DEFAULT_LOB_ID = 1;
    private static final Integer UPDATED_LOB_ID = 2;

    private static final Integer DEFAULT_SOR_ID = 1;
    private static final Integer UPDATED_SOR_ID = 2;

    private static final Integer DEFAULT_SUB_LOB_ID = 1;
    private static final Integer UPDATED_SUB_LOB_ID = 2;

    private static final Integer DEFAULT_FORM_TYPE = 1;
    private static final Integer UPDATED_FORM_TYPE = 2;

    private static final LocalDate DEFAULT_FORM_EFFECTIVE_DT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FORM_EFFECTIVE_DT = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_AFFIDAVIT_DT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_AFFIDAVIT_DT = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private TaxformRepository taxformRepository;

    

    @Autowired
    private TaxformService taxformService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTaxformMockMvc;

    private Taxform taxform;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TaxformResource taxformResource = new TaxformResource(taxformService);
        this.restTaxformMockMvc = MockMvcBuilders.standaloneSetup(taxformResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Taxform createEntity(EntityManager em) {
        Taxform taxform = new Taxform()
            .coe_rcpnt_id(DEFAULT_COE_RCPNT_ID)
            .sor_rcpnt_id(DEFAULT_SOR_RCPNT_ID)
            .lob_id(DEFAULT_LOB_ID)
            .sor_id(DEFAULT_SOR_ID)
            .sub_lob_id(DEFAULT_SUB_LOB_ID)
            .form_type(DEFAULT_FORM_TYPE)
            .form_effective_dt(DEFAULT_FORM_EFFECTIVE_DT)
            .affidavit_dt(DEFAULT_AFFIDAVIT_DT);
        return taxform;
    }

    @Before
    public void initTest() {
        taxform = createEntity(em);
    }

    @Test
    @Transactional
    public void createTaxform() throws Exception {
        int databaseSizeBeforeCreate = taxformRepository.findAll().size();

        // Create the Taxform
        restTaxformMockMvc.perform(post("/api/taxforms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(taxform)))
            .andExpect(status().isCreated());

        // Validate the Taxform in the database
        List<Taxform> taxformList = taxformRepository.findAll();
        assertThat(taxformList).hasSize(databaseSizeBeforeCreate + 1);
        Taxform testTaxform = taxformList.get(taxformList.size() - 1);
        assertThat(testTaxform.getCoe_rcpnt_id()).isEqualTo(DEFAULT_COE_RCPNT_ID);
        assertThat(testTaxform.getSor_rcpnt_id()).isEqualTo(DEFAULT_SOR_RCPNT_ID);
        assertThat(testTaxform.getLob_id()).isEqualTo(DEFAULT_LOB_ID);
        assertThat(testTaxform.getSor_id()).isEqualTo(DEFAULT_SOR_ID);
        assertThat(testTaxform.getSub_lob_id()).isEqualTo(DEFAULT_SUB_LOB_ID);
        assertThat(testTaxform.getForm_type()).isEqualTo(DEFAULT_FORM_TYPE);
        assertThat(testTaxform.getForm_effective_dt()).isEqualTo(DEFAULT_FORM_EFFECTIVE_DT);
        assertThat(testTaxform.getAffidavit_dt()).isEqualTo(DEFAULT_AFFIDAVIT_DT);
    }

    @Test
    @Transactional
    public void createTaxformWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = taxformRepository.findAll().size();

        // Create the Taxform with an existing ID
        taxform.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTaxformMockMvc.perform(post("/api/taxforms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(taxform)))
            .andExpect(status().isBadRequest());

        // Validate the Taxform in the database
        List<Taxform> taxformList = taxformRepository.findAll();
        assertThat(taxformList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllTaxforms() throws Exception {
        // Initialize the database
        taxformRepository.saveAndFlush(taxform);

        // Get all the taxformList
        restTaxformMockMvc.perform(get("/api/taxforms?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(taxform.getId().intValue())))
            .andExpect(jsonPath("$.[*].coe_rcpnt_id").value(hasItem(DEFAULT_COE_RCPNT_ID)))
            .andExpect(jsonPath("$.[*].sor_rcpnt_id").value(hasItem(DEFAULT_SOR_RCPNT_ID.toString())))
            .andExpect(jsonPath("$.[*].lob_id").value(hasItem(DEFAULT_LOB_ID)))
            .andExpect(jsonPath("$.[*].sor_id").value(hasItem(DEFAULT_SOR_ID)))
            .andExpect(jsonPath("$.[*].sub_lob_id").value(hasItem(DEFAULT_SUB_LOB_ID)))
            .andExpect(jsonPath("$.[*].form_type").value(hasItem(DEFAULT_FORM_TYPE)))
            .andExpect(jsonPath("$.[*].form_effective_dt").value(hasItem(DEFAULT_FORM_EFFECTIVE_DT.toString())))
            .andExpect(jsonPath("$.[*].affidavit_dt").value(hasItem(DEFAULT_AFFIDAVIT_DT.toString())));
    }
    

    @Test
    @Transactional
    public void getTaxform() throws Exception {
        // Initialize the database
        taxformRepository.saveAndFlush(taxform);

        // Get the taxform
        restTaxformMockMvc.perform(get("/api/taxforms/{id}", taxform.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(taxform.getId().intValue()))
            .andExpect(jsonPath("$.coe_rcpnt_id").value(DEFAULT_COE_RCPNT_ID))
            .andExpect(jsonPath("$.sor_rcpnt_id").value(DEFAULT_SOR_RCPNT_ID.toString()))
            .andExpect(jsonPath("$.lob_id").value(DEFAULT_LOB_ID))
            .andExpect(jsonPath("$.sor_id").value(DEFAULT_SOR_ID))
            .andExpect(jsonPath("$.sub_lob_id").value(DEFAULT_SUB_LOB_ID))
            .andExpect(jsonPath("$.form_type").value(DEFAULT_FORM_TYPE))
            .andExpect(jsonPath("$.form_effective_dt").value(DEFAULT_FORM_EFFECTIVE_DT.toString()))
            .andExpect(jsonPath("$.affidavit_dt").value(DEFAULT_AFFIDAVIT_DT.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingTaxform() throws Exception {
        // Get the taxform
        restTaxformMockMvc.perform(get("/api/taxforms/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTaxform() throws Exception {
        // Initialize the database
        taxformService.save(taxform);

        int databaseSizeBeforeUpdate = taxformRepository.findAll().size();

        // Update the taxform
        Taxform updatedTaxform = taxformRepository.findById(taxform.getId()).get();
        // Disconnect from session so that the updates on updatedTaxform are not directly saved in db
        em.detach(updatedTaxform);
        updatedTaxform
            .coe_rcpnt_id(UPDATED_COE_RCPNT_ID)
            .sor_rcpnt_id(UPDATED_SOR_RCPNT_ID)
            .lob_id(UPDATED_LOB_ID)
            .sor_id(UPDATED_SOR_ID)
            .sub_lob_id(UPDATED_SUB_LOB_ID)
            .form_type(UPDATED_FORM_TYPE)
            .form_effective_dt(UPDATED_FORM_EFFECTIVE_DT)
            .affidavit_dt(UPDATED_AFFIDAVIT_DT);

        restTaxformMockMvc.perform(put("/api/taxforms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTaxform)))
            .andExpect(status().isOk());

        // Validate the Taxform in the database
        List<Taxform> taxformList = taxformRepository.findAll();
        assertThat(taxformList).hasSize(databaseSizeBeforeUpdate);
        Taxform testTaxform = taxformList.get(taxformList.size() - 1);
        assertThat(testTaxform.getCoe_rcpnt_id()).isEqualTo(UPDATED_COE_RCPNT_ID);
        assertThat(testTaxform.getSor_rcpnt_id()).isEqualTo(UPDATED_SOR_RCPNT_ID);
        assertThat(testTaxform.getLob_id()).isEqualTo(UPDATED_LOB_ID);
        assertThat(testTaxform.getSor_id()).isEqualTo(UPDATED_SOR_ID);
        assertThat(testTaxform.getSub_lob_id()).isEqualTo(UPDATED_SUB_LOB_ID);
        assertThat(testTaxform.getForm_type()).isEqualTo(UPDATED_FORM_TYPE);
        assertThat(testTaxform.getForm_effective_dt()).isEqualTo(UPDATED_FORM_EFFECTIVE_DT);
        assertThat(testTaxform.getAffidavit_dt()).isEqualTo(UPDATED_AFFIDAVIT_DT);
    }

    @Test
    @Transactional
    public void updateNonExistingTaxform() throws Exception {
        int databaseSizeBeforeUpdate = taxformRepository.findAll().size();

        // Create the Taxform

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTaxformMockMvc.perform(put("/api/taxforms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(taxform)))
            .andExpect(status().isBadRequest());

        // Validate the Taxform in the database
        List<Taxform> taxformList = taxformRepository.findAll();
        assertThat(taxformList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTaxform() throws Exception {
        // Initialize the database
        taxformService.save(taxform);

        int databaseSizeBeforeDelete = taxformRepository.findAll().size();

        // Get the taxform
        restTaxformMockMvc.perform(delete("/api/taxforms/{id}", taxform.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Taxform> taxformList = taxformRepository.findAll();
        assertThat(taxformList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Taxform.class);
        Taxform taxform1 = new Taxform();
        taxform1.setId(1L);
        Taxform taxform2 = new Taxform();
        taxform2.setId(taxform1.getId());
        assertThat(taxform1).isEqualTo(taxform2);
        taxform2.setId(2L);
        assertThat(taxform1).isNotEqualTo(taxform2);
        taxform1.setId(null);
        assertThat(taxform1).isNotEqualTo(taxform2);
    }
}
