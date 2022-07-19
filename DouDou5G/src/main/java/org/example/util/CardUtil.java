package org.example.util;

import org.example.entity.ConsumeInfo;
import org.example.entity.MobileCard;
import org.example.entity.Package.*;
import org.example.entity.Scene;

import java.io.*;

import java.util.*;

public class CardUtil implements Serializable {
    public static final String CARDS_ROUTE = "d:/JavaProjects/DouDou5G/Data/cards.txt";
    public static final String CONSUMEINFO_ROUTE = "d:/JavaProjects/DouDou5G/Data/consumeInfo.txt";
    public static final String CONSUMEINFO = "d:/JavaProjects/DouDou5G/Data/userConsumeInfo/";
    public static final String PACKAGE_ROUTE = "d:/JavaProjects/DouDou5G/Data/资费说明.txt";

    public Map<String, MobileCard> cards;
    public Map<String, List<ConsumeInfo>> consumeInfo; //消费记录
    public Map<Integer, ServicePackage> packages;
    public List<Scene> scenes;

    public CardUtil() {

    }

    public CardUtil(Map<Integer, ServicePackage> packages) {
        this.packages = packages;
    }

    public Map<String, MobileCard> getCards() {
        return cards;
    }

    public void setCards(Map<String, MobileCard> cards) {
        this.cards = cards;
    }

    public Map<String, List<ConsumeInfo>> getConsumeInfo() {
        return consumeInfo;
    }

    public void setConsumeInfo(Map<String, List<ConsumeInfo>> consumeInfo) {
        this.consumeInfo = consumeInfo;
    }

    public Map<Integer, ServicePackage> getPackages() {
        return packages;
    }

    public void setPackages(Map<Integer, ServicePackage> packages) {
        this.packages = packages;
    }

    //初始化使用场景
    public List<Scene> initScene() {
        scenes = new ArrayList<>();
        Scene s1 = new Scene("talkTime", 90, "问候客户，通话90分钟");
        Scene s2 = new Scene("SMSCount", 20, "通知朋友更换手机号，发送短信20条");
        Scene s3 = new Scene("flow", 1, "看视频，消费流量1GB");
        scenes.add(s1);
        scenes.add(s2);
        scenes.add(s3);
        return scenes;
    }

    //验证用户是否存在
    public boolean isExistCard(String number, String psw) {
        System.out.println(cards.get(number));
        if (cards.get(number) == null) {
            return false;
        }
        if (cards.get(number).getPsw().equals(psw)) {
            return true;
        }
        return false;
    }

    //验证手机号是否存在
    public boolean isExistCard(String number) {
        if (cards.get(number) == null) {
            return false;
        }
        return true;
    }

    //随机创建一个手机号
    public String createNumber() {
        int number = 0;
        String newNumber;
        while (true) {
            int max = 99999999;
            int min = 10000000;
            number = (int) (Math.random() * (max - min + 1)) + min;
            newNumber = "139" + number;
            if (!isExistCard(newNumber)) {
                break;
            }
        }
        return newNumber;
    }
    //获取一组新手机号
    public String[] getNewNumbers() {
        String number;
        String[] numbers = new String[9];
        int index = 0;
        while (index != 9) {
            number = createNumber();
            for (int j = 0; j < numbers.length; j++) {
                if (Objects.equals(number, numbers[j])) break;
                else {
                    numbers[index] = number;
                    index++;
                    break;
                }
            }
        }
        return numbers;
    }
    //新增用户
    public MobileCard addCard(String number, String name, String psw, ServicePackage sp, double balance) {
        balance -= sp.getPrice();
        MobileCard user = new MobileCard(number, name, psw, sp, balance);
        user.setConsumeAmount(sp.getPrice());
        cards.put(number, user);
        consumeInfo.put(number, new ArrayList<ConsumeInfo>());
        serialize(cards, CARDS_ROUTE);
        serialize(consumeInfo, CONSUMEINFO_ROUTE);
        return user;
    }
    //删除用户
    public void delCard(String number) {
        cards.remove(number);
        serialize(cards, CARDS_ROUTE);
        consumeInfo.remove(number);
        serialize(consumeInfo, CONSUMEINFO_ROUTE);
    }
    //套餐余量详情查询
    public void showRemainDetail(String number) {
        MobileCard user = cards.get(number);
        ServicePackage sp = user.getServicePackage();
        System.out.println("套餐余量为：");

//        if (sp instanceof TalkPackage) {
//            System.out.println("tp");
//            TalkPackage tp = (TalkPackage) sp;
//            if (tp.getTalkTime() - user.getRealTalkTime() > 0) {
//                System.out.print("通话时长:" + (tp.getTalkTime() - user.getRealTalkTime()) + "分钟");
//            } else System.out.println("通话时长:0分钟");
//            if (tp.getSMSCount() - user.getRealSMSCount() > 0) {
//                System.out.println("短信条数:" + (tp.getSMSCount() - user.getRealSMSCount() + "条"));
//            } else System.out.println("短信条数:0条");
//        }
//        if (sp instanceof NetPackage) {
//            System.out.println("np");
//            NetPackage np = (NetPackage) sp;
//            if (np.getFlow() - user.getRealFlow() > 0) {
//                System.out.println("流量:" + (np.getFlow() - user.getRealFlow()) + "GB");
//            } else System.out.println("流量:0GB");
//        }
//        if (sp instanceof SuperPackage) {
//            SuperPackage sup = (SuperPackage) sp;
            System.out.println(sp.getTalkTime() - user.getRealTalkTime());
            if (sp.getTalkTime() - user.getRealTalkTime() > 0) {
                System.out.print("通话时长:" + (sp.getTalkTime() - user.getRealTalkTime()) + "分钟");
            } else System.out.println("通话时长:0分钟");
            if (sp.getSMSCount() - user.getRealSMSCount() > 0) {
                System.out.println("短信条数:" + (sp.getSMSCount() - user.getRealSMSCount() + "条"));
            } else System.out.println("短信条数:0条");
            if (sp.getFlow() - user.getRealFlow() > 0) {
                System.out.println("流量:" + (sp.getFlow() - user.getRealFlow()) + "GB");
            } else System.out.println("流量:0GB");
//        }
    }
    //本月账单查询
    public void showAmountDetail(String number) {
        MobileCard user = cards.get(number);
        ServicePackage sp = user.getServicePackage();
        double addConsume = 0, addTalk = 0, addSMS = 0, addFlow = 0;
        System.out.println("套餐消费：" + sp.getPrice());

        if (user.getRealTalkTime() > sp.getTalkTime()) {
            addTalk = 0.2 * (user.getRealTalkTime() - sp.getTalkTime());
            System.out.println("额外通话时长消费：" + addTalk);
        }
        if (user.getRealSMSCount() > sp.getSMSCount()) {
            addSMS = 0.1 * (user.getRealSMSCount() - sp.getSMSCount());
            System.out.println("额外短信消费：" + addSMS);
        }
        if (user.getRealFlow() > sp.getFlow()) {
            addFlow = 0.1 * 1024 * (user.getRealFlow() - sp.getFlow());
            System.out.println("额外流量消费" + addFlow);
        }
        addConsume = addTalk + addSMS + addFlow;
        user.setConsumeAmount(addConsume + sp.getPrice());
        System.out.println("合计消费："+user.getConsumeAmount());
    }
    //新增消费记录信息
    public void addConsumeInfo(String number, String type, int data) {
        System.out.println("111" + consumeInfo.get(number).add(new ConsumeInfo(data, type, new Date().toString())));
        MobileCard user = cards.get(number);
        if (type.equals("talkTime")) {
            user.setRealTalkTime(user.getRealTalkTime() + data);
        }
        if (type.equals("SMSCount")) {
            user.setRealSMSCount(user.getRealSMSCount() + data);
        }
        if (type.equals("flow")) {
            user.setRealFlow(user.getRealFlow() + data);
        }
        serialize(cards, CARDS_ROUTE);
        serialize(consumeInfo, CONSUMEINFO_ROUTE);
        for (int i = 0; i < consumeInfo.get(number).size(); i++) {
            System.out.println(consumeInfo.get(number).get(i));
        }
    }
    //使用兜兜5G
    public void useDouDou(String number) {

    }
    //打印消费详单
    public void showDescription(String number) {
        showConsumeInfo(number);
        String path = CONSUMEINFO + number +".txt";
        System.out.println(path);
        File file = new File(path);
        if (file.exists()){
            file.delete();
        }else {
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        List<ConsumeInfo> list = consumeInfo.get(number);
        for (ConsumeInfo info : list) {
            String str = "消费类型："+info.getType() +"数量："+info.getCount() + "消费时间："+info.getConsumeDate() +"\n";
            charSerialize(str,path);
        }
    }

    //更改套餐
    public void changingPackage(String number, int sPackage) {
        double balance = cards.get(number).getBalance();
        int price = cards.get(number).getServicePackage().getPrice();
        if (balance > price ){
            cards.get(number).setServicePackage(packages.get(sPackage));
            cards.get(number).setBalance(balance - price);
            cards.get(number).setConsumeAmount(cards.get(number).getConsumeAmount() + price);
            serialize(cards,CARDS_ROUTE);

            for (int i = 0;i <consumeInfo.get(number).size();i++){
                consumeInfo.get(number).remove(i);
            }

            serialize(consumeInfo,CONSUMEINFO_ROUTE);
        }else {
            System.out.println("您的余额不足请充值！");
        }

    }
    //查看消费记录
    public void showConsumeInfo(String number) {
        for (int i = 0; i < consumeInfo.get(number).size(); i++) {
            System.out.println(consumeInfo.get(number).get(i));
        }
    }
    //资费说明
    public void showPackage(){
        try(FileReader fr = new FileReader(PACKAGE_ROUTE)){
            int ch = 0;
            while ((ch = fr.read())!=-1){
                System.out.print((char) ch);
            }
            System.out.println();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    //余额充值
    public void chargeBalance(String number, double money) {
        MobileCard user = cards.get(number);
        if (user != null) {
            user.setBalance(user.getBalance() + money);
            System.out.println("余额为:" + user.getBalance());
            serialize(cards, CARDS_ROUTE);
        } else {
            System.out.println("用户不存在，请重新输入！");
        }
    }

    //序列化
    public void serialize(Map map, String route) {
        try (ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(route))) {
            os.writeObject(map);
            os.flush();
        } catch (EOFException e) {

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
//    public void printConsumeInfo(String number,List<ConsumeInfo> list,String route){
//        String str = number +".txt";
//        File file = new File(str);
//        try {
//            file.createNewFile();
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }
   public void charSerialize(String str, String route) {
        try (Writer writer = new BufferedWriter(new FileWriter(route,true))){
            char [] chars = str.toCharArray();
            writer.write(new String(chars,0,chars.length));
        }catch (Exception e){

        }
   }
    //反序列化
    public Object deserialization(String route) {
        try (ObjectInputStream is = new ObjectInputStream(new FileInputStream(route))) {
            Object o = is.readObject();
            is.close();
            return o;

        } catch (EOFException e) {

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    public void print() {
        for (String card : cards.keySet()) {
            System.out.println(card + cards.get(card));
        }
        for (String num : consumeInfo.keySet()) {
            System.out.println(num + consumeInfo.get(num));
        }
    }

}
