package com.sri.assignment.eCard.command;

import com.sri.assignment.eCard.command.base.ICardCommand;
import com.sri.assignment.eCard.domain.base.ICard;
import com.sri.assignment.eCard.exceptions.CardException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

/*
 * Implementation to handle Load Command, i.e., load money in card
 */
@Component("loadCommand")
public class LoadCommand implements ICardCommand {

    private Logger logger = LogManager.getLogger(LoadCommand.class);

    /*
     * Performs load operation
     *
     * @param ICard card: Instance of card
     * @param Double amount: The money to be loaded
     *
     * @throws CardException
     */
    @Override
    public void execute(ICard card, Double amount) throws CardException {
        logger.info("before loading : " + amount + " on card: " + card);

        try {
            // call load method on card
            card.load(amount)
                    .balance();
        } catch (Exception e) {
            logger.error("Exception while loading balance into the card..");
            throw new CardException(e.toString());
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        logger.info("inside afterPropertiesSet()..");
    }
}
