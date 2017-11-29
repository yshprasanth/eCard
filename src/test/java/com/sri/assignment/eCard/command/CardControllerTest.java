package com.sri.assignment.eCard.command;

import com.sri.assignment.eCard.builder.CardFixtureBuilder;
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

import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * CardController Tester.
 *
 * @author Sri Yalamanchili
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class CardControllerTest {

    @Autowired
    @Qualifier("cardController")
    CardController cardControllerToTest;

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
     * Method: execute(Double amount, ActionType type)
     */
    @Test(expected = CardException.class)
    public void testExecuteSingleThread() throws Exception {
        assertEquals("Balance on the card does not match", 100d, prePaidCardToTest.balance().doubleValue(), 0.0);

        cardControllerToTest.execute(prePaidCardToTest, 10d, CardController.Command.SPEND);
        assertEquals("Balance on the card does not match", 90d, prePaidCardToTest.balance().doubleValue(), 0.0);

        cardControllerToTest.execute(prePaidCardToTest, 50d, CardController.Command.LOAD);
        assertEquals("Balance on the card does not match", 140d, prePaidCardToTest.balance().doubleValue(), 0.0);

        try {
            cardControllerToTest.execute(prePaidCardToTest, 150d, CardController.Command.SPEND);
        } catch (Exception e) {
            assertTrue(e instanceof CardException);
            assertEquals("Caught Unknown Exception", "Not enough funds for the transaction.", e.getMessage());
            throw e;
        }
    }

    @Test
    public void testExecuteMultipleThreadsConcurrently() throws Exception {
        assertEquals("Balance on the card does not match", 100d, prePaidCardToTest.balance().doubleValue(), 0.0);

        Runnable spendTask = () -> {
            try {
                cardControllerToTest.execute(prePaidCardToTest, 10d, CardController.Command.SPEND);
            } catch (CardException e) {
                e.printStackTrace();
            }
        };

        Runnable loadTask = () -> {
            try {
                cardControllerToTest.execute(prePaidCardToTest, 100d, CardController.Command.LOAD);
            } catch (CardException e) {
                e.printStackTrace();
            }
        };

        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(5);
        Future result1 = executor.submit(spendTask);
        Future result2 = executor.submit(spendTask);
        Future result3 = executor.submit(loadTask);
        Future result4 = executor.submit(spendTask);
        Future result5 = executor.submit(spendTask);

        while (!result1.isDone() || !result2.isDone() || !result3.isDone() || !result4.isDone() || !result5.isDone()) {
            Thread.sleep(1000);
        }


        // 160.0 should be the balance after spending 40.0 in 4 transactions and loading 100.0 in 1 transaction
        assertEquals("Balance on the card does not match", 160d, prePaidCardToTest.balance().doubleValue(), 0.0);
    }
} 
