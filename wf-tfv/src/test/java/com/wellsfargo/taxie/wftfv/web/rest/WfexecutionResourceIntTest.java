package com.wellsfargo.taxie.wftfv.web.rest;

import com.wellsfargo.taxie.wftfv.WftfvApp;

import com.wellsfargo.taxie.wftfv.domain.Wfexecution;
import com.wellsfargo.taxie.wftfv.domain.Wfrequest;
import com.wellsfargo.taxie.wftfv.repository.WfexecutionRepository;
import com.wellsfargo.taxie.wftfv.service.WfexecutionService;
import com.wellsfargo.taxie.wftfv.service.dto.WfexecutionDTO;
import com.wellsfargo.taxie.wftfv.service.mapper.WfexecutionMapper;
import com.wellsfargo.taxie.wftfv.web.rest.errors.ExceptionTranslator;
import com.wellsfargo.taxie.wftfv.service.dto.WfexecutionCriteria;
import com.wellsfargo.taxie.wftfv.service.WfexecutionQueryService;

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


import static com.wellsfargo.taxie.wftfv.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the WfexecutionResource REST controller.
 *
 * @see WfexecutionResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WftfvApp.class)
public class WfexecutionResourceIntTest {

    private static final Integer DEFAULT_BPM_INSTANCE_ID = 1;
    private static final Integer UPDATED_BPM_INSTANCE_ID = 2;

    private static final Integer DEFAULT_BPM_WRKFL_TASK_ID = 1;
    private static final Integer UPDATED_BPM_WRKFL_TASK_ID = 2;

    private static final Integer DEFAULT_KEY_VALUE = 1;
    private static final Integer UPDATED_KEY_VALUE = 2;

    private static final Integer DEFAULT_SLA = 1;
    private static final Integer UPDATED_SLA = 2;

    @Autowired
    private WfexecutionRepository wfexecutionRepository;


    @Autowired
    private WfexecutionMapper wfexecutionMapper;
    

    @Autowired
    private WfexecutionService wfexecutionService;

    @Autowired
    private WfexecutionQueryService wfexecutionQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restWfexecutionMockMvc;

    private Wfexecution wfexecution;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final WfexecutionResource wfexecutionResource = new WfexecutionResource(wfexecutionService, wfexecutionQueryService);
        this.restWfexecutionMockMvc = MockMvcBuilders.standaloneSetup(wfexecutionResource)
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
    public static Wfexecution createEntity(EntityManager em) {
        Wfexecution wfexecution = new Wfexecution()
            .bpm_instance_id(DEFAULT_BPM_INSTANCE_ID)
            .bpm_wrkfl_task_id(DEFAULT_BPM_WRKFL_TASK_ID)
            .key_value(DEFAULT_KEY_VALUE)
            .sla(DEFAULT_SLA);
        // Add required entity
        Wfrequest wfrequest = WfrequestResourceIntTest.createEntity(em);
        em.persist(wfrequest);
        em.flush();
        wfexecution.setWfrequest(wfrequest);
        return wfexecution;
    }

    @Before
    public void initTest() {
        wfexecution = createEntity(em);
    }

    @Test
    @Transactional
    public void createWfexecution() throws Exception {
        int databaseSizeBeforeCreate = wfexecutionRepository.findAll().size();

        // Create the Wfexecution
        WfexecutionDTO wfexecutionDTO = wfexecutionMapper.toDto(wfexecution);
        restWfexecutionMockMvc.perform(post("/api/wfexecutions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(wfexecutionDTO)))
            .andExpect(status().isCreated());

        // Validate the Wfexecution in the database
        List<Wfexecution> wfexecutionList = wfexecutionRepository.findAll();
        assertThat(wfexecutionList).hasSize(databaseSizeBeforeCreate + 1);
        Wfexecution testWfexecution = wfexecutionList.get(wfexecutionList.size() - 1);
        assertThat(testWfexecution.getBpm_instance_id()).isEqualTo(DEFAULT_BPM_INSTANCE_ID);
        assertThat(testWfexecution.getBpm_wrkfl_task_id()).isEqualTo(DEFAULT_BPM_WRKFL_TASK_ID);
        assertThat(testWfexecution.getKey_value()).isEqualTo(DEFAULT_KEY_VALUE);
        assertThat(testWfexecution.getSla()).isEqualTo(DEFAULT_SLA);
    }

    @Test
    @Transactional
    public void createWfexecutionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = wfexecutionRepository.findAll().size();

        // Create the Wfexecution with an existing ID
        wfexecution.setId(1L);
        WfexecutionDTO wfexecutionDTO = wfexecutionMapper.toDto(wfexecution);

        // An entity with an existing ID cannot be created, so this API call must fail
        restWfexecutionMockMvc.perform(post("/api/wfexecutions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(wfexecutionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Wfexecution in the database
        List<Wfexecution> wfexecutionList = wfexecutionRepository.findAll();
        assertThat(wfexecutionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllWfexecutions() throws Exception {
        // Initialize the database
        wfexecutionRepository.saveAndFlush(wfexecution);

        // Get all the wfexecutionList
        restWfexecutionMockMvc.perform(get("/api/wfexecutions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(wfexecution.getId().intValue())))
            .andExpect(jsonPath("$.[*].bpm_instance_id").value(hasItem(DEFAULT_BPM_INSTANCE_ID)))
            .andExpect(jsonPath("$.[*].bpm_wrkfl_task_id").value(hasItem(DEFAULT_BPM_WRKFL_TASK_ID)))
            .andExpect(jsonPath("$.[*].key_value").value(hasItem(DEFAULT_KEY_VALUE)))
            .andExpect(jsonPath("$.[*].sla").value(hasItem(DEFAULT_SLA)));
    }
    

    @Test
    @Transactional
    public void getWfexecution() throws Exception {
        // Initialize the database
        wfexecutionRepository.saveAndFlush(wfexecution);

        // Get the wfexecution
        restWfexecutionMockMvc.perform(get("/api/wfexecutions/{id}", wfexecution.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(wfexecution.getId().intValue()))
            .andExpect(jsonPath("$.bpm_instance_id").value(DEFAULT_BPM_INSTANCE_ID))
            .andExpect(jsonPath("$.bpm_wrkfl_task_id").value(DEFAULT_BPM_WRKFL_TASK_ID))
            .andExpect(jsonPath("$.key_value").value(DEFAULT_KEY_VALUE))
            .andExpect(jsonPath("$.sla").value(DEFAULT_SLA));
    }

    @Test
    @Transactional
    public void getAllWfexecutionsByBpm_instance_idIsEqualToSomething() throws Exception {
        // Initialize the database
        wfexecutionRepository.saveAndFlush(wfexecution);

        // Get all the wfexecutionList where bpm_instance_id equals to DEFAULT_BPM_INSTANCE_ID
        defaultWfexecutionShouldBeFound("bpm_instance_id.equals=" + DEFAULT_BPM_INSTANCE_ID);

        // Get all the wfexecutionList where bpm_instance_id equals to UPDATED_BPM_INSTANCE_ID
        defaultWfexecutionShouldNotBeFound("bpm_instance_id.equals=" + UPDATED_BPM_INSTANCE_ID);
    }

    @Test
    @Transactional
    public void getAllWfexecutionsByBpm_instance_idIsInShouldWork() throws Exception {
        // Initialize the database
        wfexecutionRepository.saveAndFlush(wfexecution);

        // Get all the wfexecutionList where bpm_instance_id in DEFAULT_BPM_INSTANCE_ID or UPDATED_BPM_INSTANCE_ID
        defaultWfexecutionShouldBeFound("bpm_instance_id.in=" + DEFAULT_BPM_INSTANCE_ID + "," + UPDATED_BPM_INSTANCE_ID);

        // Get all the wfexecutionList where bpm_instance_id equals to UPDATED_BPM_INSTANCE_ID
        defaultWfexecutionShouldNotBeFound("bpm_instance_id.in=" + UPDATED_BPM_INSTANCE_ID);
    }

    @Test
    @Transactional
    public void getAllWfexecutionsByBpm_instance_idIsNullOrNotNull() throws Exception {
        // Initialize the database
        wfexecutionRepository.saveAndFlush(wfexecution);

        // Get all the wfexecutionList where bpm_instance_id is not null
        defaultWfexecutionShouldBeFound("bpm_instance_id.specified=true");

        // Get all the wfexecutionList where bpm_instance_id is null
        defaultWfexecutionShouldNotBeFound("bpm_instance_id.specified=false");
    }

    @Test
    @Transactional
    public void getAllWfexecutionsByBpm_instance_idIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        wfexecutionRepository.saveAndFlush(wfexecution);

        // Get all the wfexecutionList where bpm_instance_id greater than or equals to DEFAULT_BPM_INSTANCE_ID
        defaultWfexecutionShouldBeFound("bpm_instance_id.greaterOrEqualThan=" + DEFAULT_BPM_INSTANCE_ID);

        // Get all the wfexecutionList where bpm_instance_id greater than or equals to UPDATED_BPM_INSTANCE_ID
        defaultWfexecutionShouldNotBeFound("bpm_instance_id.greaterOrEqualThan=" + UPDATED_BPM_INSTANCE_ID);
    }

    @Test
    @Transactional
    public void getAllWfexecutionsByBpm_instance_idIsLessThanSomething() throws Exception {
        // Initialize the database
        wfexecutionRepository.saveAndFlush(wfexecution);

        // Get all the wfexecutionList where bpm_instance_id less than or equals to DEFAULT_BPM_INSTANCE_ID
        defaultWfexecutionShouldNotBeFound("bpm_instance_id.lessThan=" + DEFAULT_BPM_INSTANCE_ID);

        // Get all the wfexecutionList where bpm_instance_id less than or equals to UPDATED_BPM_INSTANCE_ID
        defaultWfexecutionShouldBeFound("bpm_instance_id.lessThan=" + UPDATED_BPM_INSTANCE_ID);
    }


    @Test
    @Transactional
    public void getAllWfexecutionsByBpm_wrkfl_task_idIsEqualToSomething() throws Exception {
        // Initialize the database
        wfexecutionRepository.saveAndFlush(wfexecution);

        // Get all the wfexecutionList where bpm_wrkfl_task_id equals to DEFAULT_BPM_WRKFL_TASK_ID
        defaultWfexecutionShouldBeFound("bpm_wrkfl_task_id.equals=" + DEFAULT_BPM_WRKFL_TASK_ID);

        // Get all the wfexecutionList where bpm_wrkfl_task_id equals to UPDATED_BPM_WRKFL_TASK_ID
        defaultWfexecutionShouldNotBeFound("bpm_wrkfl_task_id.equals=" + UPDATED_BPM_WRKFL_TASK_ID);
    }

    @Test
    @Transactional
    public void getAllWfexecutionsByBpm_wrkfl_task_idIsInShouldWork() throws Exception {
        // Initialize the database
        wfexecutionRepository.saveAndFlush(wfexecution);

        // Get all the wfexecutionList where bpm_wrkfl_task_id in DEFAULT_BPM_WRKFL_TASK_ID or UPDATED_BPM_WRKFL_TASK_ID
        defaultWfexecutionShouldBeFound("bpm_wrkfl_task_id.in=" + DEFAULT_BPM_WRKFL_TASK_ID + "," + UPDATED_BPM_WRKFL_TASK_ID);

        // Get all the wfexecutionList where bpm_wrkfl_task_id equals to UPDATED_BPM_WRKFL_TASK_ID
        defaultWfexecutionShouldNotBeFound("bpm_wrkfl_task_id.in=" + UPDATED_BPM_WRKFL_TASK_ID);
    }

    @Test
    @Transactional
    public void getAllWfexecutionsByBpm_wrkfl_task_idIsNullOrNotNull() throws Exception {
        // Initialize the database
        wfexecutionRepository.saveAndFlush(wfexecution);

        // Get all the wfexecutionList where bpm_wrkfl_task_id is not null
        defaultWfexecutionShouldBeFound("bpm_wrkfl_task_id.specified=true");

        // Get all the wfexecutionList where bpm_wrkfl_task_id is null
        defaultWfexecutionShouldNotBeFound("bpm_wrkfl_task_id.specified=false");
    }

    @Test
    @Transactional
    public void getAllWfexecutionsByBpm_wrkfl_task_idIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        wfexecutionRepository.saveAndFlush(wfexecution);

        // Get all the wfexecutionList where bpm_wrkfl_task_id greater than or equals to DEFAULT_BPM_WRKFL_TASK_ID
        defaultWfexecutionShouldBeFound("bpm_wrkfl_task_id.greaterOrEqualThan=" + DEFAULT_BPM_WRKFL_TASK_ID);

        // Get all the wfexecutionList where bpm_wrkfl_task_id greater than or equals to UPDATED_BPM_WRKFL_TASK_ID
        defaultWfexecutionShouldNotBeFound("bpm_wrkfl_task_id.greaterOrEqualThan=" + UPDATED_BPM_WRKFL_TASK_ID);
    }

    @Test
    @Transactional
    public void getAllWfexecutionsByBpm_wrkfl_task_idIsLessThanSomething() throws Exception {
        // Initialize the database
        wfexecutionRepository.saveAndFlush(wfexecution);

        // Get all the wfexecutionList where bpm_wrkfl_task_id less than or equals to DEFAULT_BPM_WRKFL_TASK_ID
        defaultWfexecutionShouldNotBeFound("bpm_wrkfl_task_id.lessThan=" + DEFAULT_BPM_WRKFL_TASK_ID);

        // Get all the wfexecutionList where bpm_wrkfl_task_id less than or equals to UPDATED_BPM_WRKFL_TASK_ID
        defaultWfexecutionShouldBeFound("bpm_wrkfl_task_id.lessThan=" + UPDATED_BPM_WRKFL_TASK_ID);
    }


    @Test
    @Transactional
    public void getAllWfexecutionsByKey_valueIsEqualToSomething() throws Exception {
        // Initialize the database
        wfexecutionRepository.saveAndFlush(wfexecution);

        // Get all the wfexecutionList where key_value equals to DEFAULT_KEY_VALUE
        defaultWfexecutionShouldBeFound("key_value.equals=" + DEFAULT_KEY_VALUE);

        // Get all the wfexecutionList where key_value equals to UPDATED_KEY_VALUE
        defaultWfexecutionShouldNotBeFound("key_value.equals=" + UPDATED_KEY_VALUE);
    }

    @Test
    @Transactional
    public void getAllWfexecutionsByKey_valueIsInShouldWork() throws Exception {
        // Initialize the database
        wfexecutionRepository.saveAndFlush(wfexecution);

        // Get all the wfexecutionList where key_value in DEFAULT_KEY_VALUE or UPDATED_KEY_VALUE
        defaultWfexecutionShouldBeFound("key_value.in=" + DEFAULT_KEY_VALUE + "," + UPDATED_KEY_VALUE);

        // Get all the wfexecutionList where key_value equals to UPDATED_KEY_VALUE
        defaultWfexecutionShouldNotBeFound("key_value.in=" + UPDATED_KEY_VALUE);
    }

    @Test
    @Transactional
    public void getAllWfexecutionsByKey_valueIsNullOrNotNull() throws Exception {
        // Initialize the database
        wfexecutionRepository.saveAndFlush(wfexecution);

        // Get all the wfexecutionList where key_value is not null
        defaultWfexecutionShouldBeFound("key_value.specified=true");

        // Get all the wfexecutionList where key_value is null
        defaultWfexecutionShouldNotBeFound("key_value.specified=false");
    }

    @Test
    @Transactional
    public void getAllWfexecutionsByKey_valueIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        wfexecutionRepository.saveAndFlush(wfexecution);

        // Get all the wfexecutionList where key_value greater than or equals to DEFAULT_KEY_VALUE
        defaultWfexecutionShouldBeFound("key_value.greaterOrEqualThan=" + DEFAULT_KEY_VALUE);

        // Get all the wfexecutionList where key_value greater than or equals to UPDATED_KEY_VALUE
        defaultWfexecutionShouldNotBeFound("key_value.greaterOrEqualThan=" + UPDATED_KEY_VALUE);
    }

    @Test
    @Transactional
    public void getAllWfexecutionsByKey_valueIsLessThanSomething() throws Exception {
        // Initialize the database
        wfexecutionRepository.saveAndFlush(wfexecution);

        // Get all the wfexecutionList where key_value less than or equals to DEFAULT_KEY_VALUE
        defaultWfexecutionShouldNotBeFound("key_value.lessThan=" + DEFAULT_KEY_VALUE);

        // Get all the wfexecutionList where key_value less than or equals to UPDATED_KEY_VALUE
        defaultWfexecutionShouldBeFound("key_value.lessThan=" + UPDATED_KEY_VALUE);
    }


    @Test
    @Transactional
    public void getAllWfexecutionsBySlaIsEqualToSomething() throws Exception {
        // Initialize the database
        wfexecutionRepository.saveAndFlush(wfexecution);

        // Get all the wfexecutionList where sla equals to DEFAULT_SLA
        defaultWfexecutionShouldBeFound("sla.equals=" + DEFAULT_SLA);

        // Get all the wfexecutionList where sla equals to UPDATED_SLA
        defaultWfexecutionShouldNotBeFound("sla.equals=" + UPDATED_SLA);
    }

    @Test
    @Transactional
    public void getAllWfexecutionsBySlaIsInShouldWork() throws Exception {
        // Initialize the database
        wfexecutionRepository.saveAndFlush(wfexecution);

        // Get all the wfexecutionList where sla in DEFAULT_SLA or UPDATED_SLA
        defaultWfexecutionShouldBeFound("sla.in=" + DEFAULT_SLA + "," + UPDATED_SLA);

        // Get all the wfexecutionList where sla equals to UPDATED_SLA
        defaultWfexecutionShouldNotBeFound("sla.in=" + UPDATED_SLA);
    }

    @Test
    @Transactional
    public void getAllWfexecutionsBySlaIsNullOrNotNull() throws Exception {
        // Initialize the database
        wfexecutionRepository.saveAndFlush(wfexecution);

        // Get all the wfexecutionList where sla is not null
        defaultWfexecutionShouldBeFound("sla.specified=true");

        // Get all the wfexecutionList where sla is null
        defaultWfexecutionShouldNotBeFound("sla.specified=false");
    }

    @Test
    @Transactional
    public void getAllWfexecutionsBySlaIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        wfexecutionRepository.saveAndFlush(wfexecution);

        // Get all the wfexecutionList where sla greater than or equals to DEFAULT_SLA
        defaultWfexecutionShouldBeFound("sla.greaterOrEqualThan=" + DEFAULT_SLA);

        // Get all the wfexecutionList where sla greater than or equals to UPDATED_SLA
        defaultWfexecutionShouldNotBeFound("sla.greaterOrEqualThan=" + UPDATED_SLA);
    }

    @Test
    @Transactional
    public void getAllWfexecutionsBySlaIsLessThanSomething() throws Exception {
        // Initialize the database
        wfexecutionRepository.saveAndFlush(wfexecution);

        // Get all the wfexecutionList where sla less than or equals to DEFAULT_SLA
        defaultWfexecutionShouldNotBeFound("sla.lessThan=" + DEFAULT_SLA);

        // Get all the wfexecutionList where sla less than or equals to UPDATED_SLA
        defaultWfexecutionShouldBeFound("sla.lessThan=" + UPDATED_SLA);
    }


    @Test
    @Transactional
    public void getAllWfexecutionsByWfrequestIsEqualToSomething() throws Exception {
        // Initialize the database
        Wfrequest wfrequest = WfrequestResourceIntTest.createEntity(em);
        em.persist(wfrequest);
        em.flush();
        wfexecution.setWfrequest(wfrequest);
        wfexecutionRepository.saveAndFlush(wfexecution);
        Long wfrequestId = wfrequest.getId();

        // Get all the wfexecutionList where wfrequest equals to wfrequestId
        defaultWfexecutionShouldBeFound("wfrequestId.equals=" + wfrequestId);

        // Get all the wfexecutionList where wfrequest equals to wfrequestId + 1
        defaultWfexecutionShouldNotBeFound("wfrequestId.equals=" + (wfrequestId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultWfexecutionShouldBeFound(String filter) throws Exception {
        restWfexecutionMockMvc.perform(get("/api/wfexecutions?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(wfexecution.getId().intValue())))
            .andExpect(jsonPath("$.[*].bpm_instance_id").value(hasItem(DEFAULT_BPM_INSTANCE_ID)))
            .andExpect(jsonPath("$.[*].bpm_wrkfl_task_id").value(hasItem(DEFAULT_BPM_WRKFL_TASK_ID)))
            .andExpect(jsonPath("$.[*].key_value").value(hasItem(DEFAULT_KEY_VALUE)))
            .andExpect(jsonPath("$.[*].sla").value(hasItem(DEFAULT_SLA)));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultWfexecutionShouldNotBeFound(String filter) throws Exception {
        restWfexecutionMockMvc.perform(get("/api/wfexecutions?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    @Transactional
    public void getNonExistingWfexecution() throws Exception {
        // Get the wfexecution
        restWfexecutionMockMvc.perform(get("/api/wfexecutions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateWfexecution() throws Exception {
        // Initialize the database
        wfexecutionRepository.saveAndFlush(wfexecution);

        int databaseSizeBeforeUpdate = wfexecutionRepository.findAll().size();

        // Update the wfexecution
        Wfexecution updatedWfexecution = wfexecutionRepository.findById(wfexecution.getId()).get();
        // Disconnect from session so that the updates on updatedWfexecution are not directly saved in db
        em.detach(updatedWfexecution);
        updatedWfexecution
            .bpm_instance_id(UPDATED_BPM_INSTANCE_ID)
            .bpm_wrkfl_task_id(UPDATED_BPM_WRKFL_TASK_ID)
            .key_value(UPDATED_KEY_VALUE)
            .sla(UPDATED_SLA);
        WfexecutionDTO wfexecutionDTO = wfexecutionMapper.toDto(updatedWfexecution);

        restWfexecutionMockMvc.perform(put("/api/wfexecutions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(wfexecutionDTO)))
            .andExpect(status().isOk());

        // Validate the Wfexecution in the database
        List<Wfexecution> wfexecutionList = wfexecutionRepository.findAll();
        assertThat(wfexecutionList).hasSize(databaseSizeBeforeUpdate);
        Wfexecution testWfexecution = wfexecutionList.get(wfexecutionList.size() - 1);
        assertThat(testWfexecution.getBpm_instance_id()).isEqualTo(UPDATED_BPM_INSTANCE_ID);
        assertThat(testWfexecution.getBpm_wrkfl_task_id()).isEqualTo(UPDATED_BPM_WRKFL_TASK_ID);
        assertThat(testWfexecution.getKey_value()).isEqualTo(UPDATED_KEY_VALUE);
        assertThat(testWfexecution.getSla()).isEqualTo(UPDATED_SLA);
    }

    @Test
    @Transactional
    public void updateNonExistingWfexecution() throws Exception {
        int databaseSizeBeforeUpdate = wfexecutionRepository.findAll().size();

        // Create the Wfexecution
        WfexecutionDTO wfexecutionDTO = wfexecutionMapper.toDto(wfexecution);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restWfexecutionMockMvc.perform(put("/api/wfexecutions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(wfexecutionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Wfexecution in the database
        List<Wfexecution> wfexecutionList = wfexecutionRepository.findAll();
        assertThat(wfexecutionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteWfexecution() throws Exception {
        // Initialize the database
        wfexecutionRepository.saveAndFlush(wfexecution);

        int databaseSizeBeforeDelete = wfexecutionRepository.findAll().size();

        // Get the wfexecution
        restWfexecutionMockMvc.perform(delete("/api/wfexecutions/{id}", wfexecution.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Wfexecution> wfexecutionList = wfexecutionRepository.findAll();
        assertThat(wfexecutionList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Wfexecution.class);
        Wfexecution wfexecution1 = new Wfexecution();
        wfexecution1.setId(1L);
        Wfexecution wfexecution2 = new Wfexecution();
        wfexecution2.setId(wfexecution1.getId());
        assertThat(wfexecution1).isEqualTo(wfexecution2);
        wfexecution2.setId(2L);
        assertThat(wfexecution1).isNotEqualTo(wfexecution2);
        wfexecution1.setId(null);
        assertThat(wfexecution1).isNotEqualTo(wfexecution2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(WfexecutionDTO.class);
        WfexecutionDTO wfexecutionDTO1 = new WfexecutionDTO();
        wfexecutionDTO1.setId(1L);
        WfexecutionDTO wfexecutionDTO2 = new WfexecutionDTO();
        assertThat(wfexecutionDTO1).isNotEqualTo(wfexecutionDTO2);
        wfexecutionDTO2.setId(wfexecutionDTO1.getId());
        assertThat(wfexecutionDTO1).isEqualTo(wfexecutionDTO2);
        wfexecutionDTO2.setId(2L);
        assertThat(wfexecutionDTO1).isNotEqualTo(wfexecutionDTO2);
        wfexecutionDTO1.setId(null);
        assertThat(wfexecutionDTO1).isNotEqualTo(wfexecutionDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(wfexecutionMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(wfexecutionMapper.fromId(null)).isNull();
    }
}
