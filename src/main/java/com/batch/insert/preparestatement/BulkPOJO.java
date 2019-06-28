package com.batch.insert.preparestatement;

public class BulkPOJO {
    private byte[] data;

    public void setData(byte[] data) {
        this.data = data;
    }

    public byte[] getData() {
        return data;
    }

    public BulkPOJO(byte[] data) {
        this.data = data;
    }
}
