package br.com.conpec.sade.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Interview.
 */
@Entity
@Table(name = "interview")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "interview")
public class Interview implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "date")
    private LocalDate date;

    @OneToOne
    @JoinColumn(unique = true)
    private ExternalResource report;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "interview_skills",
               joinColumns = @JoinColumn(name="interviews_id", referencedColumnName="ID"),
               inverseJoinColumns = @JoinColumn(name="skills_id", referencedColumnName="ID"))
    private Set<Skill> skills = new HashSet<>();

    @ManyToOne
    private UserData interviewer;

    @ManyToOne
    private UserData interviewed;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public Interview date(LocalDate date) {
        this.date = date;
        return this;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public ExternalResource getReport() {
        return report;
    }

    public Interview report(ExternalResource externalResource) {
        this.report = externalResource;
        return this;
    }

    public void setReport(ExternalResource externalResource) {
        this.report = externalResource;
    }

    public Set<Skill> getSkills() {
        return skills;
    }

    public Interview skills(Set<Skill> skills) {
        this.skills = skills;
        return this;
    }

    public Interview addSkills(Skill skill) {
        skills.add(skill);
        return this;
    }

    public Interview removeSkills(Skill skill) {
        skills.remove(skill);
        return this;
    }

    public void setSkills(Set<Skill> skills) {
        this.skills = skills;
    }

    public UserData getInterviewer() {
        return interviewer;
    }

    public Interview interviewer(UserData userData) {
        this.interviewer = userData;
        return this;
    }

    public void setInterviewer(UserData userData) {
        this.interviewer = userData;
    }

    public UserData getInterviewed() {
        return interviewed;
    }

    public Interview interviewed(UserData userData) {
        this.interviewed = userData;
        return this;
    }

    public void setInterviewed(UserData userData) {
        this.interviewed = userData;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Interview interview = (Interview) o;
        if(interview.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, interview.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Interview{" +
            "id=" + id +
            ", date='" + date + "'" +
            '}';
    }
}
