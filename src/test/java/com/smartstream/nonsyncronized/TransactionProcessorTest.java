package com.smartstream.nonsyncronized;

import static org.junit.Assert.*;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.smartstream.common.TransactionStatus;

public class TransactionProcessorTest {
	private static Logger LOG = LoggerFactory.getLogger(TransactionProcessorTest.class);
	TransactionProcessor transactionProcessor = new TransactionProcessor();

	@Test
	public void test() {
		
		
		
		
		for(int i=0;i<100000;i++){
			Account accountA = new Account(1,10000d);
			Account accountB = new Account(2,10000d);
			Account accountC = new Account(3,10000d);
			
			Transaction transaction1 = new Transaction(accountA, accountB, 500d, "from A to B money transfer");
			Transaction transaction2 = new Transaction(accountB, accountC, 500d, "from B to C money transfer");
			Transaction transaction3 = new Transaction(accountC, accountA, 500d, "from C to A money transfer");
			try {
				transactionProcessor.addTransaction(transaction1);
				transactionProcessor.addTransaction(transaction2);
				transactionProcessor.addTransaction(transaction3);
				
				while(!transaction1.getTransactionStatus().equals(TransactionStatus.SUCCESS)){
					Thread.sleep(5);
				}
				while(!transaction2.getTransactionStatus().equals(TransactionStatus.SUCCESS)){
					Thread.sleep(5);
				}
				while(!transaction3.getTransactionStatus().equals(TransactionStatus.SUCCESS)){
					Thread.sleep(5);
				}
				assertEquals(10000d, accountA.getBalance(),0.0d);
				assertEquals(10000d, accountB.getBalance(),0.0d);
				assertEquals(10000d, accountC.getBalance(),0.0d);
				LOG.info("Sucessfull transactions for loop {} ",i);
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		transactionProcessor.stop();
		
	}

}
