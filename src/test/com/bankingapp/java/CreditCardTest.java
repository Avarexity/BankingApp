import com.bankingapp.model.Account;
import com.bankingapp.model.CreditCard;
import com.bankingapp.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Currency;

import static org.junit.jupiter.api.Assertions.*;

class CreditCardTest {
    private CreditCard creditCard;
    private Account account;

    @BeforeEach
    void setUp() {
        User owner = new User(1, "John", "Doe", LocalDate.of(1990, 1, 1),
                "john@example.com", "1234567890", "Password123!".toCharArray());
        account = new Account(1, "Test Account", Currency.getInstance("USD"), owner);
        creditCard = new CreditCard("1234567890123456", LocalDate.now().plusYears(2),
                "123", account, "1234", new BigDecimal("1000.00"));
    }

    @Test
    void testGetType() {
        assertEquals("Credit", creditCard.getType());
    }

    @Test
    void testAuthorizePaymentWithinLimit() {
        assertTrue(creditCard.authorizePayment(new BigDecimal("500.00")));
        assertEquals(new BigDecimal("500.00"), creditCard.getCurrentDraw());
    }

    @Test
    void testAuthorizePaymentExceedingLimit() {
        assertFalse(creditCard.authorizePayment(new BigDecimal("1500.00")));
        assertEquals(BigDecimal.ZERO, creditCard.getCurrentDraw());
    }

    @Test
    void testMultiplePaymentsWithinLimit() {
        assertTrue(creditCard.authorizePayment(new BigDecimal("400.00")));
        assertTrue(creditCard.authorizePayment(new BigDecimal("300.00")));
        assertEquals(new BigDecimal("700.00"), creditCard.getCurrentDraw());
    }

    @Test
    void testNegativePayment() {
        assertFalse(creditCard.authorizePayment(new BigDecimal("-100.00")));
    }
}