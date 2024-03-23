package com.example.test2.Controller;

import javafx.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class JDBCController {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @GetMapping("/CityStation")
    public List<Map<String,Object>> CityDistrict(){
        List<Map<String,Object>> ans = new ArrayList< Map<String,Object> >();
        String sql="select DISTINCT District from station where Province != City AND District NOT LIKE '%[]%' AND District not LIKE '%区'";
        for(Map<String,Object> map : jdbcTemplate.queryForList(sql)){
            Map<String,Object> tmp=new HashMap<String,Object>();
            tmp.put("label","城市");
            tmp.put("level","District");
            for (String s : map.keySet()) {
                tmp.put("value",map.get(s));
            }
            ans.add(tmp);
        }
        sql="SELECT DISTINCT City FROM station WHERE City=Province OR District LIKE '%区%'";
        for(Map<String,Object> map : jdbcTemplate.queryForList(sql)){
            Map<String,Object> tmp=new HashMap<String,Object>();
            tmp.put("label","城市");
            tmp.put("level","City");
            for (String s : map.keySet()) {
                tmp.put("value",map.get(s));
            }
            ans.add(tmp);
        }
        sql="SELECT DISTINCT NAME FROM station";
        for(Map<String,Object> map : jdbcTemplate.queryForList(sql)){
            Map<String,Object> tmp=new HashMap<String,Object>();
            tmp.put("label","火车站");
            tmp.put("level","Name");
            for (String s : map.keySet()) {
                tmp.put("value",map.get(s));
            }
            ans.add(tmp);
        }
        return ans;

    }

    @GetMapping("/historyOrder/{user}/{date}/{time}")
    public List<Map<String,Object>> historyOrder(@PathVariable("user") String user,@PathVariable("date") String date,@PathVariable("time") String time){
        String sql="";
        sql="WITH temp AS((SELECT id1 AS id FROM transferticket WHERE Account='"+user+"')UNION(SELECT id2 AS id FROM transferticket WHERE Account='"+user+"'))" +
                "SELECT * FROM ticket WHERE Account='"+user+"'and Date<='"+date+"' AND id not in (SELECT * from temp)";
        List<Map<String,Object>> ans=jdbcTemplate.queryForList(sql);
        List<Map<String,Object>> tp= new ArrayList< Map<String,Object> >();
        int t=gettime(time);
        for(Map<String,Object> map : ans){
            String stime = ""+map.get("StartTime");
            if (!(""+map.get("Date")).equals(date)) tp.add(map);
            else if(t>gettime(stime)){
                tp.add(map);
            }
        }
        return tp;
    }

    @GetMapping("/historyTransferOrder/{user}/{date}/{time}")
    public List<Map<String,Object>> historyTransferOrder(@PathVariable("user") String user,@PathVariable("date") String date,@PathVariable("time") String time){
        String sql="SELECT * FROM transferticket WHERE Account='"+user+"'and Date<='"+date+"'";
        List<Map<String,Object>> ans=jdbcTemplate.queryForList(sql);
        List<Map<String,Object>> tp= new ArrayList< Map<String,Object> >();
        int t=gettime(time);
        for(Map<String,Object> map : ans){
            String stime = ""+map.get("StartTime");
            if ((!(""+map.get("Date")).equals(date))||(t>gettime(stime))){
                String id1=map.get("id1")+"",id2=map.get("id2")+"";
                String sq="SELECT * FROM ticket where id='"+id1+"'";
                tp.add(jdbcTemplate.queryForMap(sq));
                sq="SELECT * FROM ticket where id='"+id2+"'";
                tp.add(jdbcTemplate.queryForMap(sq));
            }
        }
        return tp;
    }

    @GetMapping("/travelOrder/{user}/{date}/{time}")
    public List<Map<String,Object>> travelOrder(@PathVariable("user") String user,@PathVariable("date") String date,@PathVariable("time") String time){
        String sql="";
        sql="WITH temp AS((SELECT id1 AS id FROM transferticket WHERE Account='"+user+"')UNION(SELECT id2 AS id FROM transferticket WHERE Account='"+user+"'))" +
                "SELECT * FROM ticket WHERE Account='"+user+"'and Date>='"+date+"' AND id not in (SELECT * from temp)";
        List<Map<String,Object>> ans=jdbcTemplate.queryForList(sql);
        List<Map<String,Object>> tp= new ArrayList< Map<String,Object> >();
        int t=gettime(time);
        for(Map<String,Object> map : ans){
            String stime = ""+map.get("StartTime");
            if (!(""+map.get("Date")).equals(date)) tp.add(map);
            else if(t<gettime(stime)){
                tp.add(map);
            }
        }
        return tp;
    }

    @GetMapping("/travelTransferOrder/{user}/{date}/{time}")
    public List<Map<String,Object>> travelTransferOrder(@PathVariable("user") String user,@PathVariable("date") String date,@PathVariable("time") String time){
        String sql="SELECT * FROM transferticket WHERE Account='"+user+"'and Date>='"+date+"'";
        List<Map<String,Object>> ans=jdbcTemplate.queryForList(sql);
        List<Map<String,Object>> tp= new ArrayList< Map<String,Object> >();
        int t=gettime(time);
        for(Map<String,Object> map : ans){
            String stime = ""+map.get("StartTime");
            if ((!(""+map.get("Date")).equals(date))||(t<gettime(stime))){
                String id1=map.get("id1")+"",id2=map.get("id2")+"";
                String sq="SELECT * FROM ticket where id='"+id1+"'";
                tp.add(jdbcTemplate.queryForMap(sq));
                sq="SELECT * FROM ticket where id='"+id2+"'";
                tp.add(jdbcTemplate.queryForMap(sq));
            }
        }
        return tp;
    }

    public int gettime(String time){
        String[] t=time.split(":");
        int h=Integer.parseInt(t[0]),m=Integer.parseInt(t[1]);
        return (h*60+m);
    }

    @GetMapping("/deleteOrders/{orders}")
    public String deleteOrders(@PathVariable("orders") String orders){
        String ans="";
        String[] order=orders.split("\\|");
        if(order.length==1){
            String sql="INSERT INTO oldticket (SELECT *,2 FROM ticket WHERE Id='"+order[0]+"')";
            jdbcTemplate.update(sql);

            sql="DELETE FROM ticket WHERE Id=";
            jdbcTemplate.update(sql+"'"+order[0]+"'");
        }else {
            String sql="DELETE FROM transferticket WHERE (id1='"+order[0]+"' AND id2='"+order[1]+"') OR (id1='"+order[1]+"' AND id2='"+order[0]+"')";
            jdbcTemplate.update(sql);

            sql="INSERT INTO oldticket (SELECT *,2 FROM ticket WHERE Id='"+order[0]+"')";
            jdbcTemplate.update(sql);

            sql="DELETE FROM ticket WHERE Id=";
            jdbcTemplate.update(sql+"'"+order[0]+"'");

        }
        ans="订单删除成功";
        return ans;
    }

    @GetMapping("/quit/{Account}/{option}")
    public List<Map<String,Object>> quit(@PathVariable("Account") String Account,@PathVariable("option") String option) {
        String sql="SELECT * FROM oldticket WHERE Account='"+Account+"' AND status='"+option+"'";
        List<Map<String,Object>> ans=jdbcTemplate.queryForList(sql);
        return ans;
    }























    @GetMapping("/stationList")
    public List<Map<String,Object>> stationList(){
        String sql="select * from station";
        List<Map<String,Object>> list=jdbcTemplate.queryForList(sql);
        return list;
    }

    @GetMapping("/test/{account}/{password}")
    public String hello(@PathVariable("account") String account,@PathVariable("password") String password) {
        return account+password;
    }

    @GetMapping("/addUser/{account}/{password}")
    public String addUser(@PathVariable("account") String account,@PathVariable("password") String password){
        String sql="insert into user values('"+account+"','"+password+"')";
        jdbcTemplate.update(sql);
        return "yes,it is";
    }


}


