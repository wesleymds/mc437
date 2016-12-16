package br.com.conpec.sade.web.rest;

import br.com.conpec.sade.domain.Project;
import br.com.conpec.sade.domain.User;
import br.com.conpec.sade.domain.UserData;
import br.com.conpec.sade.repository.ProjectRepository;
import br.com.conpec.sade.repository.UserDataRepository;
import br.com.conpec.sade.repository.UserRepository;
import br.com.conpec.sade.security.SecurityUtils;
import br.com.conpec.sade.web.rest.request.CreateFeedbackRequest;
import com.codahale.metrics.annotation.Timed;
import br.com.conpec.sade.domain.Feedback;

import br.com.conpec.sade.repository.FeedbackRepository;
import br.com.conpec.sade.repository.search.FeedbackSearchRepository;
import br.com.conpec.sade.web.rest.util.HeaderUtil;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing Feedback.
 */
@RestController
@RequestMapping("/api")
public class FeedbackResource {

    private static final int MIN_GRADE = 0;

    private static final int MAX_GRADE = 5;

    private final Logger log = LoggerFactory.getLogger(FeedbackResource.class);

    @Inject
    private FeedbackRepository feedbackRepository;

    @Inject
    private FeedbackSearchRepository feedbackSearchRepository;

    @Inject
    private UserRepository userRepository;

    @Inject
    private UserDataRepository userDataRepository;

    @Inject
    private ProjectRepository projectRepository;

    /**
     * POST  /feedbacks : Create a new feedback.
     *
     * @param request the feedback to create
     * @return the ResponseEntity with status 201 (Created) and with body the new feedback, or with status 400 (Bad Request) if the feedback has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/feedbacks")
    @Timed
    public ResponseEntity<Feedback> createFeedback(@RequestBody CreateFeedbackRequest request) throws URISyntaxException {
        log.debug("REST request to save Feedback : {}", request);

        final User user = userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin()).get();
        final UserData author = userDataRepository.findAll().stream()
            .filter(ud -> user.getId().equals(ud.getUser().getId()))
            .findFirst()
            .orElse(null);

        final UserData developer = userDataRepository.findOne(request.getDeveloperId());

        final Project project = projectRepository.findOne(request.getProjectId());

        Feedback feedback = new Feedback();
        feedback.setProject(project);
        feedback.setAuthor(author);
        feedback.setDeveloper(developer);
        feedback.setCommitment(request.getCommitment());
        feedback.setCommunication(request.getCommunication());
        feedback.setPunctuality(request.getPunctuality());
        feedback.setQuality(request.getQuality());
        feedback.setTechnicalKnowledge(request.getTechnicalKnowledge());
        feedback.setExtra(request.getExtra());

        Feedback result = feedbackRepository.save(feedback);
        feedbackSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/feedbacks/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("feedback", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /feedbacks : Updates an existing feedback.
     *
     * @param feedback the feedback to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated feedback,
     * or with status 400 (Bad Request) if the feedback is not valid,
     * or with status 500 (Internal Server Error) if the feedback couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/feedbacks")
    @Timed
    public ResponseEntity<Feedback> updateFeedback(@RequestBody Feedback feedback) throws URISyntaxException {
        log.debug("REST request to update Feedback : {}", feedback);

        Validate.inclusiveBetween(MIN_GRADE, MAX_GRADE, feedback.getCommitment(),
            "Commitment must be between " + MIN_GRADE + " and " + MAX_GRADE);
        Validate.inclusiveBetween(MIN_GRADE, MAX_GRADE, feedback.getCommunication(),
            "Communication must be between " + MIN_GRADE + " and " + MAX_GRADE);
        Validate.inclusiveBetween(MIN_GRADE, MAX_GRADE, feedback.getPunctuality(),
            "Punctuality must be between " + MIN_GRADE + " and " + MAX_GRADE);
        Validate.inclusiveBetween(MIN_GRADE, MAX_GRADE, feedback.getQuality(),
            "Quality must be between " + MIN_GRADE + " and " + MAX_GRADE);
        Validate.inclusiveBetween(MIN_GRADE, MAX_GRADE, feedback.getTechnicalKnowledge(),
            "Technical Knowledge must be between " + MIN_GRADE + " and " + MAX_GRADE);

        Feedback result = feedbackRepository.save(feedback);
        feedbackSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("feedback", feedback.getId().toString()))
            .body(result);
    }

    /**
     * GET  /feedbacks : get all the feedbacks.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of feedbacks in body
     */
    @GetMapping("/feedbacks")
    @Timed
    public List<Feedback> getAllFeedbacks() {
        log.debug("REST request to get all Feedbacks");
        List<Feedback> feedbacks = feedbackRepository.findAll();
        return feedbacks;
    }

    /**
     * GET  /feedbacks/:id : get the "id" feedback.
     *
     * @param id the id of the feedback to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the feedback, or with status 404 (Not Found)
     */
    @GetMapping("/feedbacks/{id}")
    @Timed
    public ResponseEntity<Feedback> getFeedback(@PathVariable Long id) {
        log.debug("REST request to get Feedback : {}", id);
        Feedback feedback = feedbackRepository.findOne(id);
        return Optional.ofNullable(feedback)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /feedbacks/:id : delete the "id" feedback.
     *
     * @param id the id of the feedback to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/feedbacks/{id}")
    @Timed
    public ResponseEntity<Void> deleteFeedback(@PathVariable Long id) {
        log.debug("REST request to delete Feedback : {}", id);
        feedbackRepository.delete(id);
        feedbackSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("feedback", id.toString())).build();
    }

    /**
     * SEARCH  /_search/feedbacks?query=:query : search for the feedback corresponding
     * to the query.
     *
     * @param query the query of the feedback search
     * @return the result of the search
     */
    @GetMapping("/_search/feedbacks")
    @Timed
    public List<Feedback> searchFeedbacks(@RequestParam String query) {
        log.debug("REST request to search Feedbacks for query {}", query);
        return StreamSupport
            .stream(feedbackSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }


}
