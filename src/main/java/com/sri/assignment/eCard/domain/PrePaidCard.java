package com.sri.assignment.eCard.domain;

import com.sri.assignment.eCard.domain.base.ICard;
import com.sri.assignment.eCard.exceptions.CardException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.Date;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/*
 * Domain object to represent PrePaid Card, a variant of a ICard.
 */
public class PrePaidCard implements ICard {

    // used to serialize
    private long serialVersionUID = 123L;

    private Logger logger = LogManager.getLogger(PrePaidCard.class);

    // ReadWriteLock to handle the load, spend & balance actions on the card
    // This is to ensure the value is displayed consistently to all the threads
    private ReadWriteLock lock;

    private int id;
    private long number;
    private Date validFrom;
    private Date validTo;
    private String nameOnCard;
    private int cvv;
    private volatile double balance;

    /*
     * Default Constructor
     */
    public PrePaidCard() {
        // create an instance of ReentrantReadWriteLock
        lock = new ReentrantReadWriteLock();
    }

    /*
     * Constructor to create instance of PrePaidCard
     * This is the only way to create the card at the minute.
     *
     * We can replace this with Factory/Builders/JPA to create instances of this card.
     */
    public PrePaidCard(int id, long number, Date validFrom, Date validTo, String nameOnCard, int cvv, double balance) {
        this();
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

    /*
     * Method to print balance on the card.
     *
     * Gets a readLock on the balance variable.
     */
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

    /*
     * Method to load amount on the card.
     *
     * Gets a writeLock on the balance variable.
     *
     * @param Double amount: amount to be added to card
     *
     * @return instance of ICard
     */
    @Override
    public ICard load(Double amount) {
        lock.writeLock().lock();
        try {
            logger.info("adding " + amount + " to " + this.balance);
            this.balance += amount;
        } finally {
            lock.writeLock().unlock();
        }
        return this;
    }

    /*
     * Method to spend amount on the card.
     *
     * Gets a writeLock on the balance variable.
     *
     * @param Double amount: amount to be added to be spent
     *
     * @return instance of ICard
     */
    @Override
    public ICard spend(Double amount) throws CardException {
        lock.writeLock().lock();
        try {
            logger.info("spending " + amount + " from " + this.balance);
            if (amount > this.balance)
                this.balance -= amount;
            else
                throw new CardException("Not enough funds for the transaction !!");
        } finally {
            lock.writeLock().unlock();
        }
        return this;
    }

    /*
     * Getter for id of the card
     */
    @Override
    public int getId() {
        return id;
    }

    /*
     * toString() implementation
     */
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

    @Override
    public boolean equals(Object obj) {
        return id==((ICard)obj).getId();
    }

    @Override
    @SuppressWarnings("deprecation")
    protected void finalize() throws Throwable {
        super.finalize();
        lock.readLock().unlock();
        lock.writeLock().unlock();
        lock = null;
    }
}
