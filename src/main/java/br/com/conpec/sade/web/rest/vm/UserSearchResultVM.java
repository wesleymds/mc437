package br.com.conpec.sade.web.rest.vm;

import br.com.conpec.sade.domain.Skill;
import br.com.conpec.sade.domain.UserData;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Danilo Marcolino Valente (danilo.valente@movile.com)
 */
public class UserSearchResultVM {

    private Long userId;

    private Long userDataId;

    private String name;

    private String email;

    private Boolean available;

    private Integer availableHoursPerWeek;

    private Integer initialCostPerHour;

    private Set<String> skills = Collections.emptySet();

    private Float commitment;

    private Float communication;

    private Float punctuality;

    private Float quality;

    private Float technicalKnowledge;

    public UserSearchResultVM() {
    }

    public UserSearchResultVM(final UserData userData, final FeedbackVM feedbackVM) {
        this.userId = userData.getUser().getId();
        this.userDataId = userData.getId();
        this.name = userData.getUser().getFirstName() + " " + userData.getUser().getLastName();
        this.email = userData.getUser().getEmail();
        this.available = userData.isAvailable();
        this.availableHoursPerWeek = userData.getAvailableHoursPerWeek();
        this.initialCostPerHour = userData.getInitialCostPerHour();
        this.skills = userData.getSkills()
            .stream()
            .map(Skill::getName)
            .collect(Collectors.toSet());
        this.commitment = feedbackVM.getCommitment();
        this.communication = feedbackVM.getCommunication();
        this.punctuality = feedbackVM.getPunctuality();
        this.quality = feedbackVM.getQuality();
        this.technicalKnowledge = feedbackVM.getTechnicalKnowledge();
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getUserDataId() {
        return userDataId;
    }

    public void setUserDataId(Long userDataId) {
        this.userDataId = userDataId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    public Integer getAvailableHoursPerWeek() {
        return availableHoursPerWeek;
    }

    public void setAvailableHoursPerWeek(Integer availableHoursPerWeek) {
        this.availableHoursPerWeek = availableHoursPerWeek;
    }

    public Integer getInitialCostPerHour() {
        return initialCostPerHour;
    }

    public void setInitialCostPerHour(Integer initialCostPerHour) {
        this.initialCostPerHour = initialCostPerHour;
    }

    public Set<String> getSkills() {
        return skills;
    }

    public void setSkills(Set<String> skills) {
        this.skills = skills;
    }

    public Float getCommitment() {
        return commitment;
    }

    public void setCommitment(Float commitment) {
        this.commitment = commitment;
    }

    public Float getCommunication() {
        return communication;
    }

    public void setCommunication(Float communication) {
        this.communication = communication;
    }

    public Float getPunctuality() {
        return punctuality;
    }

    public void setPunctuality(Float punctuality) {
        this.punctuality = punctuality;
    }

    public Float getQuality() {
        return quality;
    }

    public void setQuality(Float quality) {
        this.quality = quality;
    }

    public Float getTechnicalKnowledge() {
        return technicalKnowledge;
    }

    public void setTechnicalKnowledge(Float technicalKnowledge) {
        this.technicalKnowledge = technicalKnowledge;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserSearchResultVM that = (UserSearchResultVM) o;

        if (available != null ? !available.equals(that.available) : that.available != null) return false;
        if (availableHoursPerWeek != null ? !availableHoursPerWeek.equals(that.availableHoursPerWeek) : that.availableHoursPerWeek != null)
            return false;
        if (commitment != null ? !commitment.equals(that.commitment) : that.commitment != null) return false;
        if (communication != null ? !communication.equals(that.communication) : that.communication != null)
            return false;
        if (email != null ? !email.equals(that.email) : that.email != null) return false;
        if (initialCostPerHour != null ? !initialCostPerHour.equals(that.initialCostPerHour) : that.initialCostPerHour != null)
            return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (punctuality != null ? !punctuality.equals(that.punctuality) : that.punctuality != null) return false;
        if (quality != null ? !quality.equals(that.quality) : that.quality != null) return false;
        if (skills != null ? !skills.equals(that.skills) : that.skills != null) return false;
        if (technicalKnowledge != null ? !technicalKnowledge.equals(that.technicalKnowledge) : that.technicalKnowledge != null)
            return false;
        if (userDataId != null ? !userDataId.equals(that.userDataId) : that.userDataId != null) return false;
        if (userId != null ? !userId.equals(that.userId) : that.userId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = userId != null ? userId.hashCode() : 0;
        result = 31 * result + (userDataId != null ? userDataId.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (available != null ? available.hashCode() : 0);
        result = 31 * result + (availableHoursPerWeek != null ? availableHoursPerWeek.hashCode() : 0);
        result = 31 * result + (initialCostPerHour != null ? initialCostPerHour.hashCode() : 0);
        result = 31 * result + (skills != null ? skills.hashCode() : 0);
        result = 31 * result + (commitment != null ? commitment.hashCode() : 0);
        result = 31 * result + (communication != null ? communication.hashCode() : 0);
        result = 31 * result + (punctuality != null ? punctuality.hashCode() : 0);
        result = 31 * result + (quality != null ? quality.hashCode() : 0);
        result = 31 * result + (technicalKnowledge != null ? technicalKnowledge.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "UserSearchResultVM{" +
            "userId=" + userId +
            ", userDataId=" + userDataId +
            ", name='" + name + '\'' +
            ", email='" + email + '\'' +
            ", available=" + available +
            ", availableHoursPerWeek=" + availableHoursPerWeek +
            ", initialCostPerHour=" + initialCostPerHour +
            ", skills=" + skills +
            ", commitment=" + commitment +
            ", communication=" + communication +
            ", punctuality=" + punctuality +
            ", quality=" + quality +
            ", technicalKnowledge=" + technicalKnowledge +
            '}';
    }
}
