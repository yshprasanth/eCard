package com.sri.assignment.eCard.builder;

import com.sri.assignment.eCard.domain.PrePaidCard;
import com.sri.assignment.eCard.domain.base.ICard;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CardFixtureBuilder {

    public static ICard buildPrePaidCard() {
        DateFormat formatter = new SimpleDateFormat("dd-MM-yy");
        Date fromDate, toDate;
        try {
            fromDate = formatter.parse("01-01-17");
            toDate = formatter.parse("31-12-18");
        } catch (ParseException e) {
            e.printStackTrace();
            fromDate = Calendar.getInstance().getTime();
            toDate = Calendar.getInstance().getTime();
        }

        return new PrePaidCard(1,
                1111222233334444L,
                fromDate,
                toDate,
                "User 1",
                111,
                100.0);
    }
}
