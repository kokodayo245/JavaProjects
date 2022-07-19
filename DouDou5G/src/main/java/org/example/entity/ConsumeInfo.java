package org.example.entity;

import java.io.Serializable;

public class ConsumeInfo implements Serializable {
    private int count;
    private String type;
    private String consumeDate;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getConsumeDate() {
        return consumeDate;
    }

    public void setConsumeDate(String consumeDate) {
        this.consumeDate = consumeDate;
    }

    public ConsumeInfo(int count, String type, String consumeDate) {
        this.count = count;
        this.type = type;
        this.consumeDate = consumeDate;
    }

    public ConsumeInfo() {
    }

    @Override
    public String toString() {
        return "ConsumeInfo{" +
                "count=" + count +
                ", type='" + type + '\'' +
                ", consumeDate='" + consumeDate + '\'' +
                '}';
    }
}
