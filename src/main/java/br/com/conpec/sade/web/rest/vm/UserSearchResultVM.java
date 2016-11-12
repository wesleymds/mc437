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

    public UserSearchResultVM() {
    }

    public UserSearchResultVM(final UserData userData) {
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
            '}';
    }
}
