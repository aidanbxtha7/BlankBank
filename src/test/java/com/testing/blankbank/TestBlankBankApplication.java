package com.testing.blankbank;

import com.testing.blankbank.app.BlankBankApplication;
import org.springframework.boot.SpringApplication;

public class TestBlankBankApplication {

    public static void main(String[] args) {
        SpringApplication.from(BlankBankApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
