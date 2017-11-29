package com.sri.assignment.eCard.domain.base;

import com.sri.assignment.eCard.exceptions.CardException;
import org.springframework.beans.factory.InitializingBean;

import java.io.Serializable;

/*
 * Interface to define a card and its behavior.
 *
 * We will have multiple instances of this interface, each representing a Card
 */
public interface ICard extends Serializable, InitializingBean {

    // gets the id of the card
    int getId();

    // to print the balance on the card
    Double balance();

    // method to load amount on to card
    ICard load(Double amount);

    // method to load amount on to card
    ICard spend(Double amount) throws CardException;
}
