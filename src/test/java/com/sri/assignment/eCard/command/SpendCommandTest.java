package com.sri.assignment.eCard.command;

import com.sri.assignment.eCard.builder.CardFixtureBuilder;
import com.sri.assignment.eCard.command.base.ICardCommand;
import com.sri.assignment.eCard.domain.base.ICard;
import com.sri.assignment.eCard.exceptions.CardException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

/**
 * SpendCommand Tester.
 *
 * @author Sri Yalamanchili
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SpendCommandTest {

    @Autowired
    @Qualifier("spendCommand")
    ICardCommand spendCommandToTest;

    private ICard prePaidCardToTest;

    @Before
    public void before() throws Exception {
        prePaidCardToTest = CardFixtureBuilder.buildPrePaidCard();
    }

    @After
    public void after() throws Exception {
        prePaidCardToTest = null;
    }

    /**
     * Method: execute(ICard card, Double amount)
     */
    @Test(expected = CardException.class)
    public void testExecute() throws Exception {
        assertEquals("Balance on the card does not match", 100d, prePaidCardToTest.balance().doubleValue(), 0.0);

        spendCommandToTest.execute(prePaidCardToTest, 40d);
        assertEquals("Balance on the card does not match", 60d, prePaidCardToTest.balance().doubleValue(), 0.0);

        // The below command should throw an exception
        spendCommandToTest.execute(prePaidCardToTest, 60.01);
    }
}
