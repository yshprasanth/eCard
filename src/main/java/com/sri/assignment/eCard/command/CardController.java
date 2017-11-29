package com.sri.assignment.eCard.command;

import com.sri.assignment.eCard.command.base.ICardCommand;
import com.sri.assignment.eCard.exceptions.CardException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class CardController {

    private Logger logger = LogManager.getLogger(CardController.class);

    @Autowired
    @Qualifier("loadCommand")
    private ICardCommand loadCommand;

    @Autowired
    @Qualifier("spendCommand")
    private ICardCommand spendCommand;

    public void execute(Double amount, ActionType type) throws CardException {
        logger.info("inside execute().." + type + ", " + amount);
        switch (type) {
            case LOAD:
                loadCommand.execute(amount);
                break;
            case SPEND:
                spendCommand.execute(amount);
                break;
            default:
                logger.warn("Not a valid action type: " + type);
        }
    }

    public enum ActionType {
        LOAD, SPEND
    }
}
