package com.sri.assignment.eCard.domain.base;

import com.sri.assignment.eCard.exceptions.CardException;
import org.springframework.beans.factory.InitializingBean;

import java.io.Serializable;

public interface ICard extends Serializable, InitializingBean {

    Double balance();

    ICard load(Double amount);

    ICard spend(Double amount) throws CardException;
}
