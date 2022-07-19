package org.example.biz;

import org.example.entity.Package.ServicePackage;
import org.example.entity.Scene;
import org.example.util.CardUtil;
import java.util.List;
import java.util.Scanner;

public class Menu {
    CardUtil cardUtil;
    Scanner scanner = new Scanner(System.in);

    public Menu() {
    }

    public Menu(CardUtil cardUtil) {
        this.cardUtil = cardUtil;
    }

    //主菜单
    public void MainMenu() {
        int i;
        do {
            System.out.println("**************欢迎使用兜兜5G移动业务服务厅**************");
            System.out.println("1.用户登录\t2.用户注册\t3.使用兜兜\t4.话费充值\t5.资费说明\t6.退出系统");
            System.out.println("请选择：");
            i = scanner.nextInt();
            switch (i) {
                case 1: {
                    //验证用户是否存在
                    while (true) {
                        System.out.println("请输入手机卡号(输入0退出):");
                        String number = scanner.next();
                        if (number.equals("0"))break;
                        System.out.println("请输入密码:");
                        String psw = scanner.next();
                        if (cardUtil.isExistCard(number, psw)) {
                            while (true) {
                                System.out.println("1.本月账单查询");
                                System.out.println("2.套餐余量查询");
                                System.out.println("3.打印消费详单");
                                System.out.println("4.套餐变更");
                                System.out.println("5.办理退网");
                                System.out.println("请输入（1~5选择功能，其他键返回上一级）:");
                                int j = scanner.nextInt();
                                if (j > 5 || j <= 0) {
                                    break;
                                }
                                logon(j,number);
                            }
                        } else {
                            System.out.println("输入的手机号或密码错误！请重新输入");
                        }
                    }
                    break;
                }
                case 2: {
                    register();
                    break;
                }
                case 3: {
                    System.out.println("请输入手机号：");
                    String number = scanner.next();
                    List scenes = cardUtil.initScene();
                    int j = (int) (Math.random()*3);
                    if (scenes.get(j) instanceof Scene){
                        Scene s = (Scene) scenes.get(j);
                        System.out.println(s.getDescription());
                        cardUtil.addConsumeInfo(number,s.getType(),s.getData());
                    }
                    break;
                }
                case 4: {
                    System.out.println("请输入你要充值的卡号:");
                    String number = scanner.next();
                    System.out.println("请输入你要充值的金额:");
                    double money = scanner.nextDouble();
                    cardUtil.chargeBalance(number,money);
                    break;
                }
                case 5: {
                    cardUtil.showPackage();
                    cardUtil.print();
                    break;
                }

            }
        } while (i != 6);
        System.out.println("感谢使用！");

    }

    //二级菜单
    public void logon(int i,String number) {

        switch (i) {
            //账单查询
            case 1: {
                cardUtil.showAmountDetail(number);
                break;
            }
            //套餐余量查询
            case 2: {
                cardUtil.showRemainDetail(number);
                break;
            }
            //打印消费详单
            case 3: {

                cardUtil.showDescription(number);
                break;
            }
            //套餐变更
            case 4: {
                System.out.println("1.话痨套餐\t 2.网虫套餐\t 3.超人套餐\t 请选择话费套餐：");
                int j = scanner.nextInt();
                cardUtil.changingPackage(number,j);
                break;
            }
            //办理退网
            case 5: {
                cardUtil.delCard(number);
                System.out.println("卡号:"+number+"办理退网成功！");
                break;
                    }
                }
            }



    public void register() {
        System.out.println("*****可选择的卡号*****");
        String[] numbers = cardUtil.getNewNumbers();
        for (int j = 1; j <= 9; j++) {
            System.out.print(j + "." + numbers[j - 1] + "\t");
            if (j % 3 == 0) {
                System.out.println();
            }
        }
        System.out.println("选择卡号（输入1~9的序号）：");
        int index = scanner.nextInt() - 1;//选择的手机号
        System.out.println("1.话痨套餐\t 2.网虫套餐\t 3.超人套餐\t 请选择话费套餐：");
        int j = scanner.nextInt();
        ServicePackage sp = cardUtil.packages.get(j);
        System.out.println("请输入姓名：");
        String name = scanner.next();
        System.out.println("请输入密码：");
        String psw = scanner.next();
        System.out.println("请输入预存话费余额");
        double balance = scanner.nextDouble();
        if (balance < sp.getPrice()) {
            System.out.println("您预存的话费不足以支付本月固定套餐资费，请重新充值");
            balance = scanner.nextDouble();
        }
        //新增用户
        cardUtil.addCard(numbers[index], name, psw, sp, balance).printInfo();
    }
}
