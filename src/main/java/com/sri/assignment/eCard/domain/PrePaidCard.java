package com.sri.assignment.eCard.domain;

import com.sri.assignment.eCard.domain.base.ICard;
import com.sri.assignment.eCard.exceptions.CardException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.Date;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class PrePaidCard implements ICard {

    static Logger logger = LogManager.getLogger(PrePaidCard.class);
    ReadWriteLock lock = new ReentrantReadWriteLock();
    private long serialVersionUID = 123L;
    private int id;
    private long number;
    private Date validFrom;
    private Date validTo;
    private String nameOnCard;
    private int cvv;
    private double balance;

    public PrePaidCard() {
    }

    public PrePaidCard(int id, long number, Date validFrom, Date validTo, String nameOnCard, int cvv, double balance) {
        this.id = id;
        this.number = number;
        this.validFrom = validFrom;
        this.validTo = validTo;
        this.nameOnCard = nameOnCard;
        this.cvv = cvv;
        this.balance = balance;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        logger.info("inside afterPropertiesSet()...");
    }

    @Override
    public Double balance() {
        lock.readLock().lock();
        try {
            logger.info("balance: " + this.balance);
            return this.balance;
        } finally {
            lock.readLock().unlock();
        }
    }

    @Override
    public ICard load(Double amount) {
        lock.writeLock().lock();
        try {
            this.balance += amount;
        } finally {
            lock.writeLock().unlock();
        }
        return this;
    }

    @Override
    public ICard spend(Double amount) throws CardException {
        lock.writeLock().lock();
        try {
            if (amount > this.balance)
                this.balance -= amount;
            else
                throw new CardException("Not enough funds for the transaction !!");
        } finally {
            lock.writeLock().unlock();
        }
        return this;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getNumber() {
        return number;
    }

    public void setNumber(long number) {
        this.number = number;
    }

    public Date getValidFrom() {
        return validFrom;
    }

    public Date getValidTo() {
        return validTo;
    }

    public String getNameOnCard() {
        return nameOnCard;
    }

    public Integer getCvv() {
        return cvv;
    }

    public Double getBalance() {
        return balance();
    }

    @Override
    public String toString() {
        return "PrePaidCard{" +
                "id=" + id +
                ", number=" + number +
                ", validFrom=" + validFrom +
                ", validTo=" + validTo +
                ", nameOnCard='" + nameOnCard + '\'' +
                ", cvv=" + cvv +
                ", balance=" + balance +
                '}';
    }

}
