package br.com.conpec.sade.web.rest;

import br.com.conpec.sade.domain.*;
import br.com.conpec.sade.domain.enumeration.ProjectStatus;
import br.com.conpec.sade.repository.ExternalResourceRepository;
import br.com.conpec.sade.repository.UserDataRepository;
import br.com.conpec.sade.security.AuthoritiesConstants;
import br.com.conpec.sade.web.rest.request.CreateExternalResourceRequest;
import br.com.conpec.sade.web.rest.request.CreateProjectRequest;
import com.codahale.metrics.annotation.Timed;

import br.com.conpec.sade.repository.ProjectRepository;
import br.com.conpec.sade.repository.search.ProjectSearchRepository;
import br.com.conpec.sade.web.rest.util.HeaderUtil;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing Project.
 */
@RestController
@RequestMapping("/api")
public class ProjectResource {

    private final Logger log = LoggerFactory.getLogger(ProjectResource.class);

    @Inject
    private ProjectRepository projectRepository;

    @Inject
    private ProjectSearchRepository projectSearchRepository;

    @Inject
    private UserDataRepository userDataRepository;

    @Inject
    private ExternalResourceRepository externalResourceRepository;

    /**
     * POST  /projects : Create a new project.
     *
     * @param request the project to create
     * @return the ResponseEntity with status 201 (Created) and with body the new project, or with status 400 (Bad Request) if the project has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/projects")
    @Timed
    public ResponseEntity<Project> createProject(@Valid @RequestBody CreateProjectRequest request) throws URISyntaxException {
        log.debug("REST request to save Project : {}", request);

        // TODO: throw specific exception instead of IllegalArgumentException on validations

        // Find and validate manager
        UserData manager = userDataRepository.findOne(request.getManagerId());
        Validate.notNull(manager, "Cannot find manager with id=" + request.getManagerId());
        Validate.isTrue(manager.getUser().getActivated(), "Manager is not activated");
        Validate.isTrue(isMember(manager.getUser()), "Manager is not a member");

        // Find and validate assessors
        Set<UserData> assessors = new HashSet<>(userDataRepository.findAll(request.getAssessorsIds()));
        assessors.forEach(assessor -> Validate.notNull(assessor, "Invalid assessor"));
        assessors.forEach(assessor -> Validate.isTrue(isMember(assessor.getUser()), "Assessor " +
            assessor.getUser().getFirstName() + "is not a member"));

        // Find and validate developers
        Set<UserData> developers = new HashSet<>(userDataRepository.findAll(request.getDevelopersIds()));
        developers.forEach(developer -> Validate.notNull(developer, "Invalid developer"));

        // Validate external resources
        request.getResources().forEach(this::validateExternalResource);

        // Create project instance
        Project project = new Project();
        project.setName(request.getName());
        project.setDevelopers(developers);
        project.setAssessors(assessors);
        project.setManager(manager);
        project.setStatus(ProjectStatus.DRAFT);

        // Save project on DB
        Project result = projectRepository.save(project);
        projectSearchRepository.save(result);

        // Save project's resources
        request.getResources()
            .forEach(resourceRequest -> saveResource(resourceRequest, result.getId()));

        return ResponseEntity.created(new URI("/api/projects/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("project", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /projects : Updates an existing project.
     *
     * @param project the project to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated project,
     * or with status 400 (Bad Request) if the project is not valid,
     * or with status 500 (Internal Server Error) if the project couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/projects")
    @Timed
    public ResponseEntity<Project> updateProject(@Valid @RequestBody Project project) throws URISyntaxException {
        log.debug("REST request to update Project : {}", project);
        Project result = projectRepository.save(project);
        projectSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("project", project.getId().toString()))
            .body(result);
    }

    @GetMapping("/projects/{id}/start")
    @Timed
    public ResponseEntity<Project> startProject(@PathVariable Long id) {
      Project project = projectRepository.findOne(id);
      Validate.notNull(project, "Cannot find project with this id");
      Validate.isTrue(project.getStatus() == ProjectStatus.DRAFT);
      project.setStatus(ProjectStatus.UNDER_DEVELOPMENT);
      Project result = projectRepository.save(project);
      return ResponseEntity.ok()
          .headers(HeaderUtil.createEntityUpdateAlert("project", project.getId().toString()))
          .body(result);
    }

    @GetMapping("/projects/{id}/finish")
    @Timed
    public ResponseEntity<Project> finishProject(@PathVariable Long id) {
      Project project = projectRepository.findOne(id);
      Validate.notNull(project, "Cannot find project with this id");
      Validate.isTrue(project.getStatus() == ProjectStatus.UNDER_DEVELOPMENT);
      project.setStatus(ProjectStatus.DONE);
      Project result = projectRepository.save(project);
      return ResponseEntity.ok()
          .headers(HeaderUtil.createEntityUpdateAlert("project", project.getId().toString()))
          .body(result);
    }

    @GetMapping("/projects/{id}/cancel")
    @Timed
    public ResponseEntity<Project> cancelProject(@PathVariable Long id) {
      Project project = projectRepository.findOne(id);
      Validate.notNull(project, "Cannot find project with this id");
      Validate.isTrue(project.getStatus() == ProjectStatus.DRAFT || project.getStatus() == ProjectStatus.UNDER_DEVELOPMENT);
      project.setStatus(ProjectStatus.CANCELLED);
      Project result = projectRepository.save(project);
      return ResponseEntity.ok()
          .headers(HeaderUtil.createEntityUpdateAlert("project", project.getId().toString()))
          .body(result);
    }

    /**
     * GET  /projects : get all the projects.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of projects in body
     */
    @GetMapping("/projects")
    @Timed
    public List<Project> getAllProjects() {
        log.debug("REST request to get all Projects");
        List<Project> projects = projectRepository.findAllWithEagerRelationships();
        return projects;
    }

    /**
     * GET  /projects/:id : get the "id" project.
     *
     * @param id the id of the project to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the project, or with status 404 (Not Found)
     */
    @GetMapping("/projects/{id}")
    @Timed
    public ResponseEntity<Project> getProject(@PathVariable Long id) {
        log.debug("REST request to get Project : {}", id);
        Project project = projectRepository.findOneWithEagerRelationships(id);
        return Optional.ofNullable(project)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /projects/:id : delete the "id" project.
     *
     * @param id the id of the project to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/projects/{id}")
    @Timed
    public ResponseEntity<Void> deleteProject(@PathVariable Long id) {
        log.debug("REST request to delete Project : {}", id);
        projectRepository.delete(id);
        projectSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("project", id.toString())).build();
    }

    /**
     * SEARCH  /_search/projects?query=:query : search for the project corresponding
     * to the query.
     *
     * @param query the query of the project search
     * @return the result of the search
     */
    @GetMapping("/_search/projects")
    @Timed
    public List<Project> searchProjects(@RequestParam String query) {
        log.debug("REST request to search Projects for query {}", query);
        return StreamSupport
            .stream(projectSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

    private boolean isMember(User user) {
        Authority authority = new Authority();
        authority.setName(AuthoritiesConstants.MEMBER);
        return user.getAuthorities().contains(authority);
    }

    private void validateExternalResource(CreateExternalResourceRequest request) {
        Validate.notBlank(request.getName(), "External resource name cannot be blank");
        Validate.notBlank(request.getUrl(), "External resource URL cannot be blank");
    }

    private void saveResource(CreateExternalResourceRequest request, Long projectId) {
        ExternalResource resource = new ExternalResource();
        resource.setName(request.getName());
        resource.setUrl(resource.getUrl());
        resource.setProject(projectRepository.findOne(projectId));

        externalResourceRepository.save(resource);
    }
}
