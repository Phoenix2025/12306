package com.example.test2.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.MergedAnnotationPredicates;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.DrbgParameters;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.ObjIntConsumer;

@RestController
public class TicketBuyController {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @GetMapping("/directBuy")
    public String DirectBuy(@RequestParam("userlist") String userlist, @RequestParam("TrainNo") String TrainNo,
                             @RequestParam("Start") String Start, @RequestParam("End") String End,
                             @RequestParam("Date") String Date, @RequestParam("Account") String Account,
                             @RequestParam("StartTime") String StartTime, @RequestParam("EndTime") String EndTime,
                             @RequestParam("TicketPrice") String TicketPrice, @RequestParam("SeatType") String SeatType,
                             @RequestParam("StartStationNo") String StartStationNo, @RequestParam("EndStationNo") String EndStationNo){
//        System.out.println(userlist);System.out.println(TrainNo);System.out.println(Start);System.out.println(End);
//        System.out.println(Date);System.out.println(Account);System.out.println(StartTime);System.out.println(EndTime);System.out.println(TicketPrice);
//        System.out.println(SeatType);System.out.println(StartStationNo);System.out.println(EndStationNo);
        String  ans="";
        String[] users=userlist.split("\\|");
        Map<String,Object> Seats=findSeat(TrainNo,Date,SeatType,StartStationNo,EndStationNo,users.length);
        if(Seats==null) return "当前所选车次座位类型票余量不足";
        for(int i=0;i<users.length;i++){
            String sql="SELECT * from ticket WHERE Account='"+users[i]+"' AND Date='"+Date+"'";
            for(Map<String,Object> map:jdbcTemplate.queryForList(sql)){
                if(!tfbuy(map,TrainNo,Date,StartStationNo,EndStationNo)){
                    ans="购票失败|";
                    ans=ans+users[i]+" 您日期为："+map.get("Date")+" 车次为："+map.get("TrainNo")+" 从 "+map.get("Start")+" 到 "+map.get("End")+" 的出行计划与当前车票冲突";
                    return ans;
                }
            }
        }
        double sum=0;
        for(int i=1;i<=users.length;i++){
            Map<String,Object> s=(Map<String,Object>)Seats.get("Seat"+i);
            String SeatNo=s.get("No")+"",Carriage=s.get("Carriage")+"";
            String price=getPrice(SeatType,TicketPrice,SeatNo);
            sum+=Double.parseDouble(price);
            addticket(users[i-1],TrainNo,Start,End,Date,Account,StartTime,EndTime,price,SeatType,StartStationNo,EndStationNo,Carriage,SeatNo);
            ans=ans+getInfo(users[i-1],TrainNo,SeatType,Carriage,SeatNo,Date)+"|";
        }
        ans="购票成功 总价：￥"+sum+"|"+ans;
        return ans;
    }

    public boolean tfbuy(Map<String,Object> map,String TrainNo,String date,String StartStationNo,String EndStationNo){
        String sql="WITH temp AS (SELECT * FROM trainstationtime WHERE TrainNo='"+TrainNo+"' AND (StationNO="+StartStationNo+" OR StationNo="+EndStationNo+") )," +
                "start1 AS(SELECT TIME_TO_SEC(Departure) AS start1 FROM trainstation WHERE TrainNo='"+TrainNo+"' AND StationNo="+StartStationNo+")," +
                "end1 AS(SELECT t1.test-t2.test+start1 AS end1 from temp as t1,temp as t2,start1 WHERE t1.StationNo="+EndStationNo+" AND t2.StationNo="+StartStationNo+")," +
                "temp2 AS (SELECT * FROM trainstationtime WHERE TrainNo='"+map.get("TrainNo")+"' AND (StationNO="+map.get("StartStationNo")+" OR StationNo="+map.get("EndStationNo")+") )," +
                "start2 AS(SELECT TIME_TO_SEC(Departure) AS start2 FROM trainstation WHERE TrainNo='"+map.get("TrainNo")+"' AND StationNo="+map.get("StartStationNo")+")," +
                "end2 AS(SELECT t1.test-t2.test+start2 AS end2 from temp2 as t1,temp2 as t2,start2 WHERE t1.StationNo="+map.get("EndStationNo")+" AND t2.StationNo="+map.get("StartStationNo")+")" +
                "SELECT start1,end1,start2,end2 FROM start1,end1,start2,end2 WHERE end1>=start2 and start1<=end2";

//        System.out.println(sql);
        List<Map<String,Object>> list=jdbcTemplate.queryForList(sql);
        if(list==null||list.size()==0) return true;//没有冲突
        else  return false;//有冲突
    }


    @GetMapping("/transferBuy")
    public String TransferBuy(@RequestParam("userlist") String userlist, @RequestParam("TrainNo1") String TrainNo1,
                            @RequestParam("Start1") String Start1, @RequestParam("End1") String End1,
                            @RequestParam("Date1") String Date1,@RequestParam("Account") String Account,
                            @RequestParam("StartTime1") String StartTime1, @RequestParam("EndTime1") String EndTime1,
                            @RequestParam("TicketPrice1") String TicketPrice1, @RequestParam("SeatType1") String SeatType1,
                            @RequestParam("StartStationNo1") String StartStationNo1, @RequestParam("EndStationNo1") String EndStationNo1,
                            @RequestParam("TrainNo2") String TrainNo2,
                            @RequestParam("Start2") String Start2, @RequestParam("End2") String End2,
                            @RequestParam("Date2") String Date2,
                            @RequestParam("StartTime2") String StartTime2, @RequestParam("EndTime2") String EndTime2,
                            @RequestParam("TicketPrice2") String TicketPrice2, @RequestParam("SeatType2") String SeatType2,
                            @RequestParam("StartStationNo2") String StartStationNo2, @RequestParam("EndStationNo2") String EndStationNo2) {

//        System.out.println(userlist);
//        System.out.println(TrainNo1);System.out.println(Start1);System.out.println(End1);
//        System.out.println(Date1);System.out.println(Account);System.out.println(StartTime1);System.out.println(EndTime1);System.out.println(TicketPrice1);
//        System.out.println(SeatType1);System.out.println(StartStationNo1);System.out.println(EndStationNo1);
//
//        System.out.println(userlist);System.out.println(TrainNo2);System.out.println(Start2);System.out.println(End2);
//        System.out.println(Date2);System.out.println(Account);System.out.println(StartTime2);System.out.println(EndTime2);System.out.println(TicketPrice2);
//        System.out.println(SeatType2);System.out.println(StartStationNo2);System.out.println(EndStationNo2);

        String ans="";

        String[] users=userlist.split("\\|");
        Map<String,Object> Seats1=findSeat(TrainNo1,Date1,SeatType1,StartStationNo1,EndStationNo1,users.length);
        if(Seats1==null) return "当前所选车次座位类型票余量不足";
        Map<String,Object> Seats2=findSeat(TrainNo2,Date2,SeatType2,StartStationNo2,EndStationNo2,users.length);
        if(Seats2==null) return "当前所选车次座位类型票余量不足";

        for(int i=0;i<users.length;i++){
            String sql="SELECT * from ticket WHERE Account='"+users[i]+"' AND Date='"+Date1+"'";
            for(Map<String,Object> map:jdbcTemplate.queryForList(sql)){
                if(!tfbuy(map,TrainNo1,Date1,StartStationNo1,EndStationNo1)){
                    ans="购票失败|";
                    ans=ans+users[i]+" 您日期为："+map.get("Date")+" 车次为："+map.get("TrainNo")+" 从 "+map.get("Start")+" 到 "+map.get("End")+" 的出行计划与当前车票冲突";
                    return ans;
                }
            }
        }

        double sum=0;
        for(int i=1;i<=users.length;i++){
            Map<String,Object> s1=(Map<String,Object>)Seats1.get("Seat"+i);
            String SeatNo1=s1.get("No")+"",Carriage1=s1.get("Carriage")+"";
            String price1=getPrice(SeatType1,TicketPrice1,SeatNo1);
            sum+=Double.parseDouble(price1);
            String id1=addticket(users[i-1],TrainNo1,Start1,End1,Date1,Account,StartTime1,EndTime1,price1,SeatType1,StartStationNo1,EndStationNo1,Carriage1,SeatNo1);
            ans=ans+getInfo(users[i-1],TrainNo1,SeatType1,Carriage1,SeatNo1,Date1)+"|";

            Map<String,Object> s2=(Map<String,Object>)Seats2.get("Seat"+i);
            String SeatNo2=s2.get("No")+"",Carriage2=s2.get("Carriage")+"";
            String price2=getPrice(SeatType2,TicketPrice2,SeatNo2);
            sum+=Double.parseDouble(price2);
            String id2=addticket(users[i-1],TrainNo2,Start2,End2,Date2,Account,StartTime2,EndTime2,price2,SeatType2,StartStationNo2,EndStationNo2,Carriage2,SeatNo2);
            ans=ans+getInfo(users[i-1],TrainNo2,SeatType2,Carriage2,SeatNo2,Date2)+"|";

            String sql="INSERT INTO transferticket VALUES('"+id1+"','"+id2+"','"+users[i-1]+"','"+Date1+"','"+StartTime1+"')";
            jdbcTemplate.update(sql);

        }
        ans="购票成功 总价：￥"+sum+"|"+ans;
        return ans;
    }


    public String getInfo(String user,String TrainNo,String SeatType,String Carriage,String SeatNo,String date){
        String ans="乘车人："+user+" "+date+" "+TrainNo+" ";
        if(SeatType.equals("HardSleeper")){
            if((Integer.parseInt(SeatNo)%3)==1) ans=ans+"硬卧上";
            else if((Integer.parseInt(SeatNo)%3)==2) ans=ans+"硬卧中";
            else ans=ans+"硬卧下";
            ans=ans+" 车厢："+Carriage+"号"+" 座位："+SeatNo+"号\n";
        }
        else if(SeatType.equals("SoftSleeper")){
            if((Integer.parseInt(SeatNo)%2)==1) ans=ans+"软卧上";
            else ans=ans+"软卧下";
            ans=ans+" 车厢："+Carriage+"号"+" 座位："+SeatNo+"号\n";
        }
        else if(SeatType.equals("NoSeat")){
            ans=ans+"无座 车厢："+Carriage+"号\n";
        }
        else if(SeatType.equals("Second")) ans=ans+"二等座"+" 车厢："+Carriage+"号"+" 座位："+SeatNo+"号\n";
        else if(SeatType.equals("First")) ans=ans+"一等座"+" 车厢："+Carriage+"号"+" 座位："+SeatNo+"号\n";
        else if(SeatType.equals("Business")) ans=ans+"商务座"+" 车厢："+Carriage+"号"+" 座位："+SeatNo+"号\n";
        else if(SeatType.equals("HardSeat")) ans=ans+"硬座"+" 车厢："+Carriage+"号"+" 座位："+SeatNo+"号\n";
        else if(SeatType.equals("SoftSeat")) ans=ans+"软座"+" 车厢："+Carriage+"号"+" 座位："+SeatNo+"号\n";
        else {
            ans=ans+SeatType+" 车厢："+Carriage+"号"+" 座位："+SeatNo+"号\n";
        }
        return ans;
    }

    public String getPrice(String SeatType,String TicketPrice,String SeatNo){
        String ans="";
        if(SeatType.equals("SoftSleeper")){
            String[] prices=TicketPrice.split("\\|");
            if((Integer.parseInt(SeatNo)%2)==1) ans=prices[0];
            else ans=prices[1];
        }else if(SeatType.equals("HardSleeper")){
            String[] prices=TicketPrice.split("\\|");
            if((Integer.parseInt(SeatNo)%3)==1) ans=prices[0];
            else if((Integer.parseInt(SeatNo)%3)==2) ans=prices[1];
            else ans=prices[2];
        }else ans=TicketPrice;
        return ans;
    }

    public Map<String,Object> findSeat(String TrainNo,String date,String SeatType,String StartStationNo,String EndStationNo,int requireSeat){
        int CarriageNo=0,SeatNo=0;
        int cnt=0;
        Map<String,Object> ans=new HashMap<String,Object>();
        if (ans==null) System.out.println("hhh");
        if(!SeatType.equals("NoSeat")){
            String sql="WITH tp1 AS(SELECT min(CarriageNo) AS Min,MAX(CarriageNo) AS Max FROM carriage NATURAL JOIN traincarriage as t WHERE TrainNo='"+TrainNo+"' AND t.Model LIKE '"+SeatType+"%')," +
                    "tp2 as (SELECT CarriageNo,SeatNo from ticket,tp1 WHERE TrainNo='"+TrainNo+"' AND Date='"+date+"' AND (("+EndStationNo+"-1)>=StartStationNo AND "+StartStationNo+"<=(EndStationNo-1)) AND CarriageNo>=tp1.Min and CarriageNo<=tp1.Max and SeatType LIKE '"+SeatType+"%' )," +
                    "tp3 as(SELECT CarriageNo,Capacity FROM (carriage NATURAL JOIN traincarriage as t),tp1 WHERE t.TrainNo='"+TrainNo+"' AND t.CarriageNo>=tp1.Min and t.CarriageNo<=tp1.Max order by t.CarriageNo asc )," +
                    "tp as(SELECT CarriageNo,id AS SeatNo FROM tp3,sec WHERE id<=Capacity ORDER BY CarriageNo ASC,id ASC)" +
                    "SELECT * FROM tp WHERE (CarriageNo,SeatNo) not in (SELECT CarriageNo,SeatNo FROM tp2)";
//            System.out.println(sql);
            for(Map<String,Object> map: jdbcTemplate.queryForList(sql)){
                if(cnt==requireSeat) return ans;
                cnt++;
                Map<String,Object> h= new HashMap<String ,Object>();
                h.put("No",map.get("SeatNo")+"");
                h.put("Carriage",map.get("CarriageNo"));
                ans.put("Seat"+cnt,h);
            }
        }
        else {
            String sql="WITH tp1 AS(SELECT * FROM carriage WHERE NoSeatNum>0)," +
                    "tp2 AS (SELECT t.CarriageNo,tp1.NoSeatNum AS Capacity FROM traincarriage AS t,tp1 WHERE TrainNo='"+TrainNo+"' AND tp1.Model=t.Model)," +
                    "tp3 AS(SELECT CarriageNo,SeatNo from ticket WHERE TrainNo='"+TrainNo+"' AND Date='"+date+"' AND (("+EndStationNo+"-1)>=StartStationNo AND "+StartStationNo+"<=(EndStationNo-1)) AND SeatType='NoSeat')," +
                    "tp as(SELECT CarriageNo,id AS SeatNo FROM tp2,sec WHERE id<=Capacity ORDER BY CarriageNo ASC,id ASC)" +
                    "SELECT * FROM tp WHERE (CarriageNo,SeatNo) not in (SELECT CarriageNo,SeatNo FROM tp3)";
//            System.out.println(sql);
            for(Map<String,Object> map: jdbcTemplate.queryForList(sql)){
                if(cnt==requireSeat) return ans;
                cnt++;
                Map<String,Object> h= new HashMap<String ,Object>();
                h.put("No",map.get("SeatNo"));
                h.put("Carriage",map.get("CarriageNo"));
                ans.put("Seat"+cnt,h);
            }
        }
        return null;
    }
//http://localhost:8181/findSeat/1133/2020-10-22/NoSeat/3/9/2
    @GetMapping("/findSeat/{TrainNo}/{date}/{SeatType}/{StartStationNo}/{EndStationNo}/{requireSeat}")
    public Map<String, Object> DirectBuy(@PathVariable("TrainNo") String TrainNo, @PathVariable("date") String date, @PathVariable("SeatType") String SeatType , @PathVariable("StartStationNo") String StartStationNO, @PathVariable("EndStationNo") String EndStationNO, @PathVariable("requireSeat") int requireSeat){
        return findSeat(TrainNo,date,SeatType,StartStationNO,EndStationNO,requireSeat);

//        for(int i=1;i<=200;i++){
//            jdbcTemplate.update("insert into test values ("+i+")");
//        }

//        return null;
    }

    public String addticket(String user,String TrainNo,String Start,String End,String Date,String Account,String StartTime,String EndTime,String TicketPrice,String SeatType,String StartStationNo,String EndStationNo,String CarriageNo,String SeatNo){
        boolean tf=true;
        String id=""+System.currentTimeMillis();
        user=user.replace("\"","");
//        System.out.println(SeatType);
        SeatType=getSeatType(SeatType,SeatNo);
        String sql="insert into ticket values ('"+id+"','"+TrainNo+"',"+CarriageNo+",'"+SeatNo+"','"+Start+"','"+End+"','"+Date+"','"+user+"','"+StartTime+"','"+EndTime+"',"+TicketPrice+",'"+SeatType+"',"+StartStationNo+","+EndStationNo+")";
        jdbcTemplate.update(sql);
        return id;
    }

    public String getSeatType(String SeatType,String SeatNo){
        String ans="";
        if(SeatType.equals("SoftSleeper")){
            if((Integer.parseInt(SeatNo)%2)==1) ans="SoftSleeperUp";
            else ans="SoftSleeperDown";
        }else if(SeatType.equals("HardSleeper")){
            if((Integer.parseInt(SeatNo)%3)==1) ans="HardSleeperUp";
            else if((Integer.parseInt(SeatNo)%3)==2) ans="HardSleeperMid";
            else ans="HardSleeperDown";
        }else ans=SeatType;
        return ans;
    }

    @GetMapping("/changeticket")
    public String changeticket(@RequestParam("oldid") String oldid,@RequestParam("TrainNo") String TrainNo,
                            @RequestParam("Start") String Start, @RequestParam("End") String End,
                            @RequestParam("Date") String Date, @RequestParam("Account") String Account,
                            @RequestParam("StartTime") String StartTime, @RequestParam("EndTime") String EndTime,
                            @RequestParam("TicketPrice") String TicketPrice, @RequestParam("SeatType") String SeatType,
                            @RequestParam("StartStationNo") String StartStationNo, @RequestParam("EndStationNo") String EndStationNo){
//        System.out.println(userlist);System.out.println(TrainNo);System.out.println(Start);System.out.println(End);
//        System.out.println(Date);System.out.println(Account);System.out.println(StartTime);System.out.println(EndTime);System.out.println(TicketPrice);
//        System.out.println(SeatType);System.out.println(StartStationNo);System.out.println(EndStationNo);
        String  ans="";
        Map<String,Object> Seats=findSeat(TrainNo,Date,SeatType,StartStationNo,EndStationNo,1);
        if(Seats==null) return "当前所选车次座位类型票余量不足";
//        for(int i=0;i<=users.length;i++){
//        }
        double sum=0;
        for(int i=1;i<=1;i++){
            Map<String,Object> s=(Map<String,Object>)Seats.get("Seat"+i);
            String SeatNo=s.get("No")+"",Carriage=s.get("Carriage")+"";
            String price=getPrice(SeatType,TicketPrice,SeatNo);
            sum-=Double.parseDouble(price);
            addticket(Account,TrainNo,Start,End,Date,Account,StartTime,EndTime,price,SeatType,StartStationNo,EndStationNo,Carriage,SeatNo);
            ans=ans+getInfo(Account,TrainNo,SeatType,Carriage,SeatNo,Date);
        }

        String p="SELECT TicketPrice FROM ticket WHERE Id='"+oldid+"'";
        sum+=Double.parseDouble((jdbcTemplate.queryForMap(p).get("TicketPrice")+""));

        String sql="INSERT INTO oldticket (SELECT *,1 FROM ticket WHERE Id='"+oldid+"')";
        jdbcTemplate.update(sql);

        sql="DELETE FROM ticket WHERE Id=";
        jdbcTemplate.update(sql+"'"+oldid+"'");

        sql="DELETE FROM transferticket WHERE id1='"+oldid+"' OR id2='"+oldid+"'";
        jdbcTemplate.update(sql);

        if(sum<0){
            sum=0-sum;
            ans="改签成功 补差价：￥"+sum+"|"+ans;
        }
        else if(sum>0) ans="改签成功 退款：￥"+sum+"|"+ans;
        else ans="改签成功|"+ans;
        return ans;
    }



}
