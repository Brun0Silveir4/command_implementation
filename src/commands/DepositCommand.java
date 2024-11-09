package commands;

import bank.Account;

public class DepositCommand extends Command {
	public DepositCommand(Account account, double amount) {
		super(account, amount);
	}

	@Override
	public boolean execute() {
		if (amount > 0) {
			backup();
			account.deposit(amount);  
			System.out.println("Depósito de " + amount + " realizado na conta.");
			return true;
		}
		return false;  
	}
}

