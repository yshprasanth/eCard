package com.sri.assignment.eCard;

import com.sri.assignment.eCard.domain.PrePaidCard;
import com.sri.assignment.eCard.domain.base.ICard;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/*
 * ECard Spring Boot Application
 */
@SpringBootApplication
public class ECardApplication {

    private static Logger logger = LogManager.getLogger(ECardApplication.class);

    /*
     * Main method where the beans are loaded and the execution starts
     */
    public static void main(String[] args) {
        SpringApplication.run(ECardApplication.class, args);
        logger.info("started ECardApplication..");
    }

    /*
     * Creates an instance of prePaidCard for the purpose of testing this app.
     * Ideally, these instances will be generated from the
     * database using JPA/Hibernate/SpringData etc
     */
    @Bean("card1")
    public ICard prePaidCard() throws ParseException {
        DateFormat formatter = new SimpleDateFormat("dd-MM-yy");
        Date fromDate, toDate;
        try {
            fromDate = formatter.parse("01-01-17");
            toDate = formatter.parse("31-12-18");
        } catch (ParseException e) {
            e.printStackTrace();
            throw e;
        }

        return new PrePaidCard(1, 1111222233334444L, fromDate, toDate, "User 1", 111, 100.0);
    }

}
