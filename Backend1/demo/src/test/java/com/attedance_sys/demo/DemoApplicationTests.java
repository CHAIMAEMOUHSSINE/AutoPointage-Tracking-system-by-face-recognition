package com.attedance_sys.demo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Connection;
import java.sql.DriverManager;

@SpringBootTest
class DemoApplicationTests {

	public static void main(String[] args) throws Exception {
		String url = "jdbc:mysql://localhost:3306/attendance_system?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
		String user = "root";
		String password = ""; // change si nécessaire

		Connection conn = DriverManager.getConnection(url, user, password);
		System.out.println("Connexion OK !");
		conn.close();
	}

}
