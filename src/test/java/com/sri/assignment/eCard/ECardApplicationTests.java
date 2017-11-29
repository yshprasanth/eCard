package com.sri.assignment.eCard;

import com.sri.assignment.eCard.command.CardController;
import com.sri.assignment.eCard.exceptions.CardException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ECardApplicationTests {

    @Autowired
    CardController cardController;

    @Test
    public void contextLoads() throws CardException {
        cardController.execute(101D, CardController.ActionType.LOAD);
    }

}
