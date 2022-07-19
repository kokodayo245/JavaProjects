package org.example.entity.Package;

import org.example.entity.MobileCard;

public class SuperPackage extends ServicePackage implements CallService,NetService,SendService{



    public SuperPackage() {
        super(78,200,50,1);
    }

    @Override
    public void showInfo() {
        System.out.println("超人套餐:");
        super.showInfo();

    }

    @Override
    public int call(int minute, MobileCard card) {
        return 0;
    }

    @Override
    public int netPlay(int flow, MobileCard card) {
        return 0;
    }

    @Override
    public int send(int count, MobileCard card) {
        return 0;
    }
}
