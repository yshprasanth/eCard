package com.sri.assignment.eCard.command;

import com.sri.assignment.eCard.command.base.ICardCommand;
import com.sri.assignment.eCard.domain.base.ICard;
import com.sri.assignment.eCard.exceptions.CardException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

@Component("spendCommand")
public class SpendCommand implements ICardCommand {

    private Logger logger = LogManager.getLogger(SpendCommand.class);

    @Value("${spend.threadPool.size}")
    private int threadPoolSize;

    @Value("${spend.thread.sleepTime}")
    private int threadSleepTime;

    @Autowired
    @Qualifier("prePaidCard")
    private ICard card;

    private ThreadPoolExecutor executor;

    @Override
    public void execute(Double amount) throws CardException {
        logger.info("inside execute: " + amount);
        Callable<ICard> task = () -> {
            try {
                return card.spend(amount);
            } catch (CardException e) {
                logger.info("Exception while running the spend task: " + e.toString());
                e.printStackTrace();
            }
            return card;
        };

        Future<ICard> result = executor.submit(task);

        while (!result.isDone()) {
            try {
                Thread.sleep(threadSleepTime);
            } catch (InterruptedException e) {
                throw new CardException(e.getMessage());
            }
        }

        card.balance();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        logger.info("creating ThreadPoolExecutor: " + threadPoolSize);
        executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(threadPoolSize);
    }

    public int getThreadPoolSize() {
        return threadPoolSize;
    }

    public void setThreadPoolSize(int threadPoolSize) {
        this.threadPoolSize = threadPoolSize;
    }

    public ICard getCard() {
        return card;
    }

    public void setCard(ICard card) {
        this.card = card;
    }
}
