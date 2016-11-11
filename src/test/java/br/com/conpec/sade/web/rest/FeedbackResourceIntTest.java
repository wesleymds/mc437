package br.com.conpec.sade.web.rest;

import br.com.conpec.sade.Mc437App;

import br.com.conpec.sade.domain.Feedback;
import br.com.conpec.sade.repository.FeedbackRepository;
import br.com.conpec.sade.repository.search.FeedbackSearchRepository;

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
 * Test class for the FeedbackResource REST controller.
 *
 * @see FeedbackResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Mc437App.class)
public class FeedbackResourceIntTest {

    private static final Integer DEFAULT_COMMITMENT = 1;
    private static final Integer UPDATED_COMMITMENT = 2;

    private static final Integer DEFAULT_COMMUNICATION = 1;
    private static final Integer UPDATED_COMMUNICATION = 2;

    private static final Integer DEFAULT_PUNCTUALITY = 1;
    private static final Integer UPDATED_PUNCTUALITY = 2;

    private static final Integer DEFAULT_QUALITY = 1;
    private static final Integer UPDATED_QUALITY = 2;

    private static final Integer DEFAULT_TECHNICAL_KNOWLEDGE = 1;
    private static final Integer UPDATED_TECHNICAL_KNOWLEDGE = 2;

    private static final Integer DEFAULT_BONUS = 1;
    private static final Integer UPDATED_BONUS = 2;

    private static final String DEFAULT_EXTRA = "AAAAA";
    private static final String UPDATED_EXTRA = "BBBBB";

    @Inject
    private FeedbackRepository feedbackRepository;

    @Inject
    private FeedbackSearchRepository feedbackSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restFeedbackMockMvc;

    private Feedback feedback;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        FeedbackResource feedbackResource = new FeedbackResource();
        ReflectionTestUtils.setField(feedbackResource, "feedbackSearchRepository", feedbackSearchRepository);
        ReflectionTestUtils.setField(feedbackResource, "feedbackRepository", feedbackRepository);
        this.restFeedbackMockMvc = MockMvcBuilders.standaloneSetup(feedbackResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Feedback createEntity(EntityManager em) {
        Feedback feedback = new Feedback()
                .commitment(DEFAULT_COMMITMENT)
                .communication(DEFAULT_COMMUNICATION)
                .punctuality(DEFAULT_PUNCTUALITY)
                .quality(DEFAULT_QUALITY)
                .technicalKnowledge(DEFAULT_TECHNICAL_KNOWLEDGE)
                .bonus(DEFAULT_BONUS)
                .extra(DEFAULT_EXTRA);
        return feedback;
    }

    @Before
    public void initTest() {
        feedbackSearchRepository.deleteAll();
        feedback = createEntity(em);
    }

    @Test
    @Transactional
    public void createFeedback() throws Exception {
        int databaseSizeBeforeCreate = feedbackRepository.findAll().size();

        // Create the Feedback

        restFeedbackMockMvc.perform(post("/api/feedbacks")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(feedback)))
                .andExpect(status().isCreated());

        // Validate the Feedback in the database
        List<Feedback> feedbacks = feedbackRepository.findAll();
        assertThat(feedbacks).hasSize(databaseSizeBeforeCreate + 1);
        Feedback testFeedback = feedbacks.get(feedbacks.size() - 1);
        assertThat(testFeedback.getCommitment()).isEqualTo(DEFAULT_COMMITMENT);
        assertThat(testFeedback.getCommunication()).isEqualTo(DEFAULT_COMMUNICATION);
        assertThat(testFeedback.getPunctuality()).isEqualTo(DEFAULT_PUNCTUALITY);
        assertThat(testFeedback.getQuality()).isEqualTo(DEFAULT_QUALITY);
        assertThat(testFeedback.getTechnicalKnowledge()).isEqualTo(DEFAULT_TECHNICAL_KNOWLEDGE);
        assertThat(testFeedback.getBonus()).isEqualTo(DEFAULT_BONUS);
        assertThat(testFeedback.getExtra()).isEqualTo(DEFAULT_EXTRA);

        // Validate the Feedback in ElasticSearch
        Feedback feedbackEs = feedbackSearchRepository.findOne(testFeedback.getId());
        assertThat(feedbackEs).isEqualToComparingFieldByField(testFeedback);
    }

    @Test
    @Transactional
    public void getAllFeedbacks() throws Exception {
        // Initialize the database
        feedbackRepository.saveAndFlush(feedback);

        // Get all the feedbacks
        restFeedbackMockMvc.perform(get("/api/feedbacks?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(feedback.getId().intValue())))
                .andExpect(jsonPath("$.[*].commitment").value(hasItem(DEFAULT_COMMITMENT)))
                .andExpect(jsonPath("$.[*].communication").value(hasItem(DEFAULT_COMMUNICATION)))
                .andExpect(jsonPath("$.[*].punctuality").value(hasItem(DEFAULT_PUNCTUALITY)))
                .andExpect(jsonPath("$.[*].quality").value(hasItem(DEFAULT_QUALITY)))
                .andExpect(jsonPath("$.[*].technicalKnowledge").value(hasItem(DEFAULT_TECHNICAL_KNOWLEDGE)))
                .andExpect(jsonPath("$.[*].bonus").value(hasItem(DEFAULT_BONUS)))
                .andExpect(jsonPath("$.[*].extra").value(hasItem(DEFAULT_EXTRA.toString())));
    }

    @Test
    @Transactional
    public void getFeedback() throws Exception {
        // Initialize the database
        feedbackRepository.saveAndFlush(feedback);

        // Get the feedback
        restFeedbackMockMvc.perform(get("/api/feedbacks/{id}", feedback.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(feedback.getId().intValue()))
            .andExpect(jsonPath("$.commitment").value(DEFAULT_COMMITMENT))
            .andExpect(jsonPath("$.communication").value(DEFAULT_COMMUNICATION))
            .andExpect(jsonPath("$.punctuality").value(DEFAULT_PUNCTUALITY))
            .andExpect(jsonPath("$.quality").value(DEFAULT_QUALITY))
            .andExpect(jsonPath("$.technicalKnowledge").value(DEFAULT_TECHNICAL_KNOWLEDGE))
            .andExpect(jsonPath("$.bonus").value(DEFAULT_BONUS))
            .andExpect(jsonPath("$.extra").value(DEFAULT_EXTRA.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingFeedback() throws Exception {
        // Get the feedback
        restFeedbackMockMvc.perform(get("/api/feedbacks/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFeedback() throws Exception {
        // Initialize the database
        feedbackRepository.saveAndFlush(feedback);
        feedbackSearchRepository.save(feedback);
        int databaseSizeBeforeUpdate = feedbackRepository.findAll().size();

        // Update the feedback
        Feedback updatedFeedback = feedbackRepository.findOne(feedback.getId());
        updatedFeedback
                .commitment(UPDATED_COMMITMENT)
                .communication(UPDATED_COMMUNICATION)
                .punctuality(UPDATED_PUNCTUALITY)
                .quality(UPDATED_QUALITY)
                .technicalKnowledge(UPDATED_TECHNICAL_KNOWLEDGE)
                .bonus(UPDATED_BONUS)
                .extra(UPDATED_EXTRA);

        restFeedbackMockMvc.perform(put("/api/feedbacks")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedFeedback)))
                .andExpect(status().isOk());

        // Validate the Feedback in the database
        List<Feedback> feedbacks = feedbackRepository.findAll();
        assertThat(feedbacks).hasSize(databaseSizeBeforeUpdate);
        Feedback testFeedback = feedbacks.get(feedbacks.size() - 1);
        assertThat(testFeedback.getCommitment()).isEqualTo(UPDATED_COMMITMENT);
        assertThat(testFeedback.getCommunication()).isEqualTo(UPDATED_COMMUNICATION);
        assertThat(testFeedback.getPunctuality()).isEqualTo(UPDATED_PUNCTUALITY);
        assertThat(testFeedback.getQuality()).isEqualTo(UPDATED_QUALITY);
        assertThat(testFeedback.getTechnicalKnowledge()).isEqualTo(UPDATED_TECHNICAL_KNOWLEDGE);
        assertThat(testFeedback.getBonus()).isEqualTo(UPDATED_BONUS);
        assertThat(testFeedback.getExtra()).isEqualTo(UPDATED_EXTRA);

        // Validate the Feedback in ElasticSearch
        Feedback feedbackEs = feedbackSearchRepository.findOne(testFeedback.getId());
        assertThat(feedbackEs).isEqualToComparingFieldByField(testFeedback);
    }

    @Test
    @Transactional
    public void deleteFeedback() throws Exception {
        // Initialize the database
        feedbackRepository.saveAndFlush(feedback);
        feedbackSearchRepository.save(feedback);
        int databaseSizeBeforeDelete = feedbackRepository.findAll().size();

        // Get the feedback
        restFeedbackMockMvc.perform(delete("/api/feedbacks/{id}", feedback.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate ElasticSearch is empty
        boolean feedbackExistsInEs = feedbackSearchRepository.exists(feedback.getId());
        assertThat(feedbackExistsInEs).isFalse();

        // Validate the database is empty
        List<Feedback> feedbacks = feedbackRepository.findAll();
        assertThat(feedbacks).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchFeedback() throws Exception {
        // Initialize the database
        feedbackRepository.saveAndFlush(feedback);
        feedbackSearchRepository.save(feedback);

        // Search the feedback
        restFeedbackMockMvc.perform(get("/api/_search/feedbacks?query=id:" + feedback.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(feedback.getId().intValue())))
            .andExpect(jsonPath("$.[*].commitment").value(hasItem(DEFAULT_COMMITMENT)))
            .andExpect(jsonPath("$.[*].communication").value(hasItem(DEFAULT_COMMUNICATION)))
            .andExpect(jsonPath("$.[*].punctuality").value(hasItem(DEFAULT_PUNCTUALITY)))
            .andExpect(jsonPath("$.[*].quality").value(hasItem(DEFAULT_QUALITY)))
            .andExpect(jsonPath("$.[*].technicalKnowledge").value(hasItem(DEFAULT_TECHNICAL_KNOWLEDGE)))
            .andExpect(jsonPath("$.[*].bonus").value(hasItem(DEFAULT_BONUS)))
            .andExpect(jsonPath("$.[*].extra").value(hasItem(DEFAULT_EXTRA.toString())));
    }
}
