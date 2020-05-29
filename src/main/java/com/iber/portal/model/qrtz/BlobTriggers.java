package com.iber.portal.model.qrtz;

public class BlobTriggers extends BlobTriggersKey {
    private byte[] blobData;

    public byte[] getBlobData() {
        return blobData;
    }

    public void setBlobData(byte[] blobData) {
        this.blobData = blobData;
    }
}