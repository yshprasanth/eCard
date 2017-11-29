package com.sri.assignment.eCard.command;

import com.sri.assignment.eCard.command.base.ICardCommand;
import com.sri.assignment.eCard.domain.base.ICard;
import com.sri.assignment.eCard.exceptions.CardException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.concurrent.*;

/*
 * Implementation to handle Spend Command, i.e., spend money on card
 */
@Component("spendCommand")
public class SpendCommand implements ICardCommand {

    private Logger logger = LogManager.getLogger(SpendCommand.class);

    // Load value from property file
    @Value("${spend.threadPool.size}")
    private int threadPoolSize;

    // Load value from property file
    @Value("${spend.thread.sleepTime}")
    private int threadSleepTime;

    // ThreadPoolExecutor instance to handle multiple calls concurrently
    private ThreadPoolExecutor executor;

    /*
     * Performs spend operation
     *
     * @param ICard card: Instance of card
     * @param Double amount: The money to be spent
     *
     * @throws CardException
     */
    @Override
    public void execute(ICard card, Double amount) throws CardException {
        logger.info("before spending : " + amount + " on card: " + card);

        // create a callable task that will invoke spend action on the card
        Callable<ICard> task = () -> {
            try {
                return card.spend(amount);
            } catch (CardException e) {
                logger.info("Exception while running the spend task: " + e.toString());
                e.printStackTrace();
                throw e;
            }
        };

        // submit the task
        Future<ICard> result = executor.submit(task);

        // wait until the task is complete
        while (!result.isDone()) {
            try {
                Thread.sleep(threadSleepTime);
            } catch (InterruptedException e) {
                throw new CardException(e.getMessage());
            }
        }

        // Following block is to catch and rethrow the CardException, in case
        // the underlying Callable interface threw an exception.
        try {
            result.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            logger.info("Caught ExecutionException: " + e.getMessage());
            throw (CardException) e.getCause();
        }

        // print card balance
        card.balance();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        logger.info("creating ThreadPoolExecutor: " + threadPoolSize);
        // create instance of ThreadPoolExecutor using the value from properties file
        executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(threadPoolSize);
    }

    public void setThreadPoolSize(int threadPoolSize) {
        this.threadPoolSize = threadPoolSize;
    }


}
