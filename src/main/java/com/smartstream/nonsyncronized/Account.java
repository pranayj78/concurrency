package com.smartstream.nonsyncronized;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Account {
	private static Logger LOG = LoggerFactory.getLogger(Account.class);
	private final Integer accountNumber; 
	private volatile Double balance;
	
	public Account(final Integer accountNumber,Double initialBalance ){
		this.accountNumber = accountNumber;
		this.balance = initialBalance;
	}
	
	public boolean deposit(Double amount){
		this.balance = this.balance + amount;
		LOG.info("After deposit the present balance of Account {} is {} ",accountNumber,balance);
		return true;
	}
	
	public boolean withdraw(Double amount){
		if(this.balance >= amount){
			this.balance = this.balance - amount;
			LOG.info("After withdraw the present balance of Account {} is {} ",accountNumber,balance);
			return true;
		}else{
			return false;
		}
	}

	public Integer getAccountNumber() {
		return accountNumber;
	}

	public Double getBalance() {
		return balance;
	}

	@Override
	public String toString() {
		return "Account [accountNumber=" + accountNumber + "]";
	}
	
	
}
