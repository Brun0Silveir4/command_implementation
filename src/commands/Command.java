package commands;

import bank.Account;

public abstract class Command {
	protected Account account;
	protected double amount;
	private double previousBalance; 

	public Command(Account account, double amount) {
		this.account = account;
		this.amount = amount;
	}

	public void backup() {
		previousBalance = account.getBalance();  
	}

	public void undo() {
		account.setBalance(previousBalance); 
	}

	public abstract boolean execute();
}
