package org.javaacademy.onlinebanking.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class SwaggerConfig {
  private final static String BASE_URL = "http://localhost:8080";

  @Bean
  public OpenAPI myOpenAPI() {

    Server  devServer = new Server();
    devServer.setUrl(BASE_URL);
    devServer.setDescription("Банк сервис");

    Contact contact = new Contact();
    contact.setEmail("test@mail.ru");
    contact.setName("Роман Доброхотов");
    contact.setUrl("https://www.java-academy.ru");

      Info info = new Info()
                .title("Api сервиса по работе с банками")
                .version("1.0")
                .contact(contact)
                .description("Апи по работе со счетами клиентов");

    return new OpenAPI().info(info).servers(List.of(devServer));
  }
}
