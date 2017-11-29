package com.sri.assignment.eCard;

import com.sri.assignment.eCard.command.CardController;
import com.sri.assignment.eCard.domain.base.ICard;
import com.sri.assignment.eCard.exceptions.CardException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ECardApplicationTests {

    @Autowired
    CardController cardController;

    @Autowired
    @Qualifier("card1")
    private ICard card;

    @Test
    public void contextLoads() throws CardException {
        cardController.execute(card, 101D, CardController.Command.LOAD);
    }
}
