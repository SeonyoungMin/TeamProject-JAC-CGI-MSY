package com.team404.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TestController {

	@Autowired
	private DataSource dataSource; // root-context.xml에 있는 DB 연결 정보 가져오기

	@RequestMapping(value = "/test", produces = "text/plain; charset=UTF-8")
	@ResponseBody
	public String test() {
		// 1. 실행되는지 확인용 콘솔 출력
		System.out.println("--- 컨트롤러 접속 성공 ---");

		try (Connection conn = dataSource.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("SELECT name FROM test_member");
				ResultSet rs = pstmt.executeQuery()) {

			if (rs.next()) {
				String name = rs.getString("name");

				// 2. DB에서 가져온 이름 콘솔에 찍기
				System.out.println("DB에서 가져온 이름: " + name);

				return "DB Name: " + name; // 브라우저 화면에 출력
			} else {
				System.out.println("데이터가 없습니다.");
				return "No Data in Table";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "Error: " + e.getMessage();
		}
	}
}