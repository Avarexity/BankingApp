import org.bankingapp.model.Account;
import org.bankingapp.model.DebitCard;
import org.bankingapp.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Currency;

import static org.junit.jupiter.api.Assertions.*;

class AccountTest {
    private Account account;
    private User owner;
    private static final Currency USD = Currency.getInstance("USD");
    
    @BeforeEach
    void setUp() {
        owner = new User(1, "John", "Doe", LocalDate.of(1990, 1, 1), 
                "john@example.com", "1234567890", "Password123!".toCharArray());
        account = new Account(1, "Main Account", USD, owner);
    }

    @Test
    void testInitialBalance() {
        assertEquals(BigDecimal.ZERO, account.getBalance());
    }

    @Test
    void testDeposit() {
        BigDecimal amount = new BigDecimal("100.00");
        account.deposit(amount);
        assertEquals(new BigDecimal("100.00"), account.getBalance());
    }

    @Test
    void testDepositNegativeAmount() {
        BigDecimal negativeAmount = new BigDecimal("-100.00");
        assertThrows(IllegalArgumentException.class, () -> account.deposit(negativeAmount));
    }

    @Test
    void testWithdrawSuccess() {
        account.deposit(new BigDecimal("100.00"));
        assertTrue(account.withdraw(new BigDecimal("50.00")));
        assertEquals(new BigDecimal("50.00"), account.getBalance());
    }

    @Test
    void testWithdrawInsufficientFunds() {
        account.deposit(new BigDecimal("50.00"));
        assertFalse(account.withdraw(new BigDecimal("100.00")));
        assertEquals(new BigDecimal("50.00"), account.getBalance());
    }

    @Test
    void testTransferMoney() {
        Account recipient = new Account(2, "Secondary Account", USD, owner);
        account.deposit(new BigDecimal("100.00"));
        account.transferMoney(recipient, new BigDecimal("50.00"));
        
        assertEquals(new BigDecimal("50.00"), account.getBalance());
        assertEquals(new BigDecimal("50.00"), recipient.getBalance());
    }

    @Test
    void testAddCard() {
        DebitCard card = new DebitCard("1234567890123456", LocalDate.now().plusYears(2),
                "123", account);
        assertTrue(account.addCard(card));
        assertTrue(account.getCards().contains(card));
    }

    @Test
    void testRemoveCard() {
        DebitCard card = new DebitCard("1234567890123456", LocalDate.now().plusYears(2), 
                "123", account);
        account.addCard(card);
        assertTrue(account.removeCard(card));
        assertFalse(account.getCards().contains(card));
    }
}