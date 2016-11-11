package br.com.conpec.sade.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Feedback.
 */
@Entity
@Table(name = "feedback")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "feedback")
public class Feedback implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "commitment")
    private Integer commitment;

    @Column(name = "communication")
    private Integer communication;

    @Column(name = "punctuality")
    private Integer punctuality;

    @Column(name = "quality")
    private Integer quality;

    @Column(name = "technical_knowledge")
    private Integer technicalKnowledge;

    @Column(name = "bonus")
    private Integer bonus;

    @Column(name = "extra")
    private String extra;

    @ManyToOne
    private Project project;

    @ManyToOne
    private UserData author;

    @ManyToOne
    private UserData developer;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCommitment() {
        return commitment;
    }

    public Feedback commitment(Integer commitment) {
        this.commitment = commitment;
        return this;
    }

    public void setCommitment(Integer commitment) {
        this.commitment = commitment;
    }

    public Integer getCommunication() {
        return communication;
    }

    public Feedback communication(Integer communication) {
        this.communication = communication;
        return this;
    }

    public void setCommunication(Integer communication) {
        this.communication = communication;
    }

    public Integer getPunctuality() {
        return punctuality;
    }

    public Feedback punctuality(Integer punctuality) {
        this.punctuality = punctuality;
        return this;
    }

    public void setPunctuality(Integer punctuality) {
        this.punctuality = punctuality;
    }

    public Integer getQuality() {
        return quality;
    }

    public Feedback quality(Integer quality) {
        this.quality = quality;
        return this;
    }

    public void setQuality(Integer quality) {
        this.quality = quality;
    }

    public Integer getTechnicalKnowledge() {
        return technicalKnowledge;
    }

    public Feedback technicalKnowledge(Integer technicalKnowledge) {
        this.technicalKnowledge = technicalKnowledge;
        return this;
    }

    public void setTechnicalKnowledge(Integer technicalKnowledge) {
        this.technicalKnowledge = technicalKnowledge;
    }

    public Integer getBonus() {
        return bonus;
    }

    public Feedback bonus(Integer bonus) {
        this.bonus = bonus;
        return this;
    }

    public void setBonus(Integer bonus) {
        this.bonus = bonus;
    }

    public String getExtra() {
        return extra;
    }

    public Feedback extra(String extra) {
        this.extra = extra;
        return this;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    public Project getProject() {
        return project;
    }

    public Feedback project(Project project) {
        this.project = project;
        return this;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public UserData getAuthor() {
        return author;
    }

    public Feedback author(UserData userData) {
        this.author = userData;
        return this;
    }

    public void setAuthor(UserData userData) {
        this.author = userData;
    }

    public UserData getDeveloper() {
        return developer;
    }

    public Feedback developer(UserData userData) {
        this.developer = userData;
        return this;
    }

    public void setDeveloper(UserData userData) {
        this.developer = userData;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Feedback feedback = (Feedback) o;
        if(feedback.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, feedback.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Feedback{" +
            "id=" + id +
            ", commitment='" + commitment + "'" +
            ", communication='" + communication + "'" +
            ", punctuality='" + punctuality + "'" +
            ", quality='" + quality + "'" +
            ", technicalKnowledge='" + technicalKnowledge + "'" +
            ", bonus='" + bonus + "'" +
            ", extra='" + extra + "'" +
            '}';
    }
}
