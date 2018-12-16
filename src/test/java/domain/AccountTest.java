package domain;

import exception.WithdrawException;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import service.DateService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@RunWith(MockitoJUnitRunner.class)
public class AccountTest {

    private Account account;

    @Mock
    private DateService dateService;

    @Before
    public void setUp() {
        account = new Account(0, dateService);
    }

    @Test
    public void add_0_to_account() {
        account.deposit(new Amount(0));

        Assertions.assertThat(account.getBalancing()).isEqualTo(0);
    }

    @Test
    public void add_100_to_account() {
        account.deposit(new Amount(100));

        Assertions.assertThat(account.getBalancing()).isEqualTo(100);
    }

    @Test
    public void add_100_twice_to_account() {
        account.deposit(new Amount(100));
        account.deposit(new Amount(100));

        Assertions.assertThat(account.getBalancing()).isEqualTo(200);
    }

    @Test
    public void unauthorize_deposit_negative_value() {
        assertThatThrownBy(() -> account.deposit(new Amount(-10))).isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("You cannot withdraw or deposit a negative amount.");
    }


    @Test
    public void withdraw_0_from_the_account() throws WithdrawException {
        account.withdraw(new Amount(0));

        Assertions.assertThat(account.getBalancing()).isEqualTo(0);
    }

    @Test
    public void withdraw_50_from_the_account() throws WithdrawException {
        account = new Account(100, dateService);
        account.withdraw(new Amount(50));

        Assertions.assertThat(account.getBalancing()).isEqualTo(50);
    }

    @Test
    public void withdraw_50_twice_from_the_account() throws WithdrawException {
        account = new Account(100, dateService);
        account.withdraw(new Amount(50));
        account.withdraw(new Amount(50));

        Assertions.assertThat(account.getBalancing()).isEqualTo(0);
    }

    @Test
    public void unauthorize_withdraw_negative_value() {
        assertThatThrownBy(() -> account.withdraw(new Amount(-10))).isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("You cannot withdraw or deposit a negative amount.");
    }

    @Test
    public void unauthorize_withdrawal_an_amount_which_is_not_present_in_an_account() {
        assertThatThrownBy(() -> account.withdraw(new Amount(10))).isInstanceOf(WithdrawException.class)
                .hasMessageContaining("You cannot withdraw an amount that you do not have on your account.");
    }

    @Test
    public void get_precision_for_withdrawal() throws WithdrawException {
        account = new Account(103, dateService);
        account.withdraw(new Amount(42));

        assertThat(account.getBalancing()).isEqualTo(61);
    }
}