package com.savemymoney.savemymoneyapi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
//@EnableJpaRepositories(basePackages = "com.savemymoney.savemymoneyapi.repositories")
//@EntityScan(basePackages = "com.savemymoney.savemymoneyapi.entities")
public class SavemymoneyapiApplication {

    private static Logger logger = LoggerFactory.getLogger(SavemymoneyapiApplication.class);

    public static void main(String[] args) {
        logger.info("Iniciando a API SaveMyMoney");
        SpringApplication.run(SavemymoneyapiApplication.class, args);
        logger.info("API iniciada com sucesso");
    }

}
