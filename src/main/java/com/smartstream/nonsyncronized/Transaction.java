package com.smartstream.nonsyncronized;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.smartstream.common.TransactionStatus;

public class Transaction {
	private static Logger LOG = LoggerFactory.getLogger(Transaction.class);
	private final Account fromAccount;
	private final Account toAccount;
	private final Double amount;
	private String transactionDescription;
	private TransactionStatus transactionStatus = TransactionStatus.NOT_STARTED;
	private long startTime;
	private long endtime;
	
	public Transaction(final Account fromAccount,final Account toAccount,final Double amount,String transactionDescription){
		this.fromAccount = fromAccount;
		this.toAccount = toAccount;
		this.amount = amount;
		this.transactionDescription = transactionDescription;
	}
	
	public TransactionStatus execute(){
		startTime = System.nanoTime();
		transactionStatus = TransactionStatus.ON_GOING;
		if(fromAccount.withdraw(amount)){
			toAccount.deposit(amount);
			transactionStatus = TransactionStatus.SUCCESS;
			endtime = System.nanoTime();
		}else{
			transactionStatus = TransactionStatus.FAILED;
			endtime = System.nanoTime();
		}
		LOG.info(this.toString());
		return transactionStatus;
	}
	
	

	public TransactionStatus getTransactionStatus() {
		return transactionStatus;
	}

	@Override
	public String toString() {
		return "Transaction [fromAccount=" + fromAccount + ", toAccount=" + toAccount + ", amount=" + amount + ", transactionDescription=" + transactionDescription + ", transactionStatus=" + transactionStatus + ", startTime=" + startTime + ", endtime=" + endtime + "]";
	}
	
	
}
