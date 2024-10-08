package com.dodatabase.backend.config;

import com.dodatabase.backend.key.ApiKey;
import lombok.Builder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

  private static final String KMDB_HOST = "http://api.koreafilm.or.kr/openapi-data2/wisenut/search_api/search_json2.jsp?collection=kmdb_new2";

  private final ApiKey apiKey;

  @Builder
  public WebClientConfig(ApiKey apiKey) {
    this.apiKey = apiKey;
  }

  @Bean
  public WebClient movieApiClient() {
    String hostUrl = KMDB_HOST + "&ServiceKey=" + apiKey.getKey();

    return WebClient.builder()
        .codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(16 * 1024 * 1024))
        .baseUrl(hostUrl)
        .build();
  }
}
