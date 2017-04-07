package com.smartstream.nonsyncronized;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class TransactionProcessor implements Runnable{
	private static Logger LOG = LoggerFactory.getLogger(TransactionProcessor.class);
	 private ExecutorService executorService;
	 private volatile boolean stopProcessor = false;
	 
	 private ArrayBlockingQueue<Transaction> transactionsQueue = new ArrayBlockingQueue<>(10000);
	 
	 public TransactionProcessor(){
		 executorService =  Executors.newFixedThreadPool(10);
		 executorService.submit(this);
	 }
	 
	 public boolean addTransaction(Transaction transaction) throws InterruptedException{
		 transactionsQueue.put(transaction);
		 return true;
	 }
	 

	 
	 private void start() throws InterruptedException{
		 while(!stopProcessor){
			 LOG.info("Getting transaction from the queue");
			 final Transaction transaction = transactionsQueue.take();
			 LOG.info("Received transaction from the queue Submiting now");
			 executorService.submit(new Runnable() {
				
				@Override
				public void run() {
					transaction.execute();
				}
			}) ;
		 }
	 }
	 
	 public void stop(){
		 stopProcessor = true;
		 executorService.shutdown();
	 }

	@Override
	public void run() {
		try {
			start();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	 
	 
}
