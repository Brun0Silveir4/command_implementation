package commands;

import bank.Account;

public class WithdrawCommand extends Command {
	public WithdrawCommand(Account account, double amount) {
		super(account, amount);
	}

	@Override
	public boolean execute() {
		if (account.getBalance() >= amount && amount > 0) {
			backup();
			account.withdraw(amount); 
			System.out.println("Saque de " + amount + " realizado da conta.");
			return true;
		}
		System.out.println("Saldo insuficiente ou valor inválido.");
		return false;
	}
}

