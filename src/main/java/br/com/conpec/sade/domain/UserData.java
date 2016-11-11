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

/**
 * A UserData.
 */
@Entity
@Table(name = "user_data")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "userdata")
public class UserData implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "primary_phone_number", nullable = false)
    private String primaryPhoneNumber;

    @Column(name = "secondary_phone_number")
    private String secondaryPhoneNumber;

    @NotNull
    @Column(name = "address", nullable = false)
    private String address;

    @NotNull
    @Column(name = "rg", nullable = false)
    private String rg;

    @NotNull
    @Column(name = "cpf", nullable = false)
    private String cpf;

    @Column(name = "extra")
    private String extra;

    @Column(name = "available")
    private Boolean available;

    @Column(name = "available_hours_per_week")
    private Integer availableHoursPerWeek;

    @Column(name = "initial_cost_per_hour")
    private Integer initialCostPerHour;

    @Column(name = "bank_agency")
    private String bankAgency;

    @Column(name = "bank_account")
    private String bankAccount;

    @OneToOne
    @JoinColumn(unique = true)
    private User user;

    @OneToMany(mappedBy = "author")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Feedback> feedbacks = new HashSet<>();

    @OneToMany(mappedBy = "developer")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Feedback> reviews = new HashSet<>();

    @OneToMany(mappedBy = "manager")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Project> managings = new HashSet<>();

    @OneToMany(mappedBy = "interviewer")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Interview> askingInterviews = new HashSet<>();

    @OneToMany(mappedBy = "interviewed")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Interview> answeringInterviews = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "user_data_skills",
               joinColumns = @JoinColumn(name="user_data_id", referencedColumnName="ID"),
               inverseJoinColumns = @JoinColumn(name="skills_id", referencedColumnName="ID"))
    private Set<Skill> skills = new HashSet<>();

    @ManyToMany(mappedBy = "developers")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Project> developings = new HashSet<>();

    @ManyToMany(mappedBy = "assessors")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Project> assessings = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPrimaryPhoneNumber() {
        return primaryPhoneNumber;
    }

    public UserData primaryPhoneNumber(String primaryPhoneNumber) {
        this.primaryPhoneNumber = primaryPhoneNumber;
        return this;
    }

    public void setPrimaryPhoneNumber(String primaryPhoneNumber) {
        this.primaryPhoneNumber = primaryPhoneNumber;
    }

    public String getSecondaryPhoneNumber() {
        return secondaryPhoneNumber;
    }

    public UserData secondaryPhoneNumber(String secondaryPhoneNumber) {
        this.secondaryPhoneNumber = secondaryPhoneNumber;
        return this;
    }

    public void setSecondaryPhoneNumber(String secondaryPhoneNumber) {
        this.secondaryPhoneNumber = secondaryPhoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public UserData address(String address) {
        this.address = address;
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRg() {
        return rg;
    }

    public UserData rg(String rg) {
        this.rg = rg;
        return this;
    }

    public void setRg(String rg) {
        this.rg = rg;
    }

    public String getCpf() {
        return cpf;
    }

    public UserData cpf(String cpf) {
        this.cpf = cpf;
        return this;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getExtra() {
        return extra;
    }

    public UserData extra(String extra) {
        this.extra = extra;
        return this;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    public Boolean isAvailable() {
        return available;
    }

    public UserData available(Boolean available) {
        this.available = available;
        return this;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    public Integer getAvailableHoursPerWeek() {
        return availableHoursPerWeek;
    }

    public UserData availableHoursPerWeek(Integer availableHoursPerWeek) {
        this.availableHoursPerWeek = availableHoursPerWeek;
        return this;
    }

    public void setAvailableHoursPerWeek(Integer availableHoursPerWeek) {
        this.availableHoursPerWeek = availableHoursPerWeek;
    }

    public Integer getInitialCostPerHour() {
        return initialCostPerHour;
    }

    public UserData initialCostPerHour(Integer initialCostPerHour) {
        this.initialCostPerHour = initialCostPerHour;
        return this;
    }

    public void setInitialCostPerHour(Integer initialCostPerHour) {
        this.initialCostPerHour = initialCostPerHour;
    }

    public String getBankAgency() {
        return bankAgency;
    }

    public UserData bankAgency(String bankAgency) {
        this.bankAgency = bankAgency;
        return this;
    }

    public void setBankAgency(String bankAgency) {
        this.bankAgency = bankAgency;
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public UserData bankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
        return this;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    public User getUser() {
        return user;
    }

    public UserData user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<Feedback> getFeedbacks() {
        return feedbacks;
    }

    public UserData feedbacks(Set<Feedback> feedbacks) {
        this.feedbacks = feedbacks;
        return this;
    }

    public UserData addFeedback(Feedback feedback) {
        feedbacks.add(feedback);
        feedback.setAuthor(this);
        return this;
    }

    public UserData removeFeedback(Feedback feedback) {
        feedbacks.remove(feedback);
        feedback.setAuthor(null);
        return this;
    }

    public void setFeedbacks(Set<Feedback> feedbacks) {
        this.feedbacks = feedbacks;
    }

    public Set<Feedback> getReviews() {
        return reviews;
    }

    public UserData reviews(Set<Feedback> feedbacks) {
        this.reviews = feedbacks;
        return this;
    }

    public UserData addReviews(Feedback feedback) {
        reviews.add(feedback);
        feedback.setDeveloper(this);
        return this;
    }

    public UserData removeReviews(Feedback feedback) {
        reviews.remove(feedback);
        feedback.setDeveloper(null);
        return this;
    }

    public void setReviews(Set<Feedback> feedbacks) {
        this.reviews = feedbacks;
    }

    public Set<Project> getManagings() {
        return managings;
    }

    public UserData managings(Set<Project> projects) {
        this.managings = projects;
        return this;
    }

    public UserData addManaging(Project project) {
        managings.add(project);
        project.setManager(this);
        return this;
    }

    public UserData removeManaging(Project project) {
        managings.remove(project);
        project.setManager(null);
        return this;
    }

    public void setManagings(Set<Project> projects) {
        this.managings = projects;
    }

    public Set<Interview> getAskingInterviews() {
        return askingInterviews;
    }

    public UserData askingInterviews(Set<Interview> interviews) {
        this.askingInterviews = interviews;
        return this;
    }

    public UserData addAskingInterviews(Interview interview) {
        askingInterviews.add(interview);
        interview.setInterviewer(this);
        return this;
    }

    public UserData removeAskingInterviews(Interview interview) {
        askingInterviews.remove(interview);
        interview.setInterviewer(null);
        return this;
    }

    public void setAskingInterviews(Set<Interview> interviews) {
        this.askingInterviews = interviews;
    }

    public Set<Interview> getAnsweringInterviews() {
        return answeringInterviews;
    }

    public UserData answeringInterviews(Set<Interview> interviews) {
        this.answeringInterviews = interviews;
        return this;
    }

    public UserData addAnsweringInterviews(Interview interview) {
        answeringInterviews.add(interview);
        interview.setInterviewed(this);
        return this;
    }

    public UserData removeAnsweringInterviews(Interview interview) {
        answeringInterviews.remove(interview);
        interview.setInterviewed(null);
        return this;
    }

    public void setAnsweringInterviews(Set<Interview> interviews) {
        this.answeringInterviews = interviews;
    }

    public Set<Skill> getSkills() {
        return skills;
    }

    public UserData skills(Set<Skill> skills) {
        this.skills = skills;
        return this;
    }

    public UserData addSkills(Skill skill) {
        skills.add(skill);
        return this;
    }

    public UserData removeSkills(Skill skill) {
        skills.remove(skill);
        return this;
    }

    public void setSkills(Set<Skill> skills) {
        this.skills = skills;
    }

    public Set<Project> getDevelopings() {
        return developings;
    }

    public UserData developings(Set<Project> projects) {
        this.developings = projects;
        return this;
    }

    public UserData addDeveloping(Project project) {
        developings.add(project);
        project.getDevelopers().add(this);
        return this;
    }

    public UserData removeDeveloping(Project project) {
        developings.remove(project);
        project.getDevelopers().remove(this);
        return this;
    }

    public void setDevelopings(Set<Project> projects) {
        this.developings = projects;
    }

    public Set<Project> getAssessings() {
        return assessings;
    }

    public UserData assessings(Set<Project> projects) {
        this.assessings = projects;
        return this;
    }

    public UserData addAssessing(Project project) {
        assessings.add(project);
        project.getAssessors().add(this);
        return this;
    }

    public UserData removeAssessing(Project project) {
        assessings.remove(project);
        project.getAssessors().remove(this);
        return this;
    }

    public void setAssessings(Set<Project> projects) {
        this.assessings = projects;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UserData userData = (UserData) o;
        if(userData.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, userData.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "UserData{" +
            "id=" + id +
            ", primaryPhoneNumber='" + primaryPhoneNumber + "'" +
            ", secondaryPhoneNumber='" + secondaryPhoneNumber + "'" +
            ", address='" + address + "'" +
            ", rg='" + rg + "'" +
            ", cpf='" + cpf + "'" +
            ", extra='" + extra + "'" +
            ", available='" + available + "'" +
            ", availableHoursPerWeek='" + availableHoursPerWeek + "'" +
            ", initialCostPerHour='" + initialCostPerHour + "'" +
            ", bankAgency='" + bankAgency + "'" +
            ", bankAccount='" + bankAccount + "'" +
            '}';
    }
}
