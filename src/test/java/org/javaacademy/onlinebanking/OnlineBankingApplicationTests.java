package org.javaacademy.onlinebanking;


import static org.springframework.http.HttpStatus.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import lombok.SneakyThrows;
import org.hamcrest.Matchers;
import org.javaacademy.onlinebanking.dto.AccountDtoRq;
import org.javaacademy.onlinebanking.dto.OperationDtoRq;
import org.javaacademy.onlinebanking.dto.OperationDtoRs;
import org.javaacademy.onlinebanking.dto.UserDtoRq;
import org.javaacademy.onlinebanking.entity.Account;
import org.javaacademy.onlinebanking.entity.User;
import org.javaacademy.onlinebanking.repository.AccountRepository;
import org.javaacademy.onlinebanking.repository.AuthRepository;
import org.javaacademy.onlinebanking.repository.OperationRepository;
import org.javaacademy.onlinebanking.repository.UserRepository;
import org.javaacademy.onlinebanking.service.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureMockMvc
//@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class OnlineBankingApplicationTests {
  private static final String BASE_URL = "/bank";
  private static final String PHONE = "8800800";
  private static final String FIO = "Иванов И.И.";

  @Autowired
  private AccountRepository accountRepository;
  @Autowired
  private AuthRepository authRepository;
  @Autowired
  private UserRepository userRepository;
  @Autowired
  private UserService userService;

  private final ObjectMapper objectMapper = new ObjectMapper();

  @Test
  @SneakyThrows
  void createUserSuccess() {
    createUser();
	User user = userRepository.findByPhone(PHONE).orElseThrow();
	Integer countEntity = userRepository.findAll().size();
	Assertions.assertEquals(PHONE, user.getPhone());
	Assertions.assertEquals(FIO, user.getFio());
	Assertions.assertEquals(1, countEntity);
  }

  @Test
  @SneakyThrows
  void createAuthSuccess() {
	String pinCod = createUser();
	createAuthUser(pinCod);
	Integer countRecord = authRepository.getAuthUsers().size();
	User user = userRepository.findByPhone("8800800").orElseThrow();
	Assertions.assertEquals(pinCod, authRepository.getAuthUsers().get(user.getUuid()));
	Assertions.assertTrue(authRepository.checkRecordUser(user.getUuid(), pinCod));
	Assertions.assertEquals(1, countRecord);
  }

  @Test
  @SneakyThrows
  void createAccountSuccess() {
	String token = createAuthUser(createUser());
	AccountDtoRq accountDtoRq = new AccountDtoRq(token);
	String numberAccount = createAccount(accountDtoRq);
	Integer countRecord = accountRepository.getAccounts().size();
	Account account = accountRepository.getAccounts().get(numberAccount);
	Assertions.assertEquals(numberAccount, account.getNumberAccount());
	Assertions.assertTrue(accountRepository.verificationAccountAndUser(numberAccount, userService.findUserByToken(token)));
	Assertions.assertEquals(1, countRecord);
  }

  @Test
  @SneakyThrows
  void giveMoneyToBankSuccess() {
	String token = createAuthUser(createUser());
	AccountDtoRq accountDtoRq = new AccountDtoRq(token);
	String numberAccount = createAccount(accountDtoRq);
	createOperation(token,
				 BigDecimal.valueOf(1000),
				 numberAccount, "кладем деньги на счет",
				BASE_URL + "/operations/receive");
	Integer countRecord = accountRepository.getAccounts().size();
	Account account = accountRepository.getAccounts().get(numberAccount);
	Assertions.assertEquals(1, countRecord);
	Assertions.assertEquals(BigDecimal.valueOf(1000), account.getValue());
	Assertions.assertEquals(numberAccount, account.getNumberAccount());
  }

  @Test
  @SneakyThrows
  void takeMoneyToBankSuccess() {
	String token = createAuthUser(createUser());
	AccountDtoRq accountDtoRq = new AccountDtoRq(token);
	String numberAccount = createAccount(accountDtoRq);
	createOperation(token,
				 BigDecimal.valueOf(1000),
				 numberAccount, "кладем деньги на счет",
			BASE_URL + "/operations/receive");
	createOperation(token,
				 BigDecimal.valueOf(100),
				 numberAccount, "берем деньги со счета",
			BASE_URL + "/operations/pay");
	Integer countRecord = accountRepository.getAccounts().size();
	Account account = accountRepository.getAccounts().get(numberAccount);
	Assertions.assertEquals(1, countRecord);
	Assertions.assertEquals(BigDecimal.valueOf(900), account.getValue());
	Assertions.assertEquals(numberAccount, account.getNumberAccount());
  }

  @Test
  void getAccountByUserSuccess() {
    String token = createAuthUser(createUser());
	AccountDtoRq accountDtoRq = new AccountDtoRq(token);
	String numberAccount = createAccount(accountDtoRq);
	accountRepository.updateMoneyByAccount(numberAccount, BigDecimal.valueOf(1000));
	String account = RestAssured
			  .given()
			  .log().all()
			  .get(BASE_URL + "/account/" + numberAccount)
			  .then()
			  .log().all()
			  .statusCode(CREATED.value())
			  .extract()
			  .body().asString();

	RestAssured
			  .given()
			  .queryParam("account", numberAccount)
			  .get(BASE_URL + "/account/" + numberAccount)
			  .then()
			  .statusCode(CREATED.value())
			  .time(Matchers.lessThan(1L), TimeUnit.SECONDS);
  }

  @Test
  void findAllOperationsByUserSuccess() {
	String token = createAuthUser(createUser());
	RestAssured
			.given()
			.queryParam("token", token)
			.get(BASE_URL + "/operations")
			.then()
			.statusCode(ACCEPTED.value())
			.log().all()
			.extract()
			.body();
  }

	@Test
	void findAllAccountByUserSuccess() {
	  String token = createAuthUser(createUser());
	  RestAssured
				.given()
				.queryParam("token", token)
				.get(BASE_URL + "/account")
				.then()
				.statusCode(CREATED.value())
				.log().all()
				.extract()
				.body();
  }

  @SneakyThrows
  private String createUser() {
	String jsonData = objectMapper.writeValueAsString(new UserDtoRq(PHONE, FIO));
	return RestAssured
			   .given()
			   .body(jsonData)
			   .contentType(ContentType.JSON)
			   .post(BASE_URL + "/auth/user/signup")
			   .then()
			   .statusCode(CREATED.value())
			   .extract()
			   .body().asString();
  }

  @SneakyThrows
  private String createAuthUser(String pinCod) {
	UserDtoRq userDtoRq = new UserDtoRq(PHONE, FIO);
	userDtoRq.setPinCod(pinCod);
	String jsonData = objectMapper.writeValueAsString(userDtoRq);
	return RestAssured
			  .given()
			  .body(jsonData)
			  .contentType(ContentType.JSON)
			  .post(BASE_URL + "/auth/user/auth")
			  .then()
			  .statusCode(ACCEPTED.value())
			  .extract()
			  .body().asString();
  }

  @SneakyThrows
  private String createAccount(AccountDtoRq accountDtoRq) {
	String jsonData = objectMapper.writeValueAsString(accountDtoRq);
	return RestAssured
			  .given()
			  .body(jsonData)
			  .contentType(ContentType.JSON)
			  .post(BASE_URL + "/account")
			  .then()
			  .statusCode(CREATED.value())
			  .extract()
			  .body().asString();
  }

  @SneakyThrows
  private void createOperation(String token, BigDecimal bigDecimal,
							   String numberAccount, String description, String post) {
    String jsonData = objectMapper.writeValueAsString(new OperationDtoRq
			                   (token, bigDecimal, numberAccount, description));
	RestAssured
			  .given()
			  .body(jsonData)
			  .contentType(ContentType.JSON)
			  .post(post)
			  .then()
			  .statusCode(OK.value())
			  .extract()
			  .body();
  }
}
