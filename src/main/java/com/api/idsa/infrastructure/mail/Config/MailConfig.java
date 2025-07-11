package com.api.idsa.infrastructure.mail.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mailgun.api.v3.MailgunMessagesApi;
import com.mailgun.client.MailgunClient;

@Configuration
public class MailConfig {

    @Value("${mailgun.private.api.key}")
    private String privateApiKey;

    @Bean
    public MailgunMessagesApi mailgunMessagesApi() {
        return MailgunClient.config(privateApiKey)
            .createApi(MailgunMessagesApi.class);
    }

}
