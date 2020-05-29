package com.iber.portal.model.qrtz;

public class TriggerListenersKey {
    private String triggerName;

    private String triggerGroup;

    private String triggerListener;

    public String getTriggerName() {
        return triggerName;
    }

    public void setTriggerName(String triggerName) {
        this.triggerName = triggerName == null ? null : triggerName.trim();
    }

    public String getTriggerGroup() {
        return triggerGroup;
    }

    public void setTriggerGroup(String triggerGroup) {
        this.triggerGroup = triggerGroup == null ? null : triggerGroup.trim();
    }

    public String getTriggerListener() {
        return triggerListener;
    }

    public void setTriggerListener(String triggerListener) {
        this.triggerListener = triggerListener == null ? null : triggerListener.trim();
    }
}