package com.wellsfargo.taxie.wftfv.web.rest;

import com.wellsfargo.taxie.wftfv.WftfvApp;

import com.wellsfargo.taxie.wftfv.domain.Wfrequest;
import com.wellsfargo.taxie.wftfv.repository.WfrequestRepository;
import com.wellsfargo.taxie.wftfv.service.WfrequestService;
import com.wellsfargo.taxie.wftfv.service.dto.WfrequestDTO;
import com.wellsfargo.taxie.wftfv.service.mapper.WfrequestMapper;
import com.wellsfargo.taxie.wftfv.web.rest.errors.ExceptionTranslator;
import com.wellsfargo.taxie.wftfv.service.dto.WfrequestCriteria;
import com.wellsfargo.taxie.wftfv.service.WfrequestQueryService;

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


import static com.wellsfargo.taxie.wftfv.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the WfrequestResource REST controller.
 *
 * @see WfrequestResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WftfvApp.class)
public class WfrequestResourceIntTest {

    private static final Integer DEFAULT_COE_RCPNT_ID = 1;
    private static final Integer UPDATED_COE_RCPNT_ID = 2;

    private static final Integer DEFAULT_WRKFL_EXECUTION_ID = 1;
    private static final Integer UPDATED_WRKFL_EXECUTION_ID = 2;

    private static final Integer DEFAULT_CURRENT_TASK_ID = 1;
    private static final Integer UPDATED_CURRENT_TASK_ID = 2;

    private static final Integer DEFAULT_CURRENT_TASK_STAT = 1;
    private static final Integer UPDATED_CURRENT_TASK_STAT = 2;

    private static final Integer DEFAULT_REQUEST_STAT = 1;
    private static final Integer UPDATED_REQUEST_STAT = 2;

    private static final LocalDate DEFAULT_START_DT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_START_DT = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_END_DT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_END_DT = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private WfrequestRepository wfrequestRepository;


    @Autowired
    private WfrequestMapper wfrequestMapper;
    

    @Autowired
    private WfrequestService wfrequestService;

    @Autowired
    private WfrequestQueryService wfrequestQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restWfrequestMockMvc;

    private Wfrequest wfrequest;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final WfrequestResource wfrequestResource = new WfrequestResource(wfrequestService, wfrequestQueryService);
        this.restWfrequestMockMvc = MockMvcBuilders.standaloneSetup(wfrequestResource)
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
    public static Wfrequest createEntity(EntityManager em) {
        Wfrequest wfrequest = new Wfrequest()
            .coe_rcpnt_id(DEFAULT_COE_RCPNT_ID)
            .wrkfl_execution_id(DEFAULT_WRKFL_EXECUTION_ID)
            .current_task_id(DEFAULT_CURRENT_TASK_ID)
            .current_task_stat(DEFAULT_CURRENT_TASK_STAT)
            .request_stat(DEFAULT_REQUEST_STAT)
            .start_dt(DEFAULT_START_DT)
            .end_dt(DEFAULT_END_DT);
        return wfrequest;
    }

    @Before
    public void initTest() {
        wfrequest = createEntity(em);
    }

    @Test
    @Transactional
    public void createWfrequest() throws Exception {
        int databaseSizeBeforeCreate = wfrequestRepository.findAll().size();

        // Create the Wfrequest
        WfrequestDTO wfrequestDTO = wfrequestMapper.toDto(wfrequest);
        restWfrequestMockMvc.perform(post("/api/wfrequests")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(wfrequestDTO)))
            .andExpect(status().isCreated());

        // Validate the Wfrequest in the database
        List<Wfrequest> wfrequestList = wfrequestRepository.findAll();
        assertThat(wfrequestList).hasSize(databaseSizeBeforeCreate + 1);
        Wfrequest testWfrequest = wfrequestList.get(wfrequestList.size() - 1);
        assertThat(testWfrequest.getCoe_rcpnt_id()).isEqualTo(DEFAULT_COE_RCPNT_ID);
        assertThat(testWfrequest.getWrkfl_execution_id()).isEqualTo(DEFAULT_WRKFL_EXECUTION_ID);
        assertThat(testWfrequest.getCurrent_task_id()).isEqualTo(DEFAULT_CURRENT_TASK_ID);
        assertThat(testWfrequest.getCurrent_task_stat()).isEqualTo(DEFAULT_CURRENT_TASK_STAT);
        assertThat(testWfrequest.getRequest_stat()).isEqualTo(DEFAULT_REQUEST_STAT);
        assertThat(testWfrequest.getStart_dt()).isEqualTo(DEFAULT_START_DT);
        assertThat(testWfrequest.getEnd_dt()).isEqualTo(DEFAULT_END_DT);
    }

    @Test
    @Transactional
    public void createWfrequestWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = wfrequestRepository.findAll().size();

        // Create the Wfrequest with an existing ID
        wfrequest.setId(1L);
        WfrequestDTO wfrequestDTO = wfrequestMapper.toDto(wfrequest);

        // An entity with an existing ID cannot be created, so this API call must fail
        restWfrequestMockMvc.perform(post("/api/wfrequests")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(wfrequestDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Wfrequest in the database
        List<Wfrequest> wfrequestList = wfrequestRepository.findAll();
        assertThat(wfrequestList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkCoe_rcpnt_idIsRequired() throws Exception {
        int databaseSizeBeforeTest = wfrequestRepository.findAll().size();
        // set the field null
        wfrequest.setCoe_rcpnt_id(null);

        // Create the Wfrequest, which fails.
        WfrequestDTO wfrequestDTO = wfrequestMapper.toDto(wfrequest);

        restWfrequestMockMvc.perform(post("/api/wfrequests")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(wfrequestDTO)))
            .andExpect(status().isBadRequest());

        List<Wfrequest> wfrequestList = wfrequestRepository.findAll();
        assertThat(wfrequestList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkWrkfl_execution_idIsRequired() throws Exception {
        int databaseSizeBeforeTest = wfrequestRepository.findAll().size();
        // set the field null
        wfrequest.setWrkfl_execution_id(null);

        // Create the Wfrequest, which fails.
        WfrequestDTO wfrequestDTO = wfrequestMapper.toDto(wfrequest);

        restWfrequestMockMvc.perform(post("/api/wfrequests")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(wfrequestDTO)))
            .andExpect(status().isBadRequest());

        List<Wfrequest> wfrequestList = wfrequestRepository.findAll();
        assertThat(wfrequestList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCurrent_task_idIsRequired() throws Exception {
        int databaseSizeBeforeTest = wfrequestRepository.findAll().size();
        // set the field null
        wfrequest.setCurrent_task_id(null);

        // Create the Wfrequest, which fails.
        WfrequestDTO wfrequestDTO = wfrequestMapper.toDto(wfrequest);

        restWfrequestMockMvc.perform(post("/api/wfrequests")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(wfrequestDTO)))
            .andExpect(status().isBadRequest());

        List<Wfrequest> wfrequestList = wfrequestRepository.findAll();
        assertThat(wfrequestList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCurrent_task_statIsRequired() throws Exception {
        int databaseSizeBeforeTest = wfrequestRepository.findAll().size();
        // set the field null
        wfrequest.setCurrent_task_stat(null);

        // Create the Wfrequest, which fails.
        WfrequestDTO wfrequestDTO = wfrequestMapper.toDto(wfrequest);

        restWfrequestMockMvc.perform(post("/api/wfrequests")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(wfrequestDTO)))
            .andExpect(status().isBadRequest());

        List<Wfrequest> wfrequestList = wfrequestRepository.findAll();
        assertThat(wfrequestList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkRequest_statIsRequired() throws Exception {
        int databaseSizeBeforeTest = wfrequestRepository.findAll().size();
        // set the field null
        wfrequest.setRequest_stat(null);

        // Create the Wfrequest, which fails.
        WfrequestDTO wfrequestDTO = wfrequestMapper.toDto(wfrequest);

        restWfrequestMockMvc.perform(post("/api/wfrequests")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(wfrequestDTO)))
            .andExpect(status().isBadRequest());

        List<Wfrequest> wfrequestList = wfrequestRepository.findAll();
        assertThat(wfrequestList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllWfrequests() throws Exception {
        // Initialize the database
        wfrequestRepository.saveAndFlush(wfrequest);

        // Get all the wfrequestList
        restWfrequestMockMvc.perform(get("/api/wfrequests?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(wfrequest.getId().intValue())))
            .andExpect(jsonPath("$.[*].coe_rcpnt_id").value(hasItem(DEFAULT_COE_RCPNT_ID)))
            .andExpect(jsonPath("$.[*].wrkfl_execution_id").value(hasItem(DEFAULT_WRKFL_EXECUTION_ID)))
            .andExpect(jsonPath("$.[*].current_task_id").value(hasItem(DEFAULT_CURRENT_TASK_ID)))
            .andExpect(jsonPath("$.[*].current_task_stat").value(hasItem(DEFAULT_CURRENT_TASK_STAT)))
            .andExpect(jsonPath("$.[*].request_stat").value(hasItem(DEFAULT_REQUEST_STAT)))
            .andExpect(jsonPath("$.[*].start_dt").value(hasItem(DEFAULT_START_DT.toString())))
            .andExpect(jsonPath("$.[*].end_dt").value(hasItem(DEFAULT_END_DT.toString())));
    }
    

    @Test
    @Transactional
    public void getWfrequest() throws Exception {
        // Initialize the database
        wfrequestRepository.saveAndFlush(wfrequest);

        // Get the wfrequest
        restWfrequestMockMvc.perform(get("/api/wfrequests/{id}", wfrequest.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(wfrequest.getId().intValue()))
            .andExpect(jsonPath("$.coe_rcpnt_id").value(DEFAULT_COE_RCPNT_ID))
            .andExpect(jsonPath("$.wrkfl_execution_id").value(DEFAULT_WRKFL_EXECUTION_ID))
            .andExpect(jsonPath("$.current_task_id").value(DEFAULT_CURRENT_TASK_ID))
            .andExpect(jsonPath("$.current_task_stat").value(DEFAULT_CURRENT_TASK_STAT))
            .andExpect(jsonPath("$.request_stat").value(DEFAULT_REQUEST_STAT))
            .andExpect(jsonPath("$.start_dt").value(DEFAULT_START_DT.toString()))
            .andExpect(jsonPath("$.end_dt").value(DEFAULT_END_DT.toString()));
    }

    @Test
    @Transactional
    public void getAllWfrequestsByCoe_rcpnt_idIsEqualToSomething() throws Exception {
        // Initialize the database
        wfrequestRepository.saveAndFlush(wfrequest);

        // Get all the wfrequestList where coe_rcpnt_id equals to DEFAULT_COE_RCPNT_ID
        defaultWfrequestShouldBeFound("coe_rcpnt_id.equals=" + DEFAULT_COE_RCPNT_ID);

        // Get all the wfrequestList where coe_rcpnt_id equals to UPDATED_COE_RCPNT_ID
        defaultWfrequestShouldNotBeFound("coe_rcpnt_id.equals=" + UPDATED_COE_RCPNT_ID);
    }

    @Test
    @Transactional
    public void getAllWfrequestsByCoe_rcpnt_idIsInShouldWork() throws Exception {
        // Initialize the database
        wfrequestRepository.saveAndFlush(wfrequest);

        // Get all the wfrequestList where coe_rcpnt_id in DEFAULT_COE_RCPNT_ID or UPDATED_COE_RCPNT_ID
        defaultWfrequestShouldBeFound("coe_rcpnt_id.in=" + DEFAULT_COE_RCPNT_ID + "," + UPDATED_COE_RCPNT_ID);

        // Get all the wfrequestList where coe_rcpnt_id equals to UPDATED_COE_RCPNT_ID
        defaultWfrequestShouldNotBeFound("coe_rcpnt_id.in=" + UPDATED_COE_RCPNT_ID);
    }

    @Test
    @Transactional
    public void getAllWfrequestsByCoe_rcpnt_idIsNullOrNotNull() throws Exception {
        // Initialize the database
        wfrequestRepository.saveAndFlush(wfrequest);

        // Get all the wfrequestList where coe_rcpnt_id is not null
        defaultWfrequestShouldBeFound("coe_rcpnt_id.specified=true");

        // Get all the wfrequestList where coe_rcpnt_id is null
        defaultWfrequestShouldNotBeFound("coe_rcpnt_id.specified=false");
    }

    @Test
    @Transactional
    public void getAllWfrequestsByCoe_rcpnt_idIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        wfrequestRepository.saveAndFlush(wfrequest);

        // Get all the wfrequestList where coe_rcpnt_id greater than or equals to DEFAULT_COE_RCPNT_ID
        defaultWfrequestShouldBeFound("coe_rcpnt_id.greaterOrEqualThan=" + DEFAULT_COE_RCPNT_ID);

        // Get all the wfrequestList where coe_rcpnt_id greater than or equals to UPDATED_COE_RCPNT_ID
        defaultWfrequestShouldNotBeFound("coe_rcpnt_id.greaterOrEqualThan=" + UPDATED_COE_RCPNT_ID);
    }

    @Test
    @Transactional
    public void getAllWfrequestsByCoe_rcpnt_idIsLessThanSomething() throws Exception {
        // Initialize the database
        wfrequestRepository.saveAndFlush(wfrequest);

        // Get all the wfrequestList where coe_rcpnt_id less than or equals to DEFAULT_COE_RCPNT_ID
        defaultWfrequestShouldNotBeFound("coe_rcpnt_id.lessThan=" + DEFAULT_COE_RCPNT_ID);

        // Get all the wfrequestList where coe_rcpnt_id less than or equals to UPDATED_COE_RCPNT_ID
        defaultWfrequestShouldBeFound("coe_rcpnt_id.lessThan=" + UPDATED_COE_RCPNT_ID);
    }


    @Test
    @Transactional
    public void getAllWfrequestsByWrkfl_execution_idIsEqualToSomething() throws Exception {
        // Initialize the database
        wfrequestRepository.saveAndFlush(wfrequest);

        // Get all the wfrequestList where wrkfl_execution_id equals to DEFAULT_WRKFL_EXECUTION_ID
        defaultWfrequestShouldBeFound("wrkfl_execution_id.equals=" + DEFAULT_WRKFL_EXECUTION_ID);

        // Get all the wfrequestList where wrkfl_execution_id equals to UPDATED_WRKFL_EXECUTION_ID
        defaultWfrequestShouldNotBeFound("wrkfl_execution_id.equals=" + UPDATED_WRKFL_EXECUTION_ID);
    }

    @Test
    @Transactional
    public void getAllWfrequestsByWrkfl_execution_idIsInShouldWork() throws Exception {
        // Initialize the database
        wfrequestRepository.saveAndFlush(wfrequest);

        // Get all the wfrequestList where wrkfl_execution_id in DEFAULT_WRKFL_EXECUTION_ID or UPDATED_WRKFL_EXECUTION_ID
        defaultWfrequestShouldBeFound("wrkfl_execution_id.in=" + DEFAULT_WRKFL_EXECUTION_ID + "," + UPDATED_WRKFL_EXECUTION_ID);

        // Get all the wfrequestList where wrkfl_execution_id equals to UPDATED_WRKFL_EXECUTION_ID
        defaultWfrequestShouldNotBeFound("wrkfl_execution_id.in=" + UPDATED_WRKFL_EXECUTION_ID);
    }

    @Test
    @Transactional
    public void getAllWfrequestsByWrkfl_execution_idIsNullOrNotNull() throws Exception {
        // Initialize the database
        wfrequestRepository.saveAndFlush(wfrequest);

        // Get all the wfrequestList where wrkfl_execution_id is not null
        defaultWfrequestShouldBeFound("wrkfl_execution_id.specified=true");

        // Get all the wfrequestList where wrkfl_execution_id is null
        defaultWfrequestShouldNotBeFound("wrkfl_execution_id.specified=false");
    }

    @Test
    @Transactional
    public void getAllWfrequestsByWrkfl_execution_idIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        wfrequestRepository.saveAndFlush(wfrequest);

        // Get all the wfrequestList where wrkfl_execution_id greater than or equals to DEFAULT_WRKFL_EXECUTION_ID
        defaultWfrequestShouldBeFound("wrkfl_execution_id.greaterOrEqualThan=" + DEFAULT_WRKFL_EXECUTION_ID);

        // Get all the wfrequestList where wrkfl_execution_id greater than or equals to UPDATED_WRKFL_EXECUTION_ID
        defaultWfrequestShouldNotBeFound("wrkfl_execution_id.greaterOrEqualThan=" + UPDATED_WRKFL_EXECUTION_ID);
    }

    @Test
    @Transactional
    public void getAllWfrequestsByWrkfl_execution_idIsLessThanSomething() throws Exception {
        // Initialize the database
        wfrequestRepository.saveAndFlush(wfrequest);

        // Get all the wfrequestList where wrkfl_execution_id less than or equals to DEFAULT_WRKFL_EXECUTION_ID
        defaultWfrequestShouldNotBeFound("wrkfl_execution_id.lessThan=" + DEFAULT_WRKFL_EXECUTION_ID);

        // Get all the wfrequestList where wrkfl_execution_id less than or equals to UPDATED_WRKFL_EXECUTION_ID
        defaultWfrequestShouldBeFound("wrkfl_execution_id.lessThan=" + UPDATED_WRKFL_EXECUTION_ID);
    }


    @Test
    @Transactional
    public void getAllWfrequestsByCurrent_task_idIsEqualToSomething() throws Exception {
        // Initialize the database
        wfrequestRepository.saveAndFlush(wfrequest);

        // Get all the wfrequestList where current_task_id equals to DEFAULT_CURRENT_TASK_ID
        defaultWfrequestShouldBeFound("current_task_id.equals=" + DEFAULT_CURRENT_TASK_ID);

        // Get all the wfrequestList where current_task_id equals to UPDATED_CURRENT_TASK_ID
        defaultWfrequestShouldNotBeFound("current_task_id.equals=" + UPDATED_CURRENT_TASK_ID);
    }

    @Test
    @Transactional
    public void getAllWfrequestsByCurrent_task_idIsInShouldWork() throws Exception {
        // Initialize the database
        wfrequestRepository.saveAndFlush(wfrequest);

        // Get all the wfrequestList where current_task_id in DEFAULT_CURRENT_TASK_ID or UPDATED_CURRENT_TASK_ID
        defaultWfrequestShouldBeFound("current_task_id.in=" + DEFAULT_CURRENT_TASK_ID + "," + UPDATED_CURRENT_TASK_ID);

        // Get all the wfrequestList where current_task_id equals to UPDATED_CURRENT_TASK_ID
        defaultWfrequestShouldNotBeFound("current_task_id.in=" + UPDATED_CURRENT_TASK_ID);
    }

    @Test
    @Transactional
    public void getAllWfrequestsByCurrent_task_idIsNullOrNotNull() throws Exception {
        // Initialize the database
        wfrequestRepository.saveAndFlush(wfrequest);

        // Get all the wfrequestList where current_task_id is not null
        defaultWfrequestShouldBeFound("current_task_id.specified=true");

        // Get all the wfrequestList where current_task_id is null
        defaultWfrequestShouldNotBeFound("current_task_id.specified=false");
    }

    @Test
    @Transactional
    public void getAllWfrequestsByCurrent_task_idIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        wfrequestRepository.saveAndFlush(wfrequest);

        // Get all the wfrequestList where current_task_id greater than or equals to DEFAULT_CURRENT_TASK_ID
        defaultWfrequestShouldBeFound("current_task_id.greaterOrEqualThan=" + DEFAULT_CURRENT_TASK_ID);

        // Get all the wfrequestList where current_task_id greater than or equals to UPDATED_CURRENT_TASK_ID
        defaultWfrequestShouldNotBeFound("current_task_id.greaterOrEqualThan=" + UPDATED_CURRENT_TASK_ID);
    }

    @Test
    @Transactional
    public void getAllWfrequestsByCurrent_task_idIsLessThanSomething() throws Exception {
        // Initialize the database
        wfrequestRepository.saveAndFlush(wfrequest);

        // Get all the wfrequestList where current_task_id less than or equals to DEFAULT_CURRENT_TASK_ID
        defaultWfrequestShouldNotBeFound("current_task_id.lessThan=" + DEFAULT_CURRENT_TASK_ID);

        // Get all the wfrequestList where current_task_id less than or equals to UPDATED_CURRENT_TASK_ID
        defaultWfrequestShouldBeFound("current_task_id.lessThan=" + UPDATED_CURRENT_TASK_ID);
    }


    @Test
    @Transactional
    public void getAllWfrequestsByCurrent_task_statIsEqualToSomething() throws Exception {
        // Initialize the database
        wfrequestRepository.saveAndFlush(wfrequest);

        // Get all the wfrequestList where current_task_stat equals to DEFAULT_CURRENT_TASK_STAT
        defaultWfrequestShouldBeFound("current_task_stat.equals=" + DEFAULT_CURRENT_TASK_STAT);

        // Get all the wfrequestList where current_task_stat equals to UPDATED_CURRENT_TASK_STAT
        defaultWfrequestShouldNotBeFound("current_task_stat.equals=" + UPDATED_CURRENT_TASK_STAT);
    }

    @Test
    @Transactional
    public void getAllWfrequestsByCurrent_task_statIsInShouldWork() throws Exception {
        // Initialize the database
        wfrequestRepository.saveAndFlush(wfrequest);

        // Get all the wfrequestList where current_task_stat in DEFAULT_CURRENT_TASK_STAT or UPDATED_CURRENT_TASK_STAT
        defaultWfrequestShouldBeFound("current_task_stat.in=" + DEFAULT_CURRENT_TASK_STAT + "," + UPDATED_CURRENT_TASK_STAT);

        // Get all the wfrequestList where current_task_stat equals to UPDATED_CURRENT_TASK_STAT
        defaultWfrequestShouldNotBeFound("current_task_stat.in=" + UPDATED_CURRENT_TASK_STAT);
    }

    @Test
    @Transactional
    public void getAllWfrequestsByCurrent_task_statIsNullOrNotNull() throws Exception {
        // Initialize the database
        wfrequestRepository.saveAndFlush(wfrequest);

        // Get all the wfrequestList where current_task_stat is not null
        defaultWfrequestShouldBeFound("current_task_stat.specified=true");

        // Get all the wfrequestList where current_task_stat is null
        defaultWfrequestShouldNotBeFound("current_task_stat.specified=false");
    }

    @Test
    @Transactional
    public void getAllWfrequestsByCurrent_task_statIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        wfrequestRepository.saveAndFlush(wfrequest);

        // Get all the wfrequestList where current_task_stat greater than or equals to DEFAULT_CURRENT_TASK_STAT
        defaultWfrequestShouldBeFound("current_task_stat.greaterOrEqualThan=" + DEFAULT_CURRENT_TASK_STAT);

        // Get all the wfrequestList where current_task_stat greater than or equals to UPDATED_CURRENT_TASK_STAT
        defaultWfrequestShouldNotBeFound("current_task_stat.greaterOrEqualThan=" + UPDATED_CURRENT_TASK_STAT);
    }

    @Test
    @Transactional
    public void getAllWfrequestsByCurrent_task_statIsLessThanSomething() throws Exception {
        // Initialize the database
        wfrequestRepository.saveAndFlush(wfrequest);

        // Get all the wfrequestList where current_task_stat less than or equals to DEFAULT_CURRENT_TASK_STAT
        defaultWfrequestShouldNotBeFound("current_task_stat.lessThan=" + DEFAULT_CURRENT_TASK_STAT);

        // Get all the wfrequestList where current_task_stat less than or equals to UPDATED_CURRENT_TASK_STAT
        defaultWfrequestShouldBeFound("current_task_stat.lessThan=" + UPDATED_CURRENT_TASK_STAT);
    }


    @Test
    @Transactional
    public void getAllWfrequestsByRequest_statIsEqualToSomething() throws Exception {
        // Initialize the database
        wfrequestRepository.saveAndFlush(wfrequest);

        // Get all the wfrequestList where request_stat equals to DEFAULT_REQUEST_STAT
        defaultWfrequestShouldBeFound("request_stat.equals=" + DEFAULT_REQUEST_STAT);

        // Get all the wfrequestList where request_stat equals to UPDATED_REQUEST_STAT
        defaultWfrequestShouldNotBeFound("request_stat.equals=" + UPDATED_REQUEST_STAT);
    }

    @Test
    @Transactional
    public void getAllWfrequestsByRequest_statIsInShouldWork() throws Exception {
        // Initialize the database
        wfrequestRepository.saveAndFlush(wfrequest);

        // Get all the wfrequestList where request_stat in DEFAULT_REQUEST_STAT or UPDATED_REQUEST_STAT
        defaultWfrequestShouldBeFound("request_stat.in=" + DEFAULT_REQUEST_STAT + "," + UPDATED_REQUEST_STAT);

        // Get all the wfrequestList where request_stat equals to UPDATED_REQUEST_STAT
        defaultWfrequestShouldNotBeFound("request_stat.in=" + UPDATED_REQUEST_STAT);
    }

    @Test
    @Transactional
    public void getAllWfrequestsByRequest_statIsNullOrNotNull() throws Exception {
        // Initialize the database
        wfrequestRepository.saveAndFlush(wfrequest);

        // Get all the wfrequestList where request_stat is not null
        defaultWfrequestShouldBeFound("request_stat.specified=true");

        // Get all the wfrequestList where request_stat is null
        defaultWfrequestShouldNotBeFound("request_stat.specified=false");
    }

    @Test
    @Transactional
    public void getAllWfrequestsByRequest_statIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        wfrequestRepository.saveAndFlush(wfrequest);

        // Get all the wfrequestList where request_stat greater than or equals to DEFAULT_REQUEST_STAT
        defaultWfrequestShouldBeFound("request_stat.greaterOrEqualThan=" + DEFAULT_REQUEST_STAT);

        // Get all the wfrequestList where request_stat greater than or equals to UPDATED_REQUEST_STAT
        defaultWfrequestShouldNotBeFound("request_stat.greaterOrEqualThan=" + UPDATED_REQUEST_STAT);
    }

    @Test
    @Transactional
    public void getAllWfrequestsByRequest_statIsLessThanSomething() throws Exception {
        // Initialize the database
        wfrequestRepository.saveAndFlush(wfrequest);

        // Get all the wfrequestList where request_stat less than or equals to DEFAULT_REQUEST_STAT
        defaultWfrequestShouldNotBeFound("request_stat.lessThan=" + DEFAULT_REQUEST_STAT);

        // Get all the wfrequestList where request_stat less than or equals to UPDATED_REQUEST_STAT
        defaultWfrequestShouldBeFound("request_stat.lessThan=" + UPDATED_REQUEST_STAT);
    }


    @Test
    @Transactional
    public void getAllWfrequestsByStart_dtIsEqualToSomething() throws Exception {
        // Initialize the database
        wfrequestRepository.saveAndFlush(wfrequest);

        // Get all the wfrequestList where start_dt equals to DEFAULT_START_DT
        defaultWfrequestShouldBeFound("start_dt.equals=" + DEFAULT_START_DT);

        // Get all the wfrequestList where start_dt equals to UPDATED_START_DT
        defaultWfrequestShouldNotBeFound("start_dt.equals=" + UPDATED_START_DT);
    }

    @Test
    @Transactional
    public void getAllWfrequestsByStart_dtIsInShouldWork() throws Exception {
        // Initialize the database
        wfrequestRepository.saveAndFlush(wfrequest);

        // Get all the wfrequestList where start_dt in DEFAULT_START_DT or UPDATED_START_DT
        defaultWfrequestShouldBeFound("start_dt.in=" + DEFAULT_START_DT + "," + UPDATED_START_DT);

        // Get all the wfrequestList where start_dt equals to UPDATED_START_DT
        defaultWfrequestShouldNotBeFound("start_dt.in=" + UPDATED_START_DT);
    }

    @Test
    @Transactional
    public void getAllWfrequestsByStart_dtIsNullOrNotNull() throws Exception {
        // Initialize the database
        wfrequestRepository.saveAndFlush(wfrequest);

        // Get all the wfrequestList where start_dt is not null
        defaultWfrequestShouldBeFound("start_dt.specified=true");

        // Get all the wfrequestList where start_dt is null
        defaultWfrequestShouldNotBeFound("start_dt.specified=false");
    }

    @Test
    @Transactional
    public void getAllWfrequestsByStart_dtIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        wfrequestRepository.saveAndFlush(wfrequest);

        // Get all the wfrequestList where start_dt greater than or equals to DEFAULT_START_DT
        defaultWfrequestShouldBeFound("start_dt.greaterOrEqualThan=" + DEFAULT_START_DT);

        // Get all the wfrequestList where start_dt greater than or equals to UPDATED_START_DT
        defaultWfrequestShouldNotBeFound("start_dt.greaterOrEqualThan=" + UPDATED_START_DT);
    }

    @Test
    @Transactional
    public void getAllWfrequestsByStart_dtIsLessThanSomething() throws Exception {
        // Initialize the database
        wfrequestRepository.saveAndFlush(wfrequest);

        // Get all the wfrequestList where start_dt less than or equals to DEFAULT_START_DT
        defaultWfrequestShouldNotBeFound("start_dt.lessThan=" + DEFAULT_START_DT);

        // Get all the wfrequestList where start_dt less than or equals to UPDATED_START_DT
        defaultWfrequestShouldBeFound("start_dt.lessThan=" + UPDATED_START_DT);
    }


    @Test
    @Transactional
    public void getAllWfrequestsByEnd_dtIsEqualToSomething() throws Exception {
        // Initialize the database
        wfrequestRepository.saveAndFlush(wfrequest);

        // Get all the wfrequestList where end_dt equals to DEFAULT_END_DT
        defaultWfrequestShouldBeFound("end_dt.equals=" + DEFAULT_END_DT);

        // Get all the wfrequestList where end_dt equals to UPDATED_END_DT
        defaultWfrequestShouldNotBeFound("end_dt.equals=" + UPDATED_END_DT);
    }

    @Test
    @Transactional
    public void getAllWfrequestsByEnd_dtIsInShouldWork() throws Exception {
        // Initialize the database
        wfrequestRepository.saveAndFlush(wfrequest);

        // Get all the wfrequestList where end_dt in DEFAULT_END_DT or UPDATED_END_DT
        defaultWfrequestShouldBeFound("end_dt.in=" + DEFAULT_END_DT + "," + UPDATED_END_DT);

        // Get all the wfrequestList where end_dt equals to UPDATED_END_DT
        defaultWfrequestShouldNotBeFound("end_dt.in=" + UPDATED_END_DT);
    }

    @Test
    @Transactional
    public void getAllWfrequestsByEnd_dtIsNullOrNotNull() throws Exception {
        // Initialize the database
        wfrequestRepository.saveAndFlush(wfrequest);

        // Get all the wfrequestList where end_dt is not null
        defaultWfrequestShouldBeFound("end_dt.specified=true");

        // Get all the wfrequestList where end_dt is null
        defaultWfrequestShouldNotBeFound("end_dt.specified=false");
    }

    @Test
    @Transactional
    public void getAllWfrequestsByEnd_dtIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        wfrequestRepository.saveAndFlush(wfrequest);

        // Get all the wfrequestList where end_dt greater than or equals to DEFAULT_END_DT
        defaultWfrequestShouldBeFound("end_dt.greaterOrEqualThan=" + DEFAULT_END_DT);

        // Get all the wfrequestList where end_dt greater than or equals to UPDATED_END_DT
        defaultWfrequestShouldNotBeFound("end_dt.greaterOrEqualThan=" + UPDATED_END_DT);
    }

    @Test
    @Transactional
    public void getAllWfrequestsByEnd_dtIsLessThanSomething() throws Exception {
        // Initialize the database
        wfrequestRepository.saveAndFlush(wfrequest);

        // Get all the wfrequestList where end_dt less than or equals to DEFAULT_END_DT
        defaultWfrequestShouldNotBeFound("end_dt.lessThan=" + DEFAULT_END_DT);

        // Get all the wfrequestList where end_dt less than or equals to UPDATED_END_DT
        defaultWfrequestShouldBeFound("end_dt.lessThan=" + UPDATED_END_DT);
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultWfrequestShouldBeFound(String filter) throws Exception {
        restWfrequestMockMvc.perform(get("/api/wfrequests?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(wfrequest.getId().intValue())))
            .andExpect(jsonPath("$.[*].coe_rcpnt_id").value(hasItem(DEFAULT_COE_RCPNT_ID)))
            .andExpect(jsonPath("$.[*].wrkfl_execution_id").value(hasItem(DEFAULT_WRKFL_EXECUTION_ID)))
            .andExpect(jsonPath("$.[*].current_task_id").value(hasItem(DEFAULT_CURRENT_TASK_ID)))
            .andExpect(jsonPath("$.[*].current_task_stat").value(hasItem(DEFAULT_CURRENT_TASK_STAT)))
            .andExpect(jsonPath("$.[*].request_stat").value(hasItem(DEFAULT_REQUEST_STAT)))
            .andExpect(jsonPath("$.[*].start_dt").value(hasItem(DEFAULT_START_DT.toString())))
            .andExpect(jsonPath("$.[*].end_dt").value(hasItem(DEFAULT_END_DT.toString())));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultWfrequestShouldNotBeFound(String filter) throws Exception {
        restWfrequestMockMvc.perform(get("/api/wfrequests?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    @Transactional
    public void getNonExistingWfrequest() throws Exception {
        // Get the wfrequest
        restWfrequestMockMvc.perform(get("/api/wfrequests/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateWfrequest() throws Exception {
        // Initialize the database
        wfrequestRepository.saveAndFlush(wfrequest);

        int databaseSizeBeforeUpdate = wfrequestRepository.findAll().size();

        // Update the wfrequest
        Wfrequest updatedWfrequest = wfrequestRepository.findById(wfrequest.getId()).get();
        // Disconnect from session so that the updates on updatedWfrequest are not directly saved in db
        em.detach(updatedWfrequest);
        updatedWfrequest
            .coe_rcpnt_id(UPDATED_COE_RCPNT_ID)
            .wrkfl_execution_id(UPDATED_WRKFL_EXECUTION_ID)
            .current_task_id(UPDATED_CURRENT_TASK_ID)
            .current_task_stat(UPDATED_CURRENT_TASK_STAT)
            .request_stat(UPDATED_REQUEST_STAT)
            .start_dt(UPDATED_START_DT)
            .end_dt(UPDATED_END_DT);
        WfrequestDTO wfrequestDTO = wfrequestMapper.toDto(updatedWfrequest);

        restWfrequestMockMvc.perform(put("/api/wfrequests")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(wfrequestDTO)))
            .andExpect(status().isOk());

        // Validate the Wfrequest in the database
        List<Wfrequest> wfrequestList = wfrequestRepository.findAll();
        assertThat(wfrequestList).hasSize(databaseSizeBeforeUpdate);
        Wfrequest testWfrequest = wfrequestList.get(wfrequestList.size() - 1);
        assertThat(testWfrequest.getCoe_rcpnt_id()).isEqualTo(UPDATED_COE_RCPNT_ID);
        assertThat(testWfrequest.getWrkfl_execution_id()).isEqualTo(UPDATED_WRKFL_EXECUTION_ID);
        assertThat(testWfrequest.getCurrent_task_id()).isEqualTo(UPDATED_CURRENT_TASK_ID);
        assertThat(testWfrequest.getCurrent_task_stat()).isEqualTo(UPDATED_CURRENT_TASK_STAT);
        assertThat(testWfrequest.getRequest_stat()).isEqualTo(UPDATED_REQUEST_STAT);
        assertThat(testWfrequest.getStart_dt()).isEqualTo(UPDATED_START_DT);
        assertThat(testWfrequest.getEnd_dt()).isEqualTo(UPDATED_END_DT);
    }

    @Test
    @Transactional
    public void updateNonExistingWfrequest() throws Exception {
        int databaseSizeBeforeUpdate = wfrequestRepository.findAll().size();

        // Create the Wfrequest
        WfrequestDTO wfrequestDTO = wfrequestMapper.toDto(wfrequest);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restWfrequestMockMvc.perform(put("/api/wfrequests")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(wfrequestDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Wfrequest in the database
        List<Wfrequest> wfrequestList = wfrequestRepository.findAll();
        assertThat(wfrequestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteWfrequest() throws Exception {
        // Initialize the database
        wfrequestRepository.saveAndFlush(wfrequest);

        int databaseSizeBeforeDelete = wfrequestRepository.findAll().size();

        // Get the wfrequest
        restWfrequestMockMvc.perform(delete("/api/wfrequests/{id}", wfrequest.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Wfrequest> wfrequestList = wfrequestRepository.findAll();
        assertThat(wfrequestList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Wfrequest.class);
        Wfrequest wfrequest1 = new Wfrequest();
        wfrequest1.setId(1L);
        Wfrequest wfrequest2 = new Wfrequest();
        wfrequest2.setId(wfrequest1.getId());
        assertThat(wfrequest1).isEqualTo(wfrequest2);
        wfrequest2.setId(2L);
        assertThat(wfrequest1).isNotEqualTo(wfrequest2);
        wfrequest1.setId(null);
        assertThat(wfrequest1).isNotEqualTo(wfrequest2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(WfrequestDTO.class);
        WfrequestDTO wfrequestDTO1 = new WfrequestDTO();
        wfrequestDTO1.setId(1L);
        WfrequestDTO wfrequestDTO2 = new WfrequestDTO();
        assertThat(wfrequestDTO1).isNotEqualTo(wfrequestDTO2);
        wfrequestDTO2.setId(wfrequestDTO1.getId());
        assertThat(wfrequestDTO1).isEqualTo(wfrequestDTO2);
        wfrequestDTO2.setId(2L);
        assertThat(wfrequestDTO1).isNotEqualTo(wfrequestDTO2);
        wfrequestDTO1.setId(null);
        assertThat(wfrequestDTO1).isNotEqualTo(wfrequestDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(wfrequestMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(wfrequestMapper.fromId(null)).isNull();
    }
}
