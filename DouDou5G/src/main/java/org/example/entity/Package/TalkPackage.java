package org.example.entity.Package;

import org.example.entity.MobileCard;
import org.example.entity.Package.ServicePackage;

public class TalkPackage extends ServicePackage implements CallService,SendService{


    public TalkPackage() {
        super(58,500,30,0);
    }



    @Override
    public void showInfo() {
        System.out.println("话痨套餐:");
        super.showInfo();

    }

    @Override
    public int call(int minute, MobileCard card) {
        return 0;
    }

    @Override
    public int send(int count, MobileCard card) {
        return 0;
    }
}
