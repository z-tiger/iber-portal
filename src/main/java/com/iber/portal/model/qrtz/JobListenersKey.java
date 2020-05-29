package com.iber.portal.model.qrtz;

public class JobListenersKey {
    private String jobName;

    private String jobGroup;

    private String jobListener;

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName == null ? null : jobName.trim();
    }

    public String getJobGroup() {
        return jobGroup;
    }

    public void setJobGroup(String jobGroup) {
        this.jobGroup = jobGroup == null ? null : jobGroup.trim();
    }

    public String getJobListener() {
        return jobListener;
    }

    public void setJobListener(String jobListener) {
        this.jobListener = jobListener == null ? null : jobListener.trim();
    }
}