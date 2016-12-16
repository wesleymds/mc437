package br.com.conpec.sade.web.rest.vm;

import br.com.conpec.sade.domain.Feedback;

import java.util.Collection;

public class FeedbackVM {

    private float commitment = 0;

    private float communication = 0;

    private float punctuality = 0;

    private float quality = 0;

    private float technicalKnowledge = 0;

    public FeedbackVM(final Collection<Feedback> feedbacks) {

        feedbacks.forEach(feedback -> {
            commitment += feedback.getCommitment();
            communication += feedback.getCommunication();
            punctuality += feedback.getPunctuality();
            quality += feedback.getQuality();
            technicalKnowledge += feedback.getTechnicalKnowledge();
        });

        int count = feedbacks.size();
        commitment /= count;
        communication /= count;
        punctuality /= count;
        quality /= count;
        technicalKnowledge /= count;
    }

    public float getCommitment() {
        return commitment;
    }

    public void setCommitment(float commitment) {
        this.commitment = commitment;
    }

    public float getCommunication() {
        return communication;
    }

    public void setCommunication(float communication) {
        this.communication = communication;
    }

    public float getPunctuality() {
        return punctuality;
    }

    public void setPunctuality(float punctuality) {
        this.punctuality = punctuality;
    }

    public float getQuality() {
        return quality;
    }

    public void setQuality(float quality) {
        this.quality = quality;
    }

    public float getTechnicalKnowledge() {
        return technicalKnowledge;
    }

    public void setTechnicalKnowledge(float technicalKnowledge) {
        this.technicalKnowledge = technicalKnowledge;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FeedbackVM that = (FeedbackVM) o;

        if (Float.compare(that.commitment, commitment) != 0) return false;
        if (Float.compare(that.communication, communication) != 0) return false;
        if (Float.compare(that.punctuality, punctuality) != 0) return false;
        if (Float.compare(that.quality, quality) != 0) return false;
        if (Float.compare(that.technicalKnowledge, technicalKnowledge) != 0) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (commitment != +0.0f ? Float.floatToIntBits(commitment) : 0);
        result = 31 * result + (communication != +0.0f ? Float.floatToIntBits(communication) : 0);
        result = 31 * result + (punctuality != +0.0f ? Float.floatToIntBits(punctuality) : 0);
        result = 31 * result + (quality != +0.0f ? Float.floatToIntBits(quality) : 0);
        result = 31 * result + (technicalKnowledge != +0.0f ? Float.floatToIntBits(technicalKnowledge) : 0);
        return result;
    }

    @Override
    public String toString() {
        return "FeedbackVM{" +
            "commitment=" + commitment +
            ", communication=" + communication +
            ", punctuality=" + punctuality +
            ", quality=" + quality +
            ", technicalKnowledge=" + technicalKnowledge +
            '}';
    }
}
