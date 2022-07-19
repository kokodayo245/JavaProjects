package org.example.entity.Package;


import java.io.Serializable;

public class ServicePackage implements Serializable {
    private int price;
    private int talkTime;
    private int SMSCount;
    private int flow;

    public ServicePackage(int price) {
        this.price = price;
    }

    public int getPrice() {
        return price;
    }

    public ServicePackage(int price, int talkTime, int SMSCount, int flow) {
        this.price = price;
        this.talkTime = talkTime;
        this.SMSCount = SMSCount;
        this.flow = flow;
    }

    public ServicePackage() {
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getTalkTime() {
        return talkTime;
    }

    public void setTalkTime(int talkTime) {
        this.talkTime = talkTime;
    }

    public int getSMSCount() {
        return SMSCount;
    }

    public void setSMSCount(int SMSCount) {
        this.SMSCount = SMSCount;
    }

    public int getFlow() {
        return flow;
    }

    public void setFlow(int flow) {
        this.flow = flow;
    }

    public void showInfo() {
        System.out.print("套餐价格为:" + this.price + "/每月\t通话时长：" + getTalkTime() + "分钟/每月，短信条数：" + getSMSCount() + "条/每月，上网流量：" + getFlow() + "GB/每月");

    }

    public void showRemain() {

    }
}
