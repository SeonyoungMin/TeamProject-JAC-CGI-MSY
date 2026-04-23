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
		System.out.println("--- 모든 멤버 조회 시작 ---");

		// 여러 명의 이름을 합쳐서 담을 변수
		String allNames = "";

		try (Connection conn = dataSource.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("SELECT name FROM test_member");
				ResultSet rs = pstmt.executeQuery()) {

			// if 대신 while을 쓰면 데이터가 끝날 때까지 계속 반복합니다!
			while (rs.next()) {
				String name = rs.getString("name");
				System.out.println("가져온 이름: " + name);
				allNames += name + ", "; // 이름들을 문자열로 합치기
			}

			return "모든 멤버: " + allNames;

		} catch (Exception e) {
			e.printStackTrace();
			return "Error: " + e.getMessage();
		}
	}
}