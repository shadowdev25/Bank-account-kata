package domain;

import exception.WithdrawException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import service.DateService;
import service.Printing;

import java.time.LocalDate;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AccountStatementTest {

    @Mock
    private Printing printer;

    @Mock
    private DateService dateService;

    @Test
    public void print_account_statement_for_deposit() {
        when(dateService.now()).thenReturn(LocalDate.of(2018, 12, 15));
        Amount amount = new Amount(500);
        int balance = 0;

        AccountStatement accountStatement = new AccountStatement.StatementBuilder()
                .operationType(OperationType.DEPOSIT)
                .localDate(dateService.now())
                .amount(amount)
                .balance(balance)
                .build();

        accountStatement.print(printer);
        Mockito.verify(printer).print("Deposit | 2018-12-15 | 500 | 0");
    }

    @Test
    public void print_account_statement_for_withdrawal() {
        when(dateService.now()).thenReturn(LocalDate.of(2018, 12, 15));
        Amount amount = new Amount(300);
        int balance = 0;

        AccountStatement accountStatement = new AccountStatement.StatementBuilder()
                .operationType(OperationType.WITHDRAW)
                .localDate(dateService.now())
                .amount(amount)
                .balance(balance)
                .build();

        accountStatement.print(printer);
        Mockito.verify(printer).print("Withdraw | 2018-12-15 | 300 | 0");
    }

    @Test
    public void print_all_account_statement() throws WithdrawException {
        when(dateService.now()).thenReturn(
                LocalDate.of(2018, 12, 15),
                LocalDate.of(2018, 12, 16),
                LocalDate.of(2018, 12, 17));

        Account account = new Account(0, dateService);

        account.deposit(new Amount(1000));
        account.withdraw(new Amount(300));
        account.deposit(new Amount(200));

        account.printAllAccountStatement(printer);

        Mockito.verify(printer).print("Deposit | 2018-12-15 | 1000 | 0");
        Mockito.verify(printer).print("Withdraw | 2018-12-16 | 300 | 1000");
        Mockito.verify(printer).print("Deposit | 2018-12-17 | 200 | 700");
    }
}