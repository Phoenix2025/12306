package com.example.test2;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

import com.example.test2.Controller.TicketBuyController;

@SpringBootTest
class Test2ApplicationTests {

    @Autowired
    DataSource dataSource;

    @Test
    void contextLoads() {
        TicketBuyController test=new TicketBuyController();
        System.out.println(test.findSeat("1133","2020-10-22","HardSleeper","3","9",3));
    }

}
