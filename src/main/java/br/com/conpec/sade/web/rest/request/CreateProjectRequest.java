package br.com.conpec.sade.web.rest.request;

import javax.validation.constraints.NotNull;
import java.util.Set;

/**
 * @author Danilo Valente <danilovalente96@gmail.com>
 */
public class CreateProjectRequest {

    @NotNull
    private String name;

    @NotNull
    private Set<CreateExternalResourceRequest> resources;

    @NotNull
    private Set<Long> developersIds;

    @NotNull
    private Set<Long> assessorsIds;

    @NotNull
    private Long managerId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<CreateExternalResourceRequest> getResources() {
        return resources;
    }

    public void setResources(Set<CreateExternalResourceRequest> resources) {
        this.resources = resources;
    }

    public Set<Long> getDevelopersIds() {
        return developersIds;
    }

    public void setDevelopersIds(Set<Long> developersIds) {
        this.developersIds = developersIds;
    }

    public Set<Long> getAssessorsIds() {
        return assessorsIds;
    }

    public void setAssessorsIds(Set<Long> assessorsIds) {
        this.assessorsIds = assessorsIds;
    }

    public Long getManagerId() {
        return managerId;
    }

    public void setManagerId(Long managerId) {
        this.managerId = managerId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CreateProjectRequest that = (CreateProjectRequest) o;

        if (assessorsIds != null ? !assessorsIds.equals(that.assessorsIds) : that.assessorsIds != null) return false;
        if (developersIds != null ? !developersIds.equals(that.developersIds) : that.developersIds != null)
            return false;
        if (managerId != null ? !managerId.equals(that.managerId) : that.managerId != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (resources != null ? !resources.equals(that.resources) : that.resources != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (resources != null ? resources.hashCode() : 0);
        result = 31 * result + (developersIds != null ? developersIds.hashCode() : 0);
        result = 31 * result + (assessorsIds != null ? assessorsIds.hashCode() : 0);
        result = 31 * result + (managerId != null ? managerId.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "CreateProjectRequest{" +
            "name='" + name + '\'' +
            ", resources=" + resources +
            ", developersIds=" + developersIds +
            ", assessorsIds=" + assessorsIds +
            ", managerId=" + managerId +
            '}';
    }
}
