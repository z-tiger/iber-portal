package com.iber.portal.model.qrtz;

public class JobDetails extends JobDetailsKey {
    private String description;

    private String jobClassName;

    private String isDurable;

    private String isVolatile;

    private String isStateful;

    private String requestsRecovery;

    private byte[] jobData;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public String getJobClassName() {
        return jobClassName;
    }

    public void setJobClassName(String jobClassName) {
        this.jobClassName = jobClassName == null ? null : jobClassName.trim();
    }

    public String getIsDurable() {
        return isDurable;
    }

    public void setIsDurable(String isDurable) {
        this.isDurable = isDurable == null ? null : isDurable.trim();
    }

    public String getIsVolatile() {
        return isVolatile;
    }

    public void setIsVolatile(String isVolatile) {
        this.isVolatile = isVolatile == null ? null : isVolatile.trim();
    }

    public String getIsStateful() {
        return isStateful;
    }

    public void setIsStateful(String isStateful) {
        this.isStateful = isStateful == null ? null : isStateful.trim();
    }

    public String getRequestsRecovery() {
        return requestsRecovery;
    }

    public void setRequestsRecovery(String requestsRecovery) {
        this.requestsRecovery = requestsRecovery == null ? null : requestsRecovery.trim();
    }

    public byte[] getJobData() {
        return jobData;
    }

    public void setJobData(byte[] jobData) {
        this.jobData = jobData;
    }
}