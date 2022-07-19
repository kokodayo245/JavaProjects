package org.example.entity.Package;

import org.example.entity.MobileCard;

public class NetPackage extends ServicePackage implements NetService {

    public NetPackage() {
        super(68,0,0,3);
    }



    @Override
    public void showInfo() {
        System.out.println("网虫套餐:");
        super.showInfo();

    }


    @Override
    public int netPlay(int flow, MobileCard card) {
        return 0;
    }
}
