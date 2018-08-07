package com.wellsfargo.taxie.recipienthelper.web.rest;

import com.wellsfargo.taxie.recipienthelper.RecipienthelperApp;

import com.wellsfargo.taxie.recipienthelper.domain.Recipient;
import com.wellsfargo.taxie.recipienthelper.repository.RecipientRepository;
import com.wellsfargo.taxie.recipienthelper.service.RecipientService;
import com.wellsfargo.taxie.recipienthelper.service.dto.RecipientDTO;
import com.wellsfargo.taxie.recipienthelper.service.mapper.RecipientMapper;
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
import java.util.List;


import static com.wellsfargo.taxie.recipienthelper.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the RecipientResource REST controller.
 *
 * @see RecipientResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RecipienthelperApp.class)
public class RecipientResourceIntTest {

    private static final String DEFAULT_REC_SRC = "AAAAAAAAAA";
    private static final String UPDATED_REC_SRC = "BBBBBBBBBB";

    private static final Integer DEFAULT_LOB_ID = 1;
    private static final Integer UPDATED_LOB_ID = 2;

    private static final Integer DEFAULT_SOR_ID = 1;
    private static final Integer UPDATED_SOR_ID = 2;

    private static final Integer DEFAULT_SUB_LOB_ID = 1;
    private static final Integer UPDATED_SUB_LOB_ID = 2;

    private static final String DEFAULT_SOR_RCPNT_ID = "AAAAAAAAAA";
    private static final String UPDATED_SOR_RCPNT_ID = "BBBBBBBBBB";

    private static final Integer DEFAULT_RCPNT_TYPE = 1;
    private static final Integer UPDATED_RCPNT_TYPE = 2;

    @Autowired
    private RecipientRepository recipientRepository;


    @Autowired
    private RecipientMapper recipientMapper;
    

    @Autowired
    private RecipientService recipientService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restRecipientMockMvc;

    private Recipient recipient;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final RecipientResource recipientResource = new RecipientResource(recipientService);
        this.restRecipientMockMvc = MockMvcBuilders.standaloneSetup(recipientResource)
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
    public static Recipient createEntity(EntityManager em) {
        Recipient recipient = new Recipient()
            .rec_src(DEFAULT_REC_SRC)
            .lob_id(DEFAULT_LOB_ID)
            .sor_id(DEFAULT_SOR_ID)
            .sub_lob_id(DEFAULT_SUB_LOB_ID)
            .sor_rcpnt_id(DEFAULT_SOR_RCPNT_ID)
            .rcpnt_type(DEFAULT_RCPNT_TYPE);
        return recipient;
    }

    @Before
    public void initTest() {
        recipient = createEntity(em);
    }

    @Test
    @Transactional
    public void createRecipient() throws Exception {
        int databaseSizeBeforeCreate = recipientRepository.findAll().size();

        // Create the Recipient
        RecipientDTO recipientDTO = recipientMapper.toDto(recipient);
        restRecipientMockMvc.perform(post("/api/recipients")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(recipientDTO)))
            .andExpect(status().isCreated());

        // Validate the Recipient in the database
        List<Recipient> recipientList = recipientRepository.findAll();
        assertThat(recipientList).hasSize(databaseSizeBeforeCreate + 1);
        Recipient testRecipient = recipientList.get(recipientList.size() - 1);
        assertThat(testRecipient.getRec_src()).isEqualTo(DEFAULT_REC_SRC);
        assertThat(testRecipient.getLob_id()).isEqualTo(DEFAULT_LOB_ID);
        assertThat(testRecipient.getSor_id()).isEqualTo(DEFAULT_SOR_ID);
        assertThat(testRecipient.getSub_lob_id()).isEqualTo(DEFAULT_SUB_LOB_ID);
        assertThat(testRecipient.getSor_rcpnt_id()).isEqualTo(DEFAULT_SOR_RCPNT_ID);
        assertThat(testRecipient.getRcpnt_type()).isEqualTo(DEFAULT_RCPNT_TYPE);
    }

    @Test
    @Transactional
    public void createRecipientWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = recipientRepository.findAll().size();

        // Create the Recipient with an existing ID
        recipient.setId(1L);
        RecipientDTO recipientDTO = recipientMapper.toDto(recipient);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRecipientMockMvc.perform(post("/api/recipients")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(recipientDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Recipient in the database
        List<Recipient> recipientList = recipientRepository.findAll();
        assertThat(recipientList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkLob_idIsRequired() throws Exception {
        int databaseSizeBeforeTest = recipientRepository.findAll().size();
        // set the field null
        recipient.setLob_id(null);

        // Create the Recipient, which fails.
        RecipientDTO recipientDTO = recipientMapper.toDto(recipient);

        restRecipientMockMvc.perform(post("/api/recipients")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(recipientDTO)))
            .andExpect(status().isBadRequest());

        List<Recipient> recipientList = recipientRepository.findAll();
        assertThat(recipientList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSor_idIsRequired() throws Exception {
        int databaseSizeBeforeTest = recipientRepository.findAll().size();
        // set the field null
        recipient.setSor_id(null);

        // Create the Recipient, which fails.
        RecipientDTO recipientDTO = recipientMapper.toDto(recipient);

        restRecipientMockMvc.perform(post("/api/recipients")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(recipientDTO)))
            .andExpect(status().isBadRequest());

        List<Recipient> recipientList = recipientRepository.findAll();
        assertThat(recipientList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSub_lob_idIsRequired() throws Exception {
        int databaseSizeBeforeTest = recipientRepository.findAll().size();
        // set the field null
        recipient.setSub_lob_id(null);

        // Create the Recipient, which fails.
        RecipientDTO recipientDTO = recipientMapper.toDto(recipient);

        restRecipientMockMvc.perform(post("/api/recipients")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(recipientDTO)))
            .andExpect(status().isBadRequest());

        List<Recipient> recipientList = recipientRepository.findAll();
        assertThat(recipientList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkRcpnt_typeIsRequired() throws Exception {
        int databaseSizeBeforeTest = recipientRepository.findAll().size();
        // set the field null
        recipient.setRcpnt_type(null);

        // Create the Recipient, which fails.
        RecipientDTO recipientDTO = recipientMapper.toDto(recipient);

        restRecipientMockMvc.perform(post("/api/recipients")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(recipientDTO)))
            .andExpect(status().isBadRequest());

        List<Recipient> recipientList = recipientRepository.findAll();
        assertThat(recipientList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllRecipients() throws Exception {
        // Initialize the database
        recipientRepository.saveAndFlush(recipient);

        // Get all the recipientList
        restRecipientMockMvc.perform(get("/api/recipients?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(recipient.getId().intValue())))
            .andExpect(jsonPath("$.[*].rec_src").value(hasItem(DEFAULT_REC_SRC.toString())))
            .andExpect(jsonPath("$.[*].lob_id").value(hasItem(DEFAULT_LOB_ID)))
            .andExpect(jsonPath("$.[*].sor_id").value(hasItem(DEFAULT_SOR_ID)))
            .andExpect(jsonPath("$.[*].sub_lob_id").value(hasItem(DEFAULT_SUB_LOB_ID)))
            .andExpect(jsonPath("$.[*].sor_rcpnt_id").value(hasItem(DEFAULT_SOR_RCPNT_ID.toString())))
            .andExpect(jsonPath("$.[*].rcpnt_type").value(hasItem(DEFAULT_RCPNT_TYPE)));
    }
    

    @Test
    @Transactional
    public void getRecipient() throws Exception {
        // Initialize the database
        recipientRepository.saveAndFlush(recipient);

        // Get the recipient
        restRecipientMockMvc.perform(get("/api/recipients/{id}", recipient.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(recipient.getId().intValue()))
            .andExpect(jsonPath("$.rec_src").value(DEFAULT_REC_SRC.toString()))
            .andExpect(jsonPath("$.lob_id").value(DEFAULT_LOB_ID))
            .andExpect(jsonPath("$.sor_id").value(DEFAULT_SOR_ID))
            .andExpect(jsonPath("$.sub_lob_id").value(DEFAULT_SUB_LOB_ID))
            .andExpect(jsonPath("$.sor_rcpnt_id").value(DEFAULT_SOR_RCPNT_ID.toString()))
            .andExpect(jsonPath("$.rcpnt_type").value(DEFAULT_RCPNT_TYPE));
    }
    @Test
    @Transactional
    public void getNonExistingRecipient() throws Exception {
        // Get the recipient
        restRecipientMockMvc.perform(get("/api/recipients/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRecipient() throws Exception {
        // Initialize the database
        recipientRepository.saveAndFlush(recipient);

        int databaseSizeBeforeUpdate = recipientRepository.findAll().size();

        // Update the recipient
        Recipient updatedRecipient = recipientRepository.findById(recipient.getId()).get();
        // Disconnect from session so that the updates on updatedRecipient are not directly saved in db
        em.detach(updatedRecipient);
        updatedRecipient
            .rec_src(UPDATED_REC_SRC)
            .lob_id(UPDATED_LOB_ID)
            .sor_id(UPDATED_SOR_ID)
            .sub_lob_id(UPDATED_SUB_LOB_ID)
            .sor_rcpnt_id(UPDATED_SOR_RCPNT_ID)
            .rcpnt_type(UPDATED_RCPNT_TYPE);
        RecipientDTO recipientDTO = recipientMapper.toDto(updatedRecipient);

        restRecipientMockMvc.perform(put("/api/recipients")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(recipientDTO)))
            .andExpect(status().isOk());

        // Validate the Recipient in the database
        List<Recipient> recipientList = recipientRepository.findAll();
        assertThat(recipientList).hasSize(databaseSizeBeforeUpdate);
        Recipient testRecipient = recipientList.get(recipientList.size() - 1);
        assertThat(testRecipient.getRec_src()).isEqualTo(UPDATED_REC_SRC);
        assertThat(testRecipient.getLob_id()).isEqualTo(UPDATED_LOB_ID);
        assertThat(testRecipient.getSor_id()).isEqualTo(UPDATED_SOR_ID);
        assertThat(testRecipient.getSub_lob_id()).isEqualTo(UPDATED_SUB_LOB_ID);
        assertThat(testRecipient.getSor_rcpnt_id()).isEqualTo(UPDATED_SOR_RCPNT_ID);
        assertThat(testRecipient.getRcpnt_type()).isEqualTo(UPDATED_RCPNT_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingRecipient() throws Exception {
        int databaseSizeBeforeUpdate = recipientRepository.findAll().size();

        // Create the Recipient
        RecipientDTO recipientDTO = recipientMapper.toDto(recipient);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restRecipientMockMvc.perform(put("/api/recipients")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(recipientDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Recipient in the database
        List<Recipient> recipientList = recipientRepository.findAll();
        assertThat(recipientList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteRecipient() throws Exception {
        // Initialize the database
        recipientRepository.saveAndFlush(recipient);

        int databaseSizeBeforeDelete = recipientRepository.findAll().size();

        // Get the recipient
        restRecipientMockMvc.perform(delete("/api/recipients/{id}", recipient.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Recipient> recipientList = recipientRepository.findAll();
        assertThat(recipientList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Recipient.class);
        Recipient recipient1 = new Recipient();
        recipient1.setId(1L);
        Recipient recipient2 = new Recipient();
        recipient2.setId(recipient1.getId());
        assertThat(recipient1).isEqualTo(recipient2);
        recipient2.setId(2L);
        assertThat(recipient1).isNotEqualTo(recipient2);
        recipient1.setId(null);
        assertThat(recipient1).isNotEqualTo(recipient2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RecipientDTO.class);
        RecipientDTO recipientDTO1 = new RecipientDTO();
        recipientDTO1.setId(1L);
        RecipientDTO recipientDTO2 = new RecipientDTO();
        assertThat(recipientDTO1).isNotEqualTo(recipientDTO2);
        recipientDTO2.setId(recipientDTO1.getId());
        assertThat(recipientDTO1).isEqualTo(recipientDTO2);
        recipientDTO2.setId(2L);
        assertThat(recipientDTO1).isNotEqualTo(recipientDTO2);
        recipientDTO1.setId(null);
        assertThat(recipientDTO1).isNotEqualTo(recipientDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(recipientMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(recipientMapper.fromId(null)).isNull();
    }
}
