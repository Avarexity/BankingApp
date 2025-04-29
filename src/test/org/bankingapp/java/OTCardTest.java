import org.bankingapp.model.Account;
import org.bankingapp.model.OTCard;
import org.bankingapp.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Currency;

import static org.junit.jupiter.api.Assertions.*;

class OTCardTest {
    private OTCard otCard;
    private Account account;

    @BeforeEach
    void setUp() {
        User owner = new User(1, "John", "Doe", LocalDate.of(1990, 1, 1),
                "john@example.com", "1234567890", "Password123!".toCharArray());
        account = new Account(1, "Test Account", Currency.getInstance("USD"), owner);
        otCard = new OTCard("1234567890123456", LocalDate.now().plusYears(2),
                "123", account, "1234", new BigDecimal("1000.00"));
    }

    @Test
    void testGetType() {
        assertEquals("One-Time Use", otCard.getType());
    }

    @Test
    void testInitialState() {
        assertFalse(otCard.isUsed());
        assertTrue(otCard.isOT());
    }

    @Test
    void testAuthorizePaymentWhenNotUsed() {
        account.deposit(new BigDecimal("500.00"));
        assertTrue(otCard.authorizePayment(new BigDecimal("300.00")));
        assertEquals(new BigDecimal("300.00"), otCard.getCurrentDraw());
    }

    @Test
    void testAuthorizePaymentAfterUse() {
        account.deposit(new BigDecimal("500.00"));
        otCard.use();
        assertFalse(otCard.authorizePayment(new BigDecimal("300.00")));
        assertEquals(BigDecimal.ZERO, otCard.getCurrentDraw());
    }

    @Test
    void testAuthorizePaymentWithInsufficientFunds() {
        account.deposit(new BigDecimal("200.00"));
        assertFalse(otCard.authorizePayment(new BigDecimal("300.00")));
    }

    @Test
    void testUseCard() {
        otCard.use();
        assertTrue(otCard.isUsed());
    }
}