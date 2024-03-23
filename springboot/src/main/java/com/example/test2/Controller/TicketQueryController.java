package com.example.test2.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalTime;
import java.util.*;

@RestController
public class TicketQueryController {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @GetMapping("/ticketResultDirect/{StartPoint}/{EndPoint}/{date}")
    public List<Map<String,Object>> ticketResultDirect( @PathVariable("StartPoint") String StartPoint, @PathVariable("EndPoint") String EndPoint , @PathVariable("date") String date) throws ParseException {
//        System.out.println(StartPoint);
//        System.out.println(EndPoint);
//        date=date.substring(0,10);
        date=getDate(date);
//        System.out.println(date);
        List<Map<String,Object>> ans = new ArrayList< Map<String,Object> >();

        List<String> StartStations=getStation(StartPoint);
        List<String> EndStations=getStation(EndPoint);

        for(int i=0;i<StartStations.size();i++){
            String StartStation=(String) StartStations.get(i);
            for(int j=0;j<EndStations.size();j++){
                String EndStation=(String) EndStations.get(j);
                List<String> TrainNos=getTrainNo(StartStation,EndStation);
                for(int k=0;k<TrainNos.size();k++){
                    String TrainNo=TrainNos.get(k);
                    Map<String,Object> info = getInfo(StartStation,EndStation,TrainNo,date);
                    info.put("date",date);
                    ans.add(info);
                }
            }
        }

        return ans;
    }

    public List<String> getStation(String para){
        List<String> ans= new ArrayList<String>();
        String[] p=para.split("\\|");
        if(p[2].equals("Name")) ans.add(p[0].substring(0,p[0].length()-1));
        else if(p[2].equals("District")){
            String sql="SELECT Name from station where District='"+p[0]+"'";
            for(Map<String,Object> map : jdbcTemplate.queryForList(sql)){
                String name=(String) map.get("Name");
                ans.add(name.substring(0,name.length()-1));
            }
        }else if(p[2].equals("City")){
            String sql="(SELECT Name from station where Province=City AND City='"+p[0]+"') UNION (SELECT Name from station where City='"+p[0]+"' AND District LIKE '%区')";
            for(Map<String,Object> map : jdbcTemplate.queryForList(sql)){
                String name=(String) map.get("Name");
                ans.add(name.substring(0,name.length()-1));
            }
        }
        return ans;
    }

    @GetMapping("/testgetStation/{para}")
    public List<String> ticketResultDirect(@PathVariable("para") String para){
        return getStation(para);

    }

    public List<String> getTrainNo(String StartPoint,String EndPoint){
        List<String> ans= new ArrayList<String>();
        String sql="SELECT DISTINCT TrainNo FROM trainstation WHERE TrainNo in (select DISTINCT TrainNo FROM trainstation as t1 WHERE t1.`Name`='"+StartPoint+"' AND t1.StationNo<(SELECT StationNo FROM trainstation AS t2 WHERE t2.TrainNo=t1.TrainNo and t2.Name='"+EndPoint+"' ))";
        for(Map<String,Object> map : jdbcTemplate.queryForList(sql)){
            String TrainNo=(String) map.get("TrainNo");
            ans.add(TrainNo);
        }
        return ans;
    }

    @GetMapping("/testgetTrainNo/{StartPoint}/{EndPoint}")
    public List<String> ticketResultDirect(@PathVariable("StartPoint") String StartPoint,@PathVariable("EndPoint") String EndPoint){
        return getTrainNo(StartPoint,EndPoint);

    }

    public Map<String,Object> getInfo(String StartStation,String EndStation,String TrainNo,String date){
        Map<String,Object> ans=new HashMap<String,Object>();
        String sql0="WITH t1 AS ( SELECT * FROM trainstation WHERE TrainNo = '"+TrainNo+"' AND NAME = '"+StartStation+"'),t2 AS ( SELECT * FROM trainstation WHERE TrainNo = '"+TrainNo+"' AND NAME = '"+EndStation+"' )";
        String sql1="SELECT t1.StationNo as StartStationNo,t2.StationNo as EndStationNo,t1.Departure AS Depart,t2.Arrival,t2.Business - t1.Business AS BusinessPrice,t2. FIRST - t1. FIRST AS FirstPrice,t2. SECOND - t1. SECOND AS SecondPrice,t2.SoftSleeperUp - t1.SoftSleeperUp AS SoftSleeperUpPrice,t2.SoftSleeperDown - t1.SoftSleeperDown AS SoftSleeperDownPrice,t2.HardSleeperUp - t1.HardSleeperUp AS HardSleeperUpPrice,t2.HardSleeperMid - t1.HardSleeperMid AS HardSleeperMidPrice,t2.HardSleeperDown - t1.HardSleeperDown AS HardSleeperDownPrice,t2.SoftSeat - t1.SoftSeat AS SoftSeatPrice,t2.HardSeat - t1.HardSeat AS HardSeatPrice,t2.NoSeat - t1.NoSeat AS NoSeatPrice FROM t2,t1";
        ans=jdbcTemplate.queryForMap(sql0+sql1);
        int duration_time=getDuration_(TrainNo,ans.get("StartStationNo")+"",ans.get("EndStationNo")+"");
        String duration=formatterTime(duration_time);
        ans.put("duration",duration);
        ans.put("duration_time",duration_time);
        String sql3="SELECT t3.TrainNo,t3.Name,t3.StationNo,t3.Arrival,t3.Departure FROM trainstation AS t3,t1,t2 WHERE t3.TrainNo='"+TrainNo+"' AND t3.StationNo>=t1.StationNo AND t3.StationNo<=t2.StationNo ORDER BY t3.StationNo ASC";
        ans.put("detail",jdbcTemplate.queryForList(sql0+sql3));
        ans.put("StartStation",StartStation);
        ans.put("EndStation",EndStation);
        ans.put("TrainNo",TrainNo);
        ans.put("date",date);

        Map<String,Integer> tp=getNumInfo(TrainNo,date,ans.get("StartStationNo")+"",ans.get("EndStationNo")+"");
        ans.putAll(tp);

        return ans;
    }

    @GetMapping("/testgetInfo/{StartStation}/{EndStation}/{TrainNo}/{date}")
    public Map<String,Object> testgetInfo(@PathVariable("StartStation") String StartStation,@PathVariable("EndStation") String EndStation,@PathVariable("TrainNo") String TrainNo,@PathVariable("date") String date){
        return getInfo(StartStation,EndStation,TrainNo,date);

    }

//    http://localhost:8181/testgetnumInfo/%E5%BA%94%E5%8E%BF/%E5%A4%AA%E5%8E%9F%E5%8D%97/D2517/13
    public Map<String,Integer> getNumInfo(String TrainNo,String date,String StartStationNo,String EndStationNo){
        Map<String,Integer> ans=new HashMap<String,Integer>();
        ans.put("BusinessNum",0);ans.put("FirstNum",0);ans.put("SecondNum",0);ans.put("HardSeatNum",0);ans.put("SoftSeatNum",0);ans.put("NoSeatNum",0);
        ans.put("HardSleeperUpNum",0);ans.put("HardSleeperMidNum",0);ans.put("HardSleeperDownNum",0);ans.put("SoftSleeperUpNum",0);ans.put("SoftSleeperDownNum",0);
        String sql="SELECT Model,sum(Capacity) as sum,sum(NoSeatNum) AS NoSeatNum FROM carriage NATURAL JOIN traincarriage WHERE TrainNo='"+TrainNo+"' GROUP BY Model";
        for(Map<String,Object> map : jdbcTemplate.queryForList(sql)){
            String Model=(String) map.get("Model");
            Model= Model.substring(0,Model.length()-1);
            int sum=Integer.valueOf(map.get("sum")+"");
            if(Model.equals("SoftSleeper")){
                ans.put("SoftSleeperUpNum",(sum/2)+ans.get("SoftSleeperUpNum"));
                ans.put("SoftSleeperDownNum",(sum/2)+ans.get("SoftSleeperDownNum"));
            }else if(Model.equals("HardSleeper")){
                ans.put("HardSleeperUpNum",(sum/3)+ans.get("HardSleeperUpNum"));
                ans.put("HardSleeperMidNum",(sum/3)+ans.get("HardSleeperMidNum"));
                ans.put("HardSleeperDownNum",(sum/3)+ans.get("HardSleeperDownNum"));
            }else{
                sum+=ans.get(Model+"Num");
                ans.put(Model+"Num",sum);
            }
            sum=Integer.valueOf(map.get("NoSeatNum")+"");
            sum+=ans.get("NoSeatNum");
            ans.put("NoSeatNum",sum);
        }
        Map<String,Integer> tp=getTicketNumInfo(TrainNo,date,StartStationNo,EndStationNo);
        if(tp.keySet()!=null&&ans.keySet()!=null){
            for(String s : tp.keySet()){
                ans.put(s,(ans.get(s)-tp.get(s)));
            }
        }

        return ans;
    }

    @GetMapping("/testgetnumInfo/{TrainNo}/{date}/{StartStationNo}/{EndStationNo}")
    public Map<String,Integer> testgetnumInfo(@PathVariable("TrainNo") String TrainNo,@PathVariable("date") String date,@PathVariable("StartStationNo") String StartStationNo,@PathVariable("EndStationNo") String EndStationNo){
        return getNumInfo(TrainNo,date,StartStationNo,EndStationNo);

    }

    /*
    getticketnumInfo方法
    买：s,e
    已买：t,d
    只要判断[s,e-1]和[t,d-1]是否有重叠
    max(A.start,B.start)<=min(A.end,B,end)，即可判断A，B重叠

*/
    public Map<String,Integer> getTicketNumInfo(String TrainNo,String date,String StartStationNo,String EndStationNo) {
        Map<String,Integer> ans=new HashMap<String,Integer>();
        String sql="SELECT SeatType,COUNT(*) AS num from ticket WHERE TrainNo='"+TrainNo+"' AND Date='"+date+"' AND (("+EndStationNo+"-1)>=StartStationNo AND "+StartStationNo+"<=(EndStationNo-1)) GROUP BY SeatType";
//        System.out.println(sql);
        for(Map<String,Object> map : jdbcTemplate.queryForList(sql)){
            String type=(String) map.get("SeatType");
            int num=Integer.valueOf(map.get("num")+"");
            ans.put(type+"Num",num);
        }
        return ans;
    }



//http://localhost:8181/ticketResultTransfer/%E5%BA%94%E5%8E%BF%7C%E5%9F%8E%E5%B8%82%7CDistrict/%E6%B5%8E%E5%8D%97%E5%B8%82%7C%E5%9F%8E%E5%B8%82%7CCity/2020-10-22T16:00:00.000Z
    @GetMapping("/ticketResultTransfer/{StartPoint}/{EndPoint}/{date}/{pageno}")
    public Map<String,Object> ticketResultTransfer( @PathVariable("StartPoint") String StartPoint, @PathVariable("EndPoint") String EndPoint , @PathVariable("date") String date,@PathVariable("pageno") int pageno) throws ParseException {

//        date=date.substring(0,10);
        date=getDate(date);
        List<Map<String,Object>> ans = new ArrayList< Map<String,Object> >();

        List<String> StartStations=getStation(StartPoint);
        List<String> EndStations=getStation(EndPoint);

//        System.out.println(StartStations.size()+" "+EndStations.size());
        int cnt=0;
        Loop:
        for(int i=0;i<StartStations.size();i++){
            String StartStation=(String) StartStations.get(i);
//            System.out.println(StartStation);
            for(int j=0;j<EndStations.size();j++){
                String EndStation=(String) EndStations.get(j);
//                String sql1="WITH tp1 AS(SELECT * FROM trainstation AS t1 WHERE TrainNo in (SELECT DISTINCT TrainNo FROM trainstation WHERE Name='"+StartStation+"') AND t1.StationNo > (SELECT StationNo FROM  trainstation WHERE NAME='"+StartStation+"' AND TrainNo=t1.TrainNo ) ORDER BY TrainNo ASC,StationNo ASC),tp2 AS (SELECT * FROM trainstation AS t1 WHERE TrainNo in (SELECT DISTINCT TrainNo FROM trainstation WHERE Name='"+EndStation+"') AND t1.StationNo < (SELECT StationNo FROM  trainstation WHERE NAME='"+EndStation+"' AND TrainNo=t1.TrainNo ) ORDER BY TrainNo ASC,StationNo ASC)";
//                String sql2="SELECT tp1.Name AS Name,tp1.TrainNo AS TrainNo1,tp2.TrainNo AS TrainNo2,tp1.Arrival,tp2.Departure FROM tp1 INNER JOIN tp2 USING(NAME)";
//                System.out.println(sql1+sql2);

                for(Map<String ,Object> map:getsolutions(StartStation,EndStation)){
//                for(Map<String,Object> map : jdbcTemplate.queryForList(sql1+sql2)){

//                    WHERE tp1.TrainNo!=tp2.TrainNo
                    cnt++;
                    if(cnt<((pageno-1)*10)||cnt>=(pageno*10)) continue;
                    int duration=getDuration((String) map.get("Arrival"),(String) map.get("Departure"));
                    map.put("duration",formatterTime(duration));
////                    if(duration>(120)) continue;
//                    System.out.println(StartStation+" "+(String) map.get("Name")+" "+EndStation+" "+(String) map.get("TrainNo1")+" "+(String)map.get("TrainNo2")+" "+duration);
                    Map<String,Object> info1=getInfo(StartStation,(String) map.get("Name"),(String) map.get("TrainNo1"),date);

                    String dt=getTransferDate((List<Map<String, Object>>) info1.get("detail"),""+map.get("Departure"),date);

                    Map<String,Object> info2=getInfo((String) map.get("Name"),EndStation,(String) map.get("TrainNo2"),dt);

                    map.put("StartStation",StartStation);map.put("EndStation",EndStation);
                    map.put("duration1",info1.get("duration"));map.put("duration2",info2.get("duration"));
                    int sumduration=duration+Integer.parseInt(""+info1.get("duration_time"))+Integer.parseInt(""+info2.get("duration_time"));
                    map.put("sumduration",formatterTime(sumduration));
                    map.put("index",cnt);
                    map.put("Depart1",(String) info1.get("Depart"));
                    map.put("Arrival1",(String) info1.get("Arrival"));
                    map.put("Depart2",(String) info2.get("Depart"));
                    map.put("Arrival2",(String) info2.get("Arrival"));

                    ans.add(map);
                    ans.add(info1);
                    ans.add(info2);
                }
            }
        }
        Map<String,Object> tp=new HashMap<String,Object>();
        tp.put("ans",ans);
        tp.put("total",cnt);
        return tp;
    }

    public List<Map<String,Object>> getsolutions(String StartPoint,String EndPoint){
        List<Map<String,Object>> ans = new ArrayList< Map<String,Object> >();
        String sql="WITH " +
                "p1 AS (SELECT DISTINCT TrainNo,StationNo FROM trainstation WHERE NAME = '"+StartPoint+"')," +
                "p2 AS (SELECT DISTINCT TrainNo,StationNo FROM trainstation WHERE NAME = '"+EndPoint+"')," +
                "tp1 AS (SELECT Name,TrainNo,Arrival,Departure FROM trainstation AS t1 INNER JOIN p1 USING (TrainNo) WHERE t1.StationNo > p1.StationNo)," +
                "tp2 AS (SELECT Name,TrainNo,Arrival,Departure FROM trainstation AS t1 INNER JOIN p2 USING (TrainNo) WHERE p2.StationNo > t1.StationNo)," +
                "tp3 AS (SELECT tp1. NAME AS Name,tp1.TrainNo AS TrainNo1,tp2.TrainNo AS TrainNo2,tp1.Arrival,tp2.Departure FROM tp1 INNER JOIN tp2 USING (NAME)) " +
                "SELECT * FROM tp3";
//        System.out.println(sql);
        ans=jdbcTemplate.queryForList(sql);
        return ans;
    }

    public int getDuration_(String TrainNo,String StartStationNo,String EndStationNo){
        String sql="WITH tp AS (SELECT test FROM trainstationtime WHERE TrainNo='"+TrainNo+"' AND StationNo="+EndStationNo+")," +
                "tp2 AS (SELECT test FROM trainstationtime WHERE TrainNo='"+TrainNo+"' AND StationNo="+StartStationNo+")" +
                "SELECT tp.test-tp2.test as du FROM tp,tp2";
        Map<String,Object> map= jdbcTemplate.queryForMap(sql);
        int du=Integer.parseInt(map.get("du")+"");
        du=du/60;
        return du;
    }

    public int getDuration(String Arrival,String Departure){
        int ans=0;
        String[] t1=Arrival.split(":");
        String[] t2=Departure.split(":");
        int sum1=Integer.parseInt(t1[0])*60+Integer.parseInt(t1[1]);
        int sum2=Integer.parseInt(t2[0])*60+Integer.parseInt(t2[1]);
        ans=sum2-sum1;
        if(ans<0) ans+=(24*60);
        return ans;
    }
//
    public String formatterTime(int time){
        String ans="";
        ans+=((time/60)+"小时");
        if ((time%60)!=0) ans+=((time%60)+"分");
        return ans;
    }

    public String getTransferDate(List<Map<String,Object>> list,String end,String dt) throws ParseException {
        String ans="";int cnt=0;
        String last="00:00";
        for(Map<String,Object> map:list){
            String Arrival=map.get("Arrival")+"";
            String Departure=map.get("Departure")+"";
            if(!Arrival.equals("始发站")){
                if(!compare(Arrival,last)) cnt++;
                last=Arrival;
            }
            if(!Departure.equals("终点站")){
                if(!compare(Departure,last)) cnt++;
                last=Departure;
            }
        }
        if(!compare(end,last)) cnt++;

        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
        Date date = sdf2.parse(dt);
        Calendar   calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(calendar.DATE,cnt); //把日期往后增加一天,整数  往后推,负数往前移动
        date=calendar.getTime(); //这个时间就是日期往后推一天的结果

        return sdf2.format(date);
    }

    public boolean compare(String time1,String time2){
//        System.out.println(time1+" "+time2);
        boolean tf=false;
        String[] t1=time1.split(":");
        String[] t2=time2.split(":");
        if(Integer.parseInt(t1[0])>Integer.parseInt(t2[0])) return true;
        else if((Integer.parseInt(t1[0])==Integer.parseInt(t2[0]))&&(Integer.parseInt(t1[1])>=Integer.parseInt(t2[1]))) return true;
        return tf;
    }

    public String getDate(String s) throws ParseException {

        String str3 = s.substring(0,10);
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
        Date date = sdf2.parse(str3);

//        System.out.println(sdf2.format(date));

        Calendar   calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(calendar.DATE,1); //把日期往后增加一天,整数  往后推,负数往前移动
        date=calendar.getTime(); //这个时间就是日期往后推一天的结果

//        System.out.println(sdf2.format(date));

        return sdf2.format(date);
    }

    public static void main(String args[]) throws ParseException {


    }

}
