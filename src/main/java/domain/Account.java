package domain;

import exception.WithdrawException;
import service.DateService;
import service.Printing;

import java.util.ArrayList;
import java.util.List;

class Account {
	private long balancing;
	private final DateService dateService;

	private final List<AccountStatement> accountStatements = new ArrayList<>();

	public Account(long balancing, DateService dateService) {
		this.balancing = balancing;
		this.dateService = dateService;
	}

	long getBalancing() {
		return balancing;
	}

	void deposit(Amount amount) {
		createStatement(amount, OperationType.DEPOSIT);
		balancing += amount.getValue();
	}

	void withdraw(Amount amount) throws WithdrawException {
		if (isWithdrawNegativeResult(amount)) {
			throw new WithdrawException("You cannot withdraw an amount that you do not have on your account.");
		}
		createStatement(amount, OperationType.WITHDRAW);
		balancing -= amount.getValue();
	}

	private boolean isWithdrawNegativeResult(Amount amount) {
		return balancing - amount.getValue() < 0;
	}

	void printAllAccountStatement(Printing printer) {
		accountStatements.forEach(statement -> statement.print(printer));
	}

	private void createStatement(Amount amount, OperationType deposit) {
		AccountStatement statement = new AccountStatement.StatementBuilder().operationType(deposit).localDate(dateService.now())
				.amount(amount).balance(balancing).build();
		accountStatements.add(statement);
	}
}
