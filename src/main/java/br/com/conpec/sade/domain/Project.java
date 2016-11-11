package br.com.conpec.sade.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import br.com.conpec.sade.domain.enumeration.ProjectStatus;

/**
 * A Project.
 */
@Entity
@Table(name = "project")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "project")
public class Project implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private ProjectStatus status;

    @OneToMany(mappedBy = "project")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ExternalResource> resources = new HashSet<>();

    @OneToMany(mappedBy = "project")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Feedback> reviews = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "project_developers",
               joinColumns = @JoinColumn(name="projects_id", referencedColumnName="ID"),
               inverseJoinColumns = @JoinColumn(name="developers_id", referencedColumnName="ID"))
    private Set<UserData> developers = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "project_assessors",
               joinColumns = @JoinColumn(name="projects_id", referencedColumnName="ID"),
               inverseJoinColumns = @JoinColumn(name="assessors_id", referencedColumnName="ID"))
    private Set<UserData> assessors = new HashSet<>();

    @ManyToOne
    private UserData manager;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Project name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ProjectStatus getStatus() {
        return status;
    }

    public Project status(ProjectStatus status) {
        this.status = status;
        return this;
    }

    public void setStatus(ProjectStatus status) {
        this.status = status;
    }

    public Set<ExternalResource> getResources() {
        return resources;
    }

    public Project resources(Set<ExternalResource> externalResources) {
        this.resources = externalResources;
        return this;
    }

    public Project addResources(ExternalResource externalResource) {
        resources.add(externalResource);
        externalResource.setProject(this);
        return this;
    }

    public Project removeResources(ExternalResource externalResource) {
        resources.remove(externalResource);
        externalResource.setProject(null);
        return this;
    }

    public void setResources(Set<ExternalResource> externalResources) {
        this.resources = externalResources;
    }

    public Set<Feedback> getReviews() {
        return reviews;
    }

    public Project reviews(Set<Feedback> feedbacks) {
        this.reviews = feedbacks;
        return this;
    }

    public Project addReviews(Feedback feedback) {
        reviews.add(feedback);
        feedback.setProject(this);
        return this;
    }

    public Project removeReviews(Feedback feedback) {
        reviews.remove(feedback);
        feedback.setProject(null);
        return this;
    }

    public void setReviews(Set<Feedback> feedbacks) {
        this.reviews = feedbacks;
    }

    public Set<UserData> getDevelopers() {
        return developers;
    }

    public Project developers(Set<UserData> userData) {
        this.developers = userData;
        return this;
    }

    public Project addDevelopers(UserData userData) {
        developers.add(userData);
        userData.getDevelopings().add(this);
        return this;
    }

    public Project removeDevelopers(UserData userData) {
        developers.remove(userData);
        userData.getDevelopings().remove(this);
        return this;
    }

    public void setDevelopers(Set<UserData> userData) {
        this.developers = userData;
    }

    public Set<UserData> getAssessors() {
        return assessors;
    }

    public Project assessors(Set<UserData> userData) {
        this.assessors = userData;
        return this;
    }

    public Project addAssessors(UserData userData) {
        assessors.add(userData);
        userData.getAssessings().add(this);
        return this;
    }

    public Project removeAssessors(UserData userData) {
        assessors.remove(userData);
        userData.getAssessings().remove(this);
        return this;
    }

    public void setAssessors(Set<UserData> userData) {
        this.assessors = userData;
    }

    public UserData getManager() {
        return manager;
    }

    public Project manager(UserData userData) {
        this.manager = userData;
        return this;
    }

    public void setManager(UserData userData) {
        this.manager = userData;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Project project = (Project) o;
        if(project.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, project.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Project{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", status='" + status + "'" +
            '}';
    }
}
