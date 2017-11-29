package com.sri.assignment.eCard.domain;

import com.sri.assignment.eCard.builder.CardFixtureBuilder;
import com.sri.assignment.eCard.domain.base.ICard;
import com.sri.assignment.eCard.exceptions.CardException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * PrePaidCard Tester.
 *
 * @author Sri Yalamanchili
 */
public class PrePaidCardTest {

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
     * Methods: load(), spend(), balance()
     */
    @Test(expected = CardException.class)
    public void testBalanceSingleThread() throws Exception {
        assertEquals("Balance on the card does not match", 100d, prePaidCardToTest.balance().doubleValue(), 0.0);
        prePaidCardToTest.spend(10d);
        assertEquals("Balance on the card does not match", 90d, prePaidCardToTest.balance().doubleValue(), 0.0);

        prePaidCardToTest.load(50d);
        assertEquals("Balance on the card does not match", 140d, prePaidCardToTest.balance().doubleValue(), 0.0);

        try {
            prePaidCardToTest.spend(150d);
        } catch (Exception e) {
            assertTrue(e instanceof CardException);
            assertEquals("Caught Unknown Exception", "Not enough funds for the transaction.", e.getMessage());
            throw e;
        }
    }

    /**
     * Methods: load(), spend(), balance()
     */
    @Test
    public void testBalanceMultipleThreadsConcurrently() throws Exception {
        assertEquals("Balance on the card does not match", 100d, prePaidCardToTest.balance().doubleValue(), 0.0);

        Runnable spendTask = () -> {
            try {
                prePaidCardToTest.spend(10d);
            } catch (CardException e) {
                e.printStackTrace();
            }
        };

        Runnable loadTask = () -> {
            prePaidCardToTest.load(100d);
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
