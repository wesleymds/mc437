package br.com.conpec.sade.web.rest;

import com.codahale.metrics.annotation.Timed;
import br.com.conpec.sade.domain.UserData;

import br.com.conpec.sade.repository.UserDataRepository;
import br.com.conpec.sade.repository.search.UserDataSearchRepository;
import br.com.conpec.sade.web.rest.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing UserData.
 */
@RestController
@RequestMapping("/api")
public class UserDataResource {

    private final Logger log = LoggerFactory.getLogger(UserDataResource.class);
        
    @Inject
    private UserDataRepository userDataRepository;

    @Inject
    private UserDataSearchRepository userDataSearchRepository;

    /**
     * POST  /user-data : Create a new userData.
     *
     * @param userData the userData to create
     * @return the ResponseEntity with status 201 (Created) and with body the new userData, or with status 400 (Bad Request) if the userData has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/user-data")
    @Timed
    public ResponseEntity<UserData> createUserData(@Valid @RequestBody UserData userData) throws URISyntaxException {
        log.debug("REST request to save UserData : {}", userData);
        if (userData.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("userData", "idexists", "A new userData cannot already have an ID")).body(null);
        }
        UserData result = userDataRepository.save(userData);
        userDataSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/user-data/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("userData", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /user-data : Updates an existing userData.
     *
     * @param userData the userData to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated userData,
     * or with status 400 (Bad Request) if the userData is not valid,
     * or with status 500 (Internal Server Error) if the userData couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/user-data")
    @Timed
    public ResponseEntity<UserData> updateUserData(@Valid @RequestBody UserData userData) throws URISyntaxException {
        log.debug("REST request to update UserData : {}", userData);
        if (userData.getId() == null) {
            return createUserData(userData);
        }
        UserData result = userDataRepository.save(userData);
        userDataSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("userData", userData.getId().toString()))
            .body(result);
    }

    /**
     * GET  /user-data : get all the userData.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of userData in body
     */
    @GetMapping("/user-data")
    @Timed
    public List<UserData> getAllUserData() {
        log.debug("REST request to get all UserData");
        List<UserData> userData = userDataRepository.findAllWithEagerRelationships();
        return userData;
    }

    /**
     * GET  /user-data/:id : get the "id" userData.
     *
     * @param id the id of the userData to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the userData, or with status 404 (Not Found)
     */
    @GetMapping("/user-data/{id}")
    @Timed
    public ResponseEntity<UserData> getUserData(@PathVariable Long id) {
        log.debug("REST request to get UserData : {}", id);
        UserData userData = userDataRepository.findOneWithEagerRelationships(id);
        return Optional.ofNullable(userData)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /user-data/:id : delete the "id" userData.
     *
     * @param id the id of the userData to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/user-data/{id}")
    @Timed
    public ResponseEntity<Void> deleteUserData(@PathVariable Long id) {
        log.debug("REST request to delete UserData : {}", id);
        userDataRepository.delete(id);
        userDataSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("userData", id.toString())).build();
    }

    /**
     * SEARCH  /_search/user-data?query=:query : search for the userData corresponding
     * to the query.
     *
     * @param query the query of the userData search 
     * @return the result of the search
     */
    @GetMapping("/_search/user-data")
    @Timed
    public List<UserData> searchUserData(@RequestParam String query) {
        log.debug("REST request to search UserData for query {}", query);
        return StreamSupport
            .stream(userDataSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }


}
