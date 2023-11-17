package com.savemymoney.savemymoneyapi;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SavemymoneyapiApplication {

    private static Logger logger = LoggerFactory.getLogger(SavemymoneyapiApplication.class);

    public static void main(String[] args) {
        logger.info("Iniciando a API SaveMyMoney");
        SpringApplication.run(SavemymoneyapiApplication.class, args);
        logger.info("API iniciada com sucesso");
    }

}
