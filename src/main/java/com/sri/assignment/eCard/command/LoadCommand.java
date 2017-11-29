package com.sri.assignment.eCard.command;

import com.sri.assignment.eCard.command.base.ICardCommand;
import com.sri.assignment.eCard.domain.base.ICard;
import com.sri.assignment.eCard.exceptions.CardException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component("loadCommand")
public class LoadCommand implements ICardCommand {

    private Logger logger = LogManager.getLogger(LoadCommand.class);

    @Autowired
    @Qualifier("prePaidCard")
    private ICard card;

    @Override
    public void execute(Double amount) throws CardException {
        logger.info("inside execute command: " + amount);

        card.load(amount)
                .balance();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        logger.info("inside afterPropertiesSet()..");
    }
}
