package org.example.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.entity.Package.ServicePackage;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MobileCard implements Serializable {
    private String carNumber;//手机号
    private String userName;//用户姓名
    private String psw;//密码
    private ServicePackage servicePackage;//服务套餐
    private double consumeAmount;//消费金额
    private double balance;//账户余额
    private int realTalkTime;//实际通话时间
    private int realSMSCount;//实际短信条数
    private int realFlow;//实际流量

    public MobileCard(String carNumber, String userName, String psw, ServicePackage servicePackage, double balance) {
        this.carNumber = carNumber;
        this.userName = userName;
        this.psw = psw;
        this.servicePackage = servicePackage;
        this.balance = balance;
    }
    public void printInfo(){
        System.out.println("卡号:"+carNumber+"\t用户名:"+userName+"\t当前余额:"+balance);
        servicePackage.showInfo();
        System.out.println();
    }
}
