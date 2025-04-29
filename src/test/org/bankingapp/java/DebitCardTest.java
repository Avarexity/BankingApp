import org.bankingapp.model.Account;
import org.bankingapp.model.DebitCard;
import org.bankingapp.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Currency;

import static org.junit.jupiter.api.Assertions.*;

class DebitCardTest {
    private DebitCard debitCard;
    private Account account;

    @BeforeEach
    void setUp() {
        User owner = new User(1, "John", "Doe", LocalDate.of(1990, 1, 1),
                "john@example.com", "1234567890", "Password123!".toCharArray());
        account = new Account(1, "Test Account", Currency.getInstance("USD"), owner);
        debitCard = new DebitCard("1234567890123456", LocalDate.now().plusYears(2),
                "123", account, "1234", new BigDecimal("1000.00"));
    }

    @Test
    void testGetType() {
        assertEquals("Debit", debitCard.getType());
    }

    @Test
    void testAuthorizePaymentWithSufficientFunds() {
        account.deposit(new BigDecimal("500.00"));
        assertTrue(debitCard.authorizePayment(new BigDecimal("300.00")));
        assertEquals(new BigDecimal("300.00"), debitCard.getCurrentDraw());
    }

    @Test
    void testAuthorizePaymentWithInsufficientFunds() {
        account.deposit(new BigDecimal("200.00"));
        assertFalse(debitCard.authorizePayment(new BigDecimal("300.00")));
        assertEquals(BigDecimal.ZERO, debitCard.getCurrentDraw());
    }

    @Test
    void testAuthorizePaymentExceedingDrawLimit() {
        account.deposit(new BigDecimal("2000.00"));
        assertFalse(debitCard.authorizePayment(new BigDecimal("1500.00")));
    }

    @Test
    void testMultiplePaymentsWithinLimit() {
        account.deposit(new BigDecimal("1000.00"));
        assertTrue(debitCard.authorizePayment(new BigDecimal("300.00")));
        assertTrue(debitCard.authorizePayment(new BigDecimal("200.00")));
        assertEquals(new BigDecimal("500.00"), debitCard.getCurrentDraw());
    }
}