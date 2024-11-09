package commands;

import bank.Account;

public class TransferCommand extends Command {
	private Account targetAccount; 

	public TransferCommand(Account account, Account targetAccount, double amount) {
		super(account, amount);
		this.targetAccount = targetAccount;
	}

	@Override
	public boolean execute() {
		if (account.getBalance() >= amount && amount > 0) {
			backup();
			account.withdraw(amount);  
			targetAccount.deposit(amount);  
			System.out.println("Transferência de " + amount + " realizada.");
			return true;
		}
		System.out.println("Saldo insuficiente ou valor inválido.");
		return false;
	}

	@Override
	public void undo() {
		super.undo(); 
		targetAccount.withdraw(amount); 
		account.deposit(amount); 
		System.out.println("Transferência desfeita.");
	}
}
