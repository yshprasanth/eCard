package com.sri.assignment.eCard.command.base;

import com.sri.assignment.eCard.domain.base.ICard;
import com.sri.assignment.eCard.exceptions.CardException;
import org.springframework.beans.factory.InitializingBean;

/*
 * Interface to handle transactions on Cards.
 *
 * We will have multiple instances of this interface
 * and each of them will be handling a particular action/command
 */
public interface ICardCommand extends InitializingBean {
    void execute(ICard card, Double amount) throws CardException;
}
