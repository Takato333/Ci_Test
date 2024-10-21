package com.example.demo;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Hair Salon API", version = "1.0", description = "Information"))
@SecurityScheme(name = "api", scheme = "bearer", type = SecuritySchemeType.HTTP, in = SecuritySchemeIn.HEADER)
public class DemoApplication {
	// quản lí sinh viên
	// sinh viên (name, studentCode, score)
	// Lấy ra danh sách tất cả sinh viên
	// Thêm 1 sinh viên mới vào danh sách
	// Update điểm của sinh viên
	// Delete 1 sinh viên nào đó

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}


	// Restfull API
	// cách đặt tên api

	// Thêm 1 sinh viên mới
	// /api/student  => POST: Tạo 1 student mới
	// /api/student/studentId  => PUT: update thông tin của student
	// /api/student/studentId  => DELETE: delete thông tin của student
	// /api/student/ => GET: lấy tất cả thông tin của student
	// /api/student/studentId  => GET: lấy thông tin của 1 student cụ thể


	// METHOD:
	/*
	* 		POST => create
	*		PUT  => update
	* 		DELETE => delete
	* 		GET  => get
	* */

}
