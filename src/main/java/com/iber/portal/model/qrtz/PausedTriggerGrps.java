package com.iber.portal.model.qrtz;

public class PausedTriggerGrps {
    private String triggerGroup;

    public String getTriggerGroup() {
        return triggerGroup;
    }

    public void setTriggerGroup(String triggerGroup) {
        this.triggerGroup = triggerGroup == null ? null : triggerGroup.trim();
    }
}