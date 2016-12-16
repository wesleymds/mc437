package br.com.conpec.sade.web.rest.request;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @author Danilo Valente <danilovalente96@gmail.com>
 */
public class CreateFeedbackRequest {

    private static final int MIN_GRADE = 0;

    private static final int MAX_GRADE = 5;

    @NotNull
    @Min(MIN_GRADE)
    @Max(MAX_GRADE)
    private Integer commitment;

    @NotNull
    @Min(MIN_GRADE)
    @Max(MAX_GRADE)
    private Integer communication;

    @NotNull
    @Min(MIN_GRADE)
    @Max(MAX_GRADE)
    private Integer punctuality;

    @NotNull
    @Min(MIN_GRADE)
    @Max(MAX_GRADE)
    private Integer quality;

    @NotNull
    @Min(MIN_GRADE)
    @Max(MAX_GRADE)
    private Integer technicalKnowledge;

    private String extra;

    @NotNull
    private Long projectId;

    @NotNull
    private Long developerId;

    public static int getMinGrade() {
        return MIN_GRADE;
    }

    public static int getMaxGrade() {
        return MAX_GRADE;
    }

    public Integer getCommitment() {
        return commitment;
    }

    public void setCommitment(Integer commitment) {
        this.commitment = commitment;
    }

    public Integer getCommunication() {
        return communication;
    }

    public void setCommunication(Integer communication) {
        this.communication = communication;
    }

    public Integer getPunctuality() {
        return punctuality;
    }

    public void setPunctuality(Integer punctuality) {
        this.punctuality = punctuality;
    }

    public Integer getQuality() {
        return quality;
    }

    public void setQuality(Integer quality) {
        this.quality = quality;
    }

    public Integer getTechnicalKnowledge() {
        return technicalKnowledge;
    }

    public void setTechnicalKnowledge(Integer technicalKnowledge) {
        this.technicalKnowledge = technicalKnowledge;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public Long getDeveloperId() {
        return developerId;
    }

    public void setDeveloperId(Long developerId) {
        this.developerId = developerId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CreateFeedbackRequest that = (CreateFeedbackRequest) o;

        if (commitment != null ? !commitment.equals(that.commitment) : that.commitment != null) return false;
        if (communication != null ? !communication.equals(that.communication) : that.communication != null)
            return false;
        if (extra != null ? !extra.equals(that.extra) : that.extra != null) return false;
        if (projectId != null ? !projectId.equals(that.projectId) : that.projectId != null) return false;
        if (punctuality != null ? !punctuality.equals(that.punctuality) : that.punctuality != null) return false;
        if (quality != null ? !quality.equals(that.quality) : that.quality != null) return false;
        if (technicalKnowledge != null ? !technicalKnowledge.equals(that.technicalKnowledge) : that.technicalKnowledge != null)
            return false;
        if (developerId != null ? !developerId.equals(that.developerId) : that.developerId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = commitment != null ? commitment.hashCode() : 0;
        result = 31 * result + (communication != null ? communication.hashCode() : 0);
        result = 31 * result + (punctuality != null ? punctuality.hashCode() : 0);
        result = 31 * result + (quality != null ? quality.hashCode() : 0);
        result = 31 * result + (technicalKnowledge != null ? technicalKnowledge.hashCode() : 0);
        result = 31 * result + (extra != null ? extra.hashCode() : 0);
        result = 31 * result + (projectId != null ? projectId.hashCode() : 0);
        result = 31 * result + (developerId != null ? developerId.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "CreateFeedbackRequest{" +
            "commitment=" + commitment +
            ", communication=" + communication +
            ", punctuality=" + punctuality +
            ", quality=" + quality +
            ", technicalKnowledge=" + technicalKnowledge +
            ", extra='" + extra + '\'' +
            ", projectId=" + projectId +
            ", developerId=" + developerId +
            '}';
    }
}
