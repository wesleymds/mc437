package br.com.conpec.sade.web.rest;

import br.com.conpec.sade.Mc437App;

import br.com.conpec.sade.domain.ExternalResource;
import br.com.conpec.sade.repository.ExternalResourceRepository;
import br.com.conpec.sade.repository.search.ExternalResourceSearchRepository;

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
 * Test class for the ExternalResourceResource REST controller.
 *
 * @see ExternalResourceResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Mc437App.class)
public class ExternalResourceResourceIntTest {

    private static final String DEFAULT_URL = "AAAAA";
    private static final String UPDATED_URL = "BBBBB";

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";

    @Inject
    private ExternalResourceRepository externalResourceRepository;

    @Inject
    private ExternalResourceSearchRepository externalResourceSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restExternalResourceMockMvc;

    private ExternalResource externalResource;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ExternalResourceResource externalResourceResource = new ExternalResourceResource();
        ReflectionTestUtils.setField(externalResourceResource, "externalResourceSearchRepository", externalResourceSearchRepository);
        ReflectionTestUtils.setField(externalResourceResource, "externalResourceRepository", externalResourceRepository);
        this.restExternalResourceMockMvc = MockMvcBuilders.standaloneSetup(externalResourceResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ExternalResource createEntity(EntityManager em) {
        ExternalResource externalResource = new ExternalResource()
                .url(DEFAULT_URL)
                .name(DEFAULT_NAME);
        return externalResource;
    }

    @Before
    public void initTest() {
        externalResourceSearchRepository.deleteAll();
        externalResource = createEntity(em);
    }

    @Test
    @Transactional
    public void createExternalResource() throws Exception {
        int databaseSizeBeforeCreate = externalResourceRepository.findAll().size();

        // Create the ExternalResource

        restExternalResourceMockMvc.perform(post("/api/external-resources")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(externalResource)))
                .andExpect(status().isCreated());

        // Validate the ExternalResource in the database
        List<ExternalResource> externalResources = externalResourceRepository.findAll();
        assertThat(externalResources).hasSize(databaseSizeBeforeCreate + 1);
        ExternalResource testExternalResource = externalResources.get(externalResources.size() - 1);
        assertThat(testExternalResource.getUrl()).isEqualTo(DEFAULT_URL);
        assertThat(testExternalResource.getName()).isEqualTo(DEFAULT_NAME);

        // Validate the ExternalResource in ElasticSearch
        ExternalResource externalResourceEs = externalResourceSearchRepository.findOne(testExternalResource.getId());
        assertThat(externalResourceEs).isEqualToComparingFieldByField(testExternalResource);
    }

    @Test
    @Transactional
    public void checkUrlIsRequired() throws Exception {
        int databaseSizeBeforeTest = externalResourceRepository.findAll().size();
        // set the field null
        externalResource.setUrl(null);

        // Create the ExternalResource, which fails.

        restExternalResourceMockMvc.perform(post("/api/external-resources")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(externalResource)))
                .andExpect(status().isBadRequest());

        List<ExternalResource> externalResources = externalResourceRepository.findAll();
        assertThat(externalResources).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = externalResourceRepository.findAll().size();
        // set the field null
        externalResource.setName(null);

        // Create the ExternalResource, which fails.

        restExternalResourceMockMvc.perform(post("/api/external-resources")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(externalResource)))
                .andExpect(status().isBadRequest());

        List<ExternalResource> externalResources = externalResourceRepository.findAll();
        assertThat(externalResources).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllExternalResources() throws Exception {
        // Initialize the database
        externalResourceRepository.saveAndFlush(externalResource);

        // Get all the externalResources
        restExternalResourceMockMvc.perform(get("/api/external-resources?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(externalResource.getId().intValue())))
                .andExpect(jsonPath("$.[*].url").value(hasItem(DEFAULT_URL.toString())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getExternalResource() throws Exception {
        // Initialize the database
        externalResourceRepository.saveAndFlush(externalResource);

        // Get the externalResource
        restExternalResourceMockMvc.perform(get("/api/external-resources/{id}", externalResource.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(externalResource.getId().intValue()))
            .andExpect(jsonPath("$.url").value(DEFAULT_URL.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingExternalResource() throws Exception {
        // Get the externalResource
        restExternalResourceMockMvc.perform(get("/api/external-resources/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateExternalResource() throws Exception {
        // Initialize the database
        externalResourceRepository.saveAndFlush(externalResource);
        externalResourceSearchRepository.save(externalResource);
        int databaseSizeBeforeUpdate = externalResourceRepository.findAll().size();

        // Update the externalResource
        ExternalResource updatedExternalResource = externalResourceRepository.findOne(externalResource.getId());
        updatedExternalResource
                .url(UPDATED_URL)
                .name(UPDATED_NAME);

        restExternalResourceMockMvc.perform(put("/api/external-resources")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedExternalResource)))
                .andExpect(status().isOk());

        // Validate the ExternalResource in the database
        List<ExternalResource> externalResources = externalResourceRepository.findAll();
        assertThat(externalResources).hasSize(databaseSizeBeforeUpdate);
        ExternalResource testExternalResource = externalResources.get(externalResources.size() - 1);
        assertThat(testExternalResource.getUrl()).isEqualTo(UPDATED_URL);
        assertThat(testExternalResource.getName()).isEqualTo(UPDATED_NAME);

        // Validate the ExternalResource in ElasticSearch
        ExternalResource externalResourceEs = externalResourceSearchRepository.findOne(testExternalResource.getId());
        assertThat(externalResourceEs).isEqualToComparingFieldByField(testExternalResource);
    }

    @Test
    @Transactional
    public void deleteExternalResource() throws Exception {
        // Initialize the database
        externalResourceRepository.saveAndFlush(externalResource);
        externalResourceSearchRepository.save(externalResource);
        int databaseSizeBeforeDelete = externalResourceRepository.findAll().size();

        // Get the externalResource
        restExternalResourceMockMvc.perform(delete("/api/external-resources/{id}", externalResource.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate ElasticSearch is empty
        boolean externalResourceExistsInEs = externalResourceSearchRepository.exists(externalResource.getId());
        assertThat(externalResourceExistsInEs).isFalse();

        // Validate the database is empty
        List<ExternalResource> externalResources = externalResourceRepository.findAll();
        assertThat(externalResources).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchExternalResource() throws Exception {
        // Initialize the database
        externalResourceRepository.saveAndFlush(externalResource);
        externalResourceSearchRepository.save(externalResource);

        // Search the externalResource
        restExternalResourceMockMvc.perform(get("/api/_search/external-resources?query=id:" + externalResource.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(externalResource.getId().intValue())))
            .andExpect(jsonPath("$.[*].url").value(hasItem(DEFAULT_URL.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }
}
