package com.example.stockapp2.config;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.velocity.app.VelocityEngine;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.client.RestTemplate;

import java.util.Properties;

@Configuration
@Getter
@Setter
@Slf4j
public class AppConfig {

    @Value("${spring.mail.username}")
    private String emailUserName;

    @Value("${spring.mail.password}")
    private String emailPassword;
//
//    @Value("${paystack.base.url}")
//    private String paystackUrl;
//
//    @Value("${paystack.api.live.key}")
//    private String paystackLiveApiKey;
//
//    @Value("${paystack.api.test.key}")
//    private String paystackTestApiKey;
//
//    @Value("${paystack.api.domain:test}")
//    private String paystackDomain;
//
//    @Value("${paystack.api.transfer.callback}")
//    private String transferCallbackUrl;
//
//    @Value("${job.reminder.batch.count}")
//    private int reminderJobBatch;

//    @Bean
//    public PaystackConfig paystackConfig() {
//        return PaystackConfig.builder()
//                .baseUrl(paystackUrl)
//                .domain(PaystackConfig.Domain.fromString(paystackDomain))
//                .liveApiKey(paystackLiveApiKey)
//                .testApiKey(paystackTestApiKey)
//                .paymentReason("Betacare Payment")
//                .transferCallBackUrl(transferCallbackUrl)
//                .build();
//    }

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
        return modelMapper;
    }

    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource
                = new ReloadableResourceBundleMessageSource();

        messageSource.setBasename("classpath:messages");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }

    @Bean
    public LocalValidatorFactoryBean getValidator() {
        LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
        bean.setValidationMessageSource(messageSource());
        return bean;
    }

    @Bean
    public VelocityEngine velocityEngine() {
        VelocityEngine velocityEngine = new VelocityEngine();
        velocityEngine.setProperty("resource.loader", "class");
        velocityEngine.setProperty("class.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        return velocityEngine;
    }

    @Bean
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

        mailSender.setHost("in-v3.mailjet.com");
        mailSender.setPort(587);
        mailSender.setUsername(emailUserName);
        mailSender.setPassword(emailPassword);



        Properties props = mailSender.getJavaMailProperties();

        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.debug.auth", "true");
        props.put("mail.debug", "true");
        props.put("mail.smtp.ssl.trust", "*");
        props.put("mail.smtp.starttls.enable","true");

        return mailSender;
    }

    @Bean
    public RestTemplate restTemplate () {
        return new RestTemplate();
    }
}
