package com.example.test2.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.net.http.HttpRequest;
import java.util.Map;

@RestController
public class SignController {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @GetMapping("/tfSign/{account}/{password}")
    public boolean tfSign(@PathVariable("account") String account, @PathVariable("password") String password){
        boolean tf=false;
        String sql="SELECT count(*) AS num FROM `user` WHERE Account='"+account+"' AND `Password`='"+password+"'";
        Map<String,Object> map=jdbcTemplate.queryForMap(sql);
        if (Integer.valueOf(map.get("num")+"")>0) tf=true;
        return tf;
    }

    @GetMapping("/tfUserExist/{account}")
    public boolean tfUserExist(@PathVariable("account") String account){
        boolean tf=false;
        String sql="SELECT count(*) AS num FROM `user` WHERE Account='"+account+"'";
        Map<String,Object> map=jdbcTemplate.queryForMap(sql);
        if (Integer.valueOf(map.get("num")+"")>0) tf=true;
        return tf;
    }


//HardSleeperUp
//3
//6
//test
//K7803
//应县
//太原东
//2020-10-27
//"test"
//15:28
//18:23
//50.5
//SoftSleeperDown
//3
//6

}
