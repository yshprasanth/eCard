package com.sri.assignment.eCard.command;

import com.sri.assignment.eCard.command.base.ICardCommand;
import com.sri.assignment.eCard.domain.base.ICard;
import com.sri.assignment.eCard.exceptions.CardException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/*
 * Entry/Controller class to handle the operations
 * on Cards.
 */
@Component
public class CardController {

    private Logger logger = LogManager.getLogger(CardController.class);

    // Get a handle to the loadCommand
    @Autowired
    @Qualifier("loadCommand")
    private ICardCommand loadCommand;

    // Get a handle to the spendCommand
    @Autowired
    @Qualifier("spendCommand")
    private ICardCommand spendCommand;

    /*
     * Identify the command/action type and perform/invoke the implementation.
     * @param ICard card: instance of the card
     * @param Double amount: The money to be loaded/spent
     * @param ActionType type: indicates the command
     *
     * @throws CardException
     */
    public void execute(ICard card, Double amount, Command type) throws CardException {
        logger.info("inside execute().." + type + ", " + amount);
        switch (type) {
            case LOAD:
                loadCommand.execute(card, amount);
                break;
            case SPEND:
                spendCommand.execute(card, amount);
                break;
            default:
                logger.warn("Not a valid action type: " + type);
        }
    }

    public enum Command {
        LOAD, SPEND
    }
}
