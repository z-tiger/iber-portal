package com.iber.portal.model.qrtz;

public class Locks {
    private String lockName;

    public String getLockName() {
        return lockName;
    }

    public void setLockName(String lockName) {
        this.lockName = lockName == null ? null : lockName.trim();
    }
}