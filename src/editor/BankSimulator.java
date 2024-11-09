package editor;

import commands.*;


import bank.Account;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BankSimulator {
	private Account account;  
	private Account targetAccount;  
	private CommandHistory history;

	public BankSimulator(double initialBalance) {
		account = new Account(initialBalance);  
		targetAccount = new Account(0);  
		history = new CommandHistory();
	}


	public void displayTargetAccountBalance() {
		System.out.println("Saldo da conta de destino: " + targetAccount.getBalance());
	}

	public void init() {
		JFrame frame = new JFrame("Simulador bancario");
		JPanel content = new JPanel();
		frame.setContentPane(content);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));

		JLabel balanceLabel = new JLabel("Balance: " + account.getBalance());
		content.add(balanceLabel);


		JLabel targetBalanceLabel = new JLabel("Target Account Balance: " + targetAccount.getBalance());
		content.add(targetBalanceLabel);

		JPanel buttons = new JPanel(new FlowLayout(FlowLayout.CENTER));


		JButton depositButton = new JButton("Deposit");
		JButton withdrawButton = new JButton("Withdraw");
		JButton transferButton = new JButton("Transfer");
		JButton undoButton = new JButton("Undo"); 

		buttons.add(depositButton);
		buttons.add(withdrawButton);
		buttons.add(transferButton);
		buttons.add(undoButton);  
		content.add(buttons);


		depositButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				double amount = Double.parseDouble(JOptionPane.showInputDialog("Insira o valor de deposito:"));
				executeCommand(new DepositCommand(account, amount));


				balanceLabel.setText("Balance: " + account.getBalance());

				JOptionPane.showMessageDialog(frame, "Depositado " + amount + " em sua conta.");
			}
		});


		withdrawButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				double amount = Double.parseDouble(JOptionPane.showInputDialog("Insira o valor para saque:"));


				if (amount > account.getBalance()) {
					JOptionPane.showMessageDialog(frame, "Valor maior do que possui em conta");
				} else if (amount <= 0) {
					JOptionPane.showMessageDialog(frame, "Por favor, insira um valor válido para saque.");
				} else {
					executeCommand(new WithdrawCommand(account, amount));  
					balanceLabel.setText("Balance: " + account.getBalance());  
					JOptionPane.showMessageDialog(frame, "Withdrew " + amount + " De sua conta.");
				}
			}
		});


		transferButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				double amount = Double.parseDouble(JOptionPane.showInputDialog("Insira o valor para transferencia:"));
				executeCommand(new TransferCommand(account, targetAccount, amount));


				balanceLabel.setText("Balance: " + account.getBalance());


				targetBalanceLabel.setText("Saldo da outra conta: " + targetAccount.getBalance());

				JOptionPane.showMessageDialog(frame, "Transferido " + amount + " para outra conta.");
			}
		});


		undoButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				undo();
				balanceLabel.setText("Balance: " + account.getBalance());
				targetBalanceLabel.setText("Target Account Balance: " + targetAccount.getBalance());
				JOptionPane.showMessageDialog(frame, "Last operation undone.");
			}
		});

		frame.setSize(450, 200);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	private void executeCommand(Command command) {
		if (command.execute()) {
			history.push(command);
		}
	}

	private void undo() {
		if (!history.isEmpty()) {
			Command command = history.pop();
			command.undo();
		}
	}

	public static void main(String[] args) {
		BankSimulator simulator = new BankSimulator(1000);  
		simulator.init();
	}
}
