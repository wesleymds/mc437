package br.com.conpec.sade.web.rest;

import br.com.conpec.sade.Mc437App;

import br.com.conpec.sade.domain.UserData;
import br.com.conpec.sade.repository.UserDataRepository;
import br.com.conpec.sade.repository.search.UserDataSearchRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the UserDataResource REST controller.
 *
 * @see UserDataResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Mc437App.class)
public class UserDataResourceIntTest {

    private static final String DEFAULT_PRIMARY_PHONE_NUMBER = "AAAAA";
    private static final String UPDATED_PRIMARY_PHONE_NUMBER = "BBBBB";

    private static final String DEFAULT_SECONDARY_PHONE_NUMBER = "AAAAA";
    private static final String UPDATED_SECONDARY_PHONE_NUMBER = "BBBBB";

    private static final String DEFAULT_ADDRESS = "AAAAA";
    private static final String UPDATED_ADDRESS = "BBBBB";

    private static final String DEFAULT_RG = "AAAAA";
    private static final String UPDATED_RG = "BBBBB";

    private static final String DEFAULT_CPF = "AAAAA";
    private static final String UPDATED_CPF = "BBBBB";

    private static final String DEFAULT_EXTRA = "AAAAA";
    private static final String UPDATED_EXTRA = "BBBBB";

    private static final Boolean DEFAULT_AVAILABLE = false;
    private static final Boolean UPDATED_AVAILABLE = true;

    private static final Integer DEFAULT_AVAILABLE_HOURS_PER_WEEK = 1;
    private static final Integer UPDATED_AVAILABLE_HOURS_PER_WEEK = 2;

    private static final Integer DEFAULT_INITIAL_COST_PER_HOUR = 1;
    private static final Integer UPDATED_INITIAL_COST_PER_HOUR = 2;

    private static final String DEFAULT_BANK_AGENCY = "AAAAA";
    private static final String UPDATED_BANK_AGENCY = "BBBBB";

    private static final String DEFAULT_BANK_ACCOUNT = "AAAAA";
    private static final String UPDATED_BANK_ACCOUNT = "BBBBB";

    @Inject
    private UserDataRepository userDataRepository;

    @Inject
    private UserDataSearchRepository userDataSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restUserDataMockMvc;

    private UserData userData;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        UserDataResource userDataResource = new UserDataResource();
        ReflectionTestUtils.setField(userDataResource, "userDataSearchRepository", userDataSearchRepository);
        ReflectionTestUtils.setField(userDataResource, "userDataRepository", userDataRepository);
        this.restUserDataMockMvc = MockMvcBuilders.standaloneSetup(userDataResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserData createEntity(EntityManager em) {
        UserData userData = new UserData()
                .primaryPhoneNumber(DEFAULT_PRIMARY_PHONE_NUMBER)
                .secondaryPhoneNumber(DEFAULT_SECONDARY_PHONE_NUMBER)
                .address(DEFAULT_ADDRESS)
                .rg(DEFAULT_RG)
                .cpf(DEFAULT_CPF)
                .extra(DEFAULT_EXTRA)
                .available(DEFAULT_AVAILABLE)
                .availableHoursPerWeek(DEFAULT_AVAILABLE_HOURS_PER_WEEK)
                .initialCostPerHour(DEFAULT_INITIAL_COST_PER_HOUR)
                .bankAgency(DEFAULT_BANK_AGENCY)
                .bankAccount(DEFAULT_BANK_ACCOUNT);
        return userData;
    }

    @Before
    public void initTest() {
        userDataSearchRepository.deleteAll();
        userData = createEntity(em);
    }

    @Test
    @Transactional
    public void createUserData() throws Exception {
        int databaseSizeBeforeCreate = userDataRepository.findAll().size();

        // Create the UserData

        restUserDataMockMvc.perform(post("/api/user-data")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(userData)))
                .andExpect(status().isCreated());

        // Validate the UserData in the database
        List<UserData> userData = userDataRepository.findAll();
        assertThat(userData).hasSize(databaseSizeBeforeCreate + 1);
        UserData testUserData = userData.get(userData.size() - 1);
        assertThat(testUserData.getPrimaryPhoneNumber()).isEqualTo(DEFAULT_PRIMARY_PHONE_NUMBER);
        assertThat(testUserData.getSecondaryPhoneNumber()).isEqualTo(DEFAULT_SECONDARY_PHONE_NUMBER);
        assertThat(testUserData.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testUserData.getRg()).isEqualTo(DEFAULT_RG);
        assertThat(testUserData.getCpf()).isEqualTo(DEFAULT_CPF);
        assertThat(testUserData.getExtra()).isEqualTo(DEFAULT_EXTRA);
        assertThat(testUserData.isAvailable()).isEqualTo(DEFAULT_AVAILABLE);
        assertThat(testUserData.getAvailableHoursPerWeek()).isEqualTo(DEFAULT_AVAILABLE_HOURS_PER_WEEK);
        assertThat(testUserData.getInitialCostPerHour()).isEqualTo(DEFAULT_INITIAL_COST_PER_HOUR);
        assertThat(testUserData.getBankAgency()).isEqualTo(DEFAULT_BANK_AGENCY);
        assertThat(testUserData.getBankAccount()).isEqualTo(DEFAULT_BANK_ACCOUNT);

        // Validate the UserData in ElasticSearch
        UserData userDataEs = userDataSearchRepository.findOne(testUserData.getId());
        assertThat(userDataEs).isEqualToComparingFieldByField(testUserData);
    }

    @Test
    @Transactional
    public void checkPrimaryPhoneNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = userDataRepository.findAll().size();
        // set the field null
        userData.setPrimaryPhoneNumber(null);

        // Create the UserData, which fails.

        restUserDataMockMvc.perform(post("/api/user-data")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(userData)))
                .andExpect(status().isBadRequest());

        List<UserData> userData = userDataRepository.findAll();
        assertThat(userData).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAddressIsRequired() throws Exception {
        int databaseSizeBeforeTest = userDataRepository.findAll().size();
        // set the field null
        userData.setAddress(null);

        // Create the UserData, which fails.

        restUserDataMockMvc.perform(post("/api/user-data")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(userData)))
                .andExpect(status().isBadRequest());

        List<UserData> userData = userDataRepository.findAll();
        assertThat(userData).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkRgIsRequired() throws Exception {
        int databaseSizeBeforeTest = userDataRepository.findAll().size();
        // set the field null
        userData.setRg(null);

        // Create the UserData, which fails.

        restUserDataMockMvc.perform(post("/api/user-data")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(userData)))
                .andExpect(status().isBadRequest());

        List<UserData> userData = userDataRepository.findAll();
        assertThat(userData).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCpfIsRequired() throws Exception {
        int databaseSizeBeforeTest = userDataRepository.findAll().size();
        // set the field null
        userData.setCpf(null);

        // Create the UserData, which fails.

        restUserDataMockMvc.perform(post("/api/user-data")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(userData)))
                .andExpect(status().isBadRequest());

        List<UserData> userData = userDataRepository.findAll();
        assertThat(userData).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllUserData() throws Exception {
        // Initialize the database
        userDataRepository.saveAndFlush(userData);

        // Get all the userData
        restUserDataMockMvc.perform(get("/api/user-data?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(userData.getId().intValue())))
                .andExpect(jsonPath("$.[*].primaryPhoneNumber").value(hasItem(DEFAULT_PRIMARY_PHONE_NUMBER.toString())))
                .andExpect(jsonPath("$.[*].secondaryPhoneNumber").value(hasItem(DEFAULT_SECONDARY_PHONE_NUMBER.toString())))
                .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS.toString())))
                .andExpect(jsonPath("$.[*].rg").value(hasItem(DEFAULT_RG.toString())))
                .andExpect(jsonPath("$.[*].cpf").value(hasItem(DEFAULT_CPF.toString())))
                .andExpect(jsonPath("$.[*].extra").value(hasItem(DEFAULT_EXTRA.toString())))
                .andExpect(jsonPath("$.[*].available").value(hasItem(DEFAULT_AVAILABLE.booleanValue())))
                .andExpect(jsonPath("$.[*].availableHoursPerWeek").value(hasItem(DEFAULT_AVAILABLE_HOURS_PER_WEEK)))
                .andExpect(jsonPath("$.[*].initialCostPerHour").value(hasItem(DEFAULT_INITIAL_COST_PER_HOUR)))
                .andExpect(jsonPath("$.[*].bankAgency").value(hasItem(DEFAULT_BANK_AGENCY.toString())))
                .andExpect(jsonPath("$.[*].bankAccount").value(hasItem(DEFAULT_BANK_ACCOUNT.toString())));
    }

    @Test
    @Transactional
    public void getUserData() throws Exception {
        // Initialize the database
        userDataRepository.saveAndFlush(userData);

        // Get the userData
        restUserDataMockMvc.perform(get("/api/user-data/{id}", userData.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(userData.getId().intValue()))
            .andExpect(jsonPath("$.primaryPhoneNumber").value(DEFAULT_PRIMARY_PHONE_NUMBER.toString()))
            .andExpect(jsonPath("$.secondaryPhoneNumber").value(DEFAULT_SECONDARY_PHONE_NUMBER.toString()))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS.toString()))
            .andExpect(jsonPath("$.rg").value(DEFAULT_RG.toString()))
            .andExpect(jsonPath("$.cpf").value(DEFAULT_CPF.toString()))
            .andExpect(jsonPath("$.extra").value(DEFAULT_EXTRA.toString()))
            .andExpect(jsonPath("$.available").value(DEFAULT_AVAILABLE.booleanValue()))
            .andExpect(jsonPath("$.availableHoursPerWeek").value(DEFAULT_AVAILABLE_HOURS_PER_WEEK))
            .andExpect(jsonPath("$.initialCostPerHour").value(DEFAULT_INITIAL_COST_PER_HOUR))
            .andExpect(jsonPath("$.bankAgency").value(DEFAULT_BANK_AGENCY.toString()))
            .andExpect(jsonPath("$.bankAccount").value(DEFAULT_BANK_ACCOUNT.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingUserData() throws Exception {
        // Get the userData
        restUserDataMockMvc.perform(get("/api/user-data/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUserData() throws Exception {
        // Initialize the database
        userDataRepository.saveAndFlush(userData);
        userDataSearchRepository.save(userData);
        int databaseSizeBeforeUpdate = userDataRepository.findAll().size();

        // Update the userData
        UserData updatedUserData = userDataRepository.findOne(userData.getId());
        updatedUserData
                .primaryPhoneNumber(UPDATED_PRIMARY_PHONE_NUMBER)
                .secondaryPhoneNumber(UPDATED_SECONDARY_PHONE_NUMBER)
                .address(UPDATED_ADDRESS)
                .rg(UPDATED_RG)
                .cpf(UPDATED_CPF)
                .extra(UPDATED_EXTRA)
                .available(UPDATED_AVAILABLE)
                .availableHoursPerWeek(UPDATED_AVAILABLE_HOURS_PER_WEEK)
                .initialCostPerHour(UPDATED_INITIAL_COST_PER_HOUR)
                .bankAgency(UPDATED_BANK_AGENCY)
                .bankAccount(UPDATED_BANK_ACCOUNT);

        restUserDataMockMvc.perform(put("/api/user-data")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedUserData)))
                .andExpect(status().isOk());

        // Validate the UserData in the database
        List<UserData> userData = userDataRepository.findAll();
        assertThat(userData).hasSize(databaseSizeBeforeUpdate);
        UserData testUserData = userData.get(userData.size() - 1);
        assertThat(testUserData.getPrimaryPhoneNumber()).isEqualTo(UPDATED_PRIMARY_PHONE_NUMBER);
        assertThat(testUserData.getSecondaryPhoneNumber()).isEqualTo(UPDATED_SECONDARY_PHONE_NUMBER);
        assertThat(testUserData.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testUserData.getRg()).isEqualTo(UPDATED_RG);
        assertThat(testUserData.getCpf()).isEqualTo(UPDATED_CPF);
        assertThat(testUserData.getExtra()).isEqualTo(UPDATED_EXTRA);
        assertThat(testUserData.isAvailable()).isEqualTo(UPDATED_AVAILABLE);
        assertThat(testUserData.getAvailableHoursPerWeek()).isEqualTo(UPDATED_AVAILABLE_HOURS_PER_WEEK);
        assertThat(testUserData.getInitialCostPerHour()).isEqualTo(UPDATED_INITIAL_COST_PER_HOUR);
        assertThat(testUserData.getBankAgency()).isEqualTo(UPDATED_BANK_AGENCY);
        assertThat(testUserData.getBankAccount()).isEqualTo(UPDATED_BANK_ACCOUNT);

        // Validate the UserData in ElasticSearch
        UserData userDataEs = userDataSearchRepository.findOne(testUserData.getId());
        assertThat(userDataEs).isEqualToComparingFieldByField(testUserData);
    }

    @Test
    @Transactional
    public void deleteUserData() throws Exception {
        // Initialize the database
        userDataRepository.saveAndFlush(userData);
        userDataSearchRepository.save(userData);
        int databaseSizeBeforeDelete = userDataRepository.findAll().size();

        // Get the userData
        restUserDataMockMvc.perform(delete("/api/user-data/{id}", userData.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate ElasticSearch is empty
        boolean userDataExistsInEs = userDataSearchRepository.exists(userData.getId());
        assertThat(userDataExistsInEs).isFalse();

        // Validate the database is empty
        List<UserData> userData = userDataRepository.findAll();
        assertThat(userData).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchUserData() throws Exception {
        // Initialize the database
        userDataRepository.saveAndFlush(userData);
        userDataSearchRepository.save(userData);

        // Search the userData
        restUserDataMockMvc.perform(get("/api/_search/user-data?query=id:" + userData.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userData.getId().intValue())))
            .andExpect(jsonPath("$.[*].primaryPhoneNumber").value(hasItem(DEFAULT_PRIMARY_PHONE_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].secondaryPhoneNumber").value(hasItem(DEFAULT_SECONDARY_PHONE_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS.toString())))
            .andExpect(jsonPath("$.[*].rg").value(hasItem(DEFAULT_RG.toString())))
            .andExpect(jsonPath("$.[*].cpf").value(hasItem(DEFAULT_CPF.toString())))
            .andExpect(jsonPath("$.[*].extra").value(hasItem(DEFAULT_EXTRA.toString())))
            .andExpect(jsonPath("$.[*].available").value(hasItem(DEFAULT_AVAILABLE.booleanValue())))
            .andExpect(jsonPath("$.[*].availableHoursPerWeek").value(hasItem(DEFAULT_AVAILABLE_HOURS_PER_WEEK)))
            .andExpect(jsonPath("$.[*].initialCostPerHour").value(hasItem(DEFAULT_INITIAL_COST_PER_HOUR)))
            .andExpect(jsonPath("$.[*].bankAgency").value(hasItem(DEFAULT_BANK_AGENCY.toString())))
            .andExpect(jsonPath("$.[*].bankAccount").value(hasItem(DEFAULT_BANK_ACCOUNT.toString())));
    }
}
