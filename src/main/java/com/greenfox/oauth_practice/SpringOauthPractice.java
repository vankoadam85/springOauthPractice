package com.greenfox.oauth_practice;

import com.greenfox.oauth_practice.configuration.AppProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(AppProperties.class)
public class SpringOauthPractice {

  public static void main(String[] args) {
    SpringApplication.run(SpringOauthPractice.class, args);
  }

}

