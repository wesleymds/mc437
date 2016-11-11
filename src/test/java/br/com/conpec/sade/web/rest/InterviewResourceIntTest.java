package br.com.conpec.sade.web.rest;

import br.com.conpec.sade.Mc437App;

import br.com.conpec.sade.domain.Interview;
import br.com.conpec.sade.repository.InterviewRepository;
import br.com.conpec.sade.repository.search.InterviewSearchRepository;

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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the InterviewResource REST controller.
 *
 * @see InterviewResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Mc437App.class)
public class InterviewResourceIntTest {

    private static final LocalDate DEFAULT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());

    @Inject
    private InterviewRepository interviewRepository;

    @Inject
    private InterviewSearchRepository interviewSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restInterviewMockMvc;

    private Interview interview;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        InterviewResource interviewResource = new InterviewResource();
        ReflectionTestUtils.setField(interviewResource, "interviewSearchRepository", interviewSearchRepository);
        ReflectionTestUtils.setField(interviewResource, "interviewRepository", interviewRepository);
        this.restInterviewMockMvc = MockMvcBuilders.standaloneSetup(interviewResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Interview createEntity(EntityManager em) {
        Interview interview = new Interview()
                .date(DEFAULT_DATE);
        return interview;
    }

    @Before
    public void initTest() {
        interviewSearchRepository.deleteAll();
        interview = createEntity(em);
    }

    @Test
    @Transactional
    public void createInterview() throws Exception {
        int databaseSizeBeforeCreate = interviewRepository.findAll().size();

        // Create the Interview

        restInterviewMockMvc.perform(post("/api/interviews")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(interview)))
                .andExpect(status().isCreated());

        // Validate the Interview in the database
        List<Interview> interviews = interviewRepository.findAll();
        assertThat(interviews).hasSize(databaseSizeBeforeCreate + 1);
        Interview testInterview = interviews.get(interviews.size() - 1);
        assertThat(testInterview.getDate()).isEqualTo(DEFAULT_DATE);

        // Validate the Interview in ElasticSearch
        Interview interviewEs = interviewSearchRepository.findOne(testInterview.getId());
        assertThat(interviewEs).isEqualToComparingFieldByField(testInterview);
    }

    @Test
    @Transactional
    public void getAllInterviews() throws Exception {
        // Initialize the database
        interviewRepository.saveAndFlush(interview);

        // Get all the interviews
        restInterviewMockMvc.perform(get("/api/interviews?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(interview.getId().intValue())))
                .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())));
    }

    @Test
    @Transactional
    public void getInterview() throws Exception {
        // Initialize the database
        interviewRepository.saveAndFlush(interview);

        // Get the interview
        restInterviewMockMvc.perform(get("/api/interviews/{id}", interview.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(interview.getId().intValue()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingInterview() throws Exception {
        // Get the interview
        restInterviewMockMvc.perform(get("/api/interviews/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInterview() throws Exception {
        // Initialize the database
        interviewRepository.saveAndFlush(interview);
        interviewSearchRepository.save(interview);
        int databaseSizeBeforeUpdate = interviewRepository.findAll().size();

        // Update the interview
        Interview updatedInterview = interviewRepository.findOne(interview.getId());
        updatedInterview
                .date(UPDATED_DATE);

        restInterviewMockMvc.perform(put("/api/interviews")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedInterview)))
                .andExpect(status().isOk());

        // Validate the Interview in the database
        List<Interview> interviews = interviewRepository.findAll();
        assertThat(interviews).hasSize(databaseSizeBeforeUpdate);
        Interview testInterview = interviews.get(interviews.size() - 1);
        assertThat(testInterview.getDate()).isEqualTo(UPDATED_DATE);

        // Validate the Interview in ElasticSearch
        Interview interviewEs = interviewSearchRepository.findOne(testInterview.getId());
        assertThat(interviewEs).isEqualToComparingFieldByField(testInterview);
    }

    @Test
    @Transactional
    public void deleteInterview() throws Exception {
        // Initialize the database
        interviewRepository.saveAndFlush(interview);
        interviewSearchRepository.save(interview);
        int databaseSizeBeforeDelete = interviewRepository.findAll().size();

        // Get the interview
        restInterviewMockMvc.perform(delete("/api/interviews/{id}", interview.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate ElasticSearch is empty
        boolean interviewExistsInEs = interviewSearchRepository.exists(interview.getId());
        assertThat(interviewExistsInEs).isFalse();

        // Validate the database is empty
        List<Interview> interviews = interviewRepository.findAll();
        assertThat(interviews).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchInterview() throws Exception {
        // Initialize the database
        interviewRepository.saveAndFlush(interview);
        interviewSearchRepository.save(interview);

        // Search the interview
        restInterviewMockMvc.perform(get("/api/_search/interviews?query=id:" + interview.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(interview.getId().intValue())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())));
    }
}
