package br.com.conpec.sade.web.rest;

import com.codahale.metrics.annotation.Timed;
import br.com.conpec.sade.domain.ExternalResource;

import br.com.conpec.sade.repository.ExternalResourceRepository;
import br.com.conpec.sade.repository.search.ExternalResourceSearchRepository;
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
 * REST controller for managing ExternalResource.
 */
@RestController
@RequestMapping("/api")
public class ExternalResourceResource {

    private final Logger log = LoggerFactory.getLogger(ExternalResourceResource.class);
        
    @Inject
    private ExternalResourceRepository externalResourceRepository;

    @Inject
    private ExternalResourceSearchRepository externalResourceSearchRepository;

    /**
     * POST  /external-resources : Create a new externalResource.
     *
     * @param externalResource the externalResource to create
     * @return the ResponseEntity with status 201 (Created) and with body the new externalResource, or with status 400 (Bad Request) if the externalResource has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/external-resources")
    @Timed
    public ResponseEntity<ExternalResource> createExternalResource(@Valid @RequestBody ExternalResource externalResource) throws URISyntaxException {
        log.debug("REST request to save ExternalResource : {}", externalResource);
        if (externalResource.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("externalResource", "idexists", "A new externalResource cannot already have an ID")).body(null);
        }
        ExternalResource result = externalResourceRepository.save(externalResource);
        externalResourceSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/external-resources/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("externalResource", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /external-resources : Updates an existing externalResource.
     *
     * @param externalResource the externalResource to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated externalResource,
     * or with status 400 (Bad Request) if the externalResource is not valid,
     * or with status 500 (Internal Server Error) if the externalResource couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/external-resources")
    @Timed
    public ResponseEntity<ExternalResource> updateExternalResource(@Valid @RequestBody ExternalResource externalResource) throws URISyntaxException {
        log.debug("REST request to update ExternalResource : {}", externalResource);
        if (externalResource.getId() == null) {
            return createExternalResource(externalResource);
        }
        ExternalResource result = externalResourceRepository.save(externalResource);
        externalResourceSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("externalResource", externalResource.getId().toString()))
            .body(result);
    }

    /**
     * GET  /external-resources : get all the externalResources.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of externalResources in body
     */
    @GetMapping("/external-resources")
    @Timed
    public List<ExternalResource> getAllExternalResources() {
        log.debug("REST request to get all ExternalResources");
        List<ExternalResource> externalResources = externalResourceRepository.findAll();
        return externalResources;
    }

    /**
     * GET  /external-resources/:id : get the "id" externalResource.
     *
     * @param id the id of the externalResource to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the externalResource, or with status 404 (Not Found)
     */
    @GetMapping("/external-resources/{id}")
    @Timed
    public ResponseEntity<ExternalResource> getExternalResource(@PathVariable Long id) {
        log.debug("REST request to get ExternalResource : {}", id);
        ExternalResource externalResource = externalResourceRepository.findOne(id);
        return Optional.ofNullable(externalResource)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /external-resources/:id : delete the "id" externalResource.
     *
     * @param id the id of the externalResource to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/external-resources/{id}")
    @Timed
    public ResponseEntity<Void> deleteExternalResource(@PathVariable Long id) {
        log.debug("REST request to delete ExternalResource : {}", id);
        externalResourceRepository.delete(id);
        externalResourceSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("externalResource", id.toString())).build();
    }

    /**
     * SEARCH  /_search/external-resources?query=:query : search for the externalResource corresponding
     * to the query.
     *
     * @param query the query of the externalResource search 
     * @return the result of the search
     */
    @GetMapping("/_search/external-resources")
    @Timed
    public List<ExternalResource> searchExternalResources(@RequestParam String query) {
        log.debug("REST request to search ExternalResources for query {}", query);
        return StreamSupport
            .stream(externalResourceSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }


}
