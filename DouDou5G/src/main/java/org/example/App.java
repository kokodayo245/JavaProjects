package org.example;

import org.example.biz.Menu;
import org.example.entity.ConsumeInfo;
import org.example.entity.MobileCard;
import org.example.entity.Package.NetPackage;
import org.example.entity.Package.ServicePackage;
import org.example.entity.Package.SuperPackage;
import org.example.entity.Package.TalkPackage;
import org.example.util.CardUtil;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        CardUtil cardUtil = initCardUtil();
        new Menu(cardUtil).MainMenu();
    }
    public static CardUtil initCardUtil(){
        Map<Integer, ServicePackage> packages = new HashMap<>();
        packages.put(1,new TalkPackage());
        packages.put(2,new NetPackage());
        packages.put(3,new SuperPackage());
        CardUtil cardUtil = new CardUtil(packages);
        Map cards = (Map<String, MobileCard>) cardUtil.deserialization(CardUtil.CARDS_ROUTE);
        Map consumeInfo = (Map<String,List<ConsumeInfo>>) cardUtil.deserialization(CardUtil.CONSUMEINFO_ROUTE);
        if( cards == null){
            System.out.println("cards == null");
            cards = new HashMap<String,MobileCard>();
            cardUtil.setCards(cards);
        }else cardUtil.setCards(cards);
        if(consumeInfo == null){
            System.out.println("consumeInfo == null");
            consumeInfo = new HashMap<String,List<ConsumeInfo>>();
            cardUtil.setConsumeInfo(consumeInfo);
        }else cardUtil.setConsumeInfo(consumeInfo);
        return cardUtil;
    }
}
