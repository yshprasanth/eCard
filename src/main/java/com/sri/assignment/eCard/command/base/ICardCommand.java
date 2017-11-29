package com.sri.assignment.eCard.command.base;

import com.sri.assignment.eCard.exceptions.CardException;
import org.springframework.beans.factory.InitializingBean;

public interface ICardCommand extends InitializingBean {

    void execute(Double amount) throws CardException;
}