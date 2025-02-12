package com.backend.customerapp.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class ClientConfig {

  @Bean
  public WebClient webClient(
      @Value("${customer.service.catalogue.uri}") String uri) {
    return WebClient.builder().baseUrl(uri).build();
  }
}
