package domain;

import lombok.Builder;
import service.Printing;

import java.time.LocalDate;

@Builder
public class AccountStatement {
    private final OperationType operationType;
    private final LocalDate localDate;
    private final Amount amount;
    private final long balance;

    public AccountStatement(OperationType operationType, LocalDate localDate, Amount amount, long balance) {
        this.operationType = operationType;
        this.localDate = localDate;
        this.amount = amount;
        this.balance = balance;
    }

    void print(Printing printString) {
        printString.print(this.toString());
    }

    @Override
    public String toString() {
        return operationType + " | " + localDate + " | " + amount + " | " + balance;
    }
}