package com.team404.minimarket;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;

@Service 
public class TestService {
    @Autowired
    private DataSource dataSource;

    @PostConstruct // 의존성 주입이 끝나면 자동으로 실행됨!
    public void init() {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement("SELECT name FROM test_member LIMIT 1");
             ResultSet rs = pstmt.executeQuery()) {
            
            if (rs.next()) {
                System.out.println("========================");
                System.out.println("DB 결과: " + rs.getString("name"));
                System.out.println("========================");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

