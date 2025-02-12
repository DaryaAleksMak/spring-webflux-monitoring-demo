package com.backend.managerapp.config;

import com.backend.managerapp.client.ProductRestClientImpl;
import com.backend.managerapp.security.OAuthClientHttpInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;
import org.springframework.web.client.RestClient;

@Configuration
public class ClientBeans {

  @Bean
  public ProductRestClientImpl productsRestClient(
      @Value("${services.catalogue.uri:http://localhost:8081}") String catalogueBaseUri,
      ClientRegistrationRepository registrationRepository,
      OAuth2AuthorizedClientRepository oAuth2AuthorizedClientRepository,
      @Value("${services.catalogue.registration-id}") String registrationId) {
    return new ProductRestClientImpl(RestClient.builder()
        .baseUrl(catalogueBaseUri)
        .requestInterceptor(new OAuthClientHttpInterceptor(
            new DefaultOAuth2AuthorizedClientManager(registrationRepository,
                oAuth2AuthorizedClientRepository), registrationId))
        .build());
  }


}
