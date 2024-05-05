package org.javaacademy.onlinebanking;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.ResponseBodyExtractionOptions;
import lombok.SneakyThrows;
import org.javaacademy.onlinebanking.dto.UserDtoRq;
import org.javaacademy.onlinebanking.dto.UserDtoRs;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureMockMvc
class OnlineBankingApplicationTests {
  private final ObjectMapper objectMapper = new ObjectMapper();
  @Test
  @SneakyThrows
  void createUserSuccess() {
    String jsonData = objectMapper.writeValueAsString(new UserDtoRq("8800800", "Иванов И.И."));
	ResponseBodyExtractionOptions body = RestAssured
				.given()
				.body(jsonData)
				.contentType(ContentType.JSON)
				.post("/bank/auth/user/signup")
				.then()
				.statusCode(201)
				.extract()
				.body();
	UserDtoRs userDtoRs = body.as(UserDtoRs.class);
	System.out.println(userDtoRs.getPinCod());

  }
}
