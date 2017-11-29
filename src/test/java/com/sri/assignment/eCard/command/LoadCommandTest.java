package com.sri.assignment.eCard.command;

import com.sri.assignment.eCard.builder.CardFixtureBuilder;
import com.sri.assignment.eCard.command.base.ICardCommand;
import com.sri.assignment.eCard.domain.base.ICard;
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
 * LoadCommand Tester.
 *
 * @author Sri Yalamanchili
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class LoadCommandTest {

    @Autowired
    @Qualifier("loadCommand")
    ICardCommand loadCommandToTest;

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
    @Test
    public void testExecute() throws Exception {
        assertEquals("Balance on the card does not match", 100d, prePaidCardToTest.balance().doubleValue(), 0.0);

        loadCommandToTest.execute(prePaidCardToTest, 200d);
        assertEquals("Balance on the card does not match", 300d, prePaidCardToTest.balance().doubleValue(), 0.0);
    }
}
