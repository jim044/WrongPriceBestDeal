package com.kode4you.wrongpricebestdeal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class WrongPriceBestDealApplication {

	public static void main(String[] args) {
		SpringApplication.run(WrongPriceBestDealApplication.class, args);
	}

}
