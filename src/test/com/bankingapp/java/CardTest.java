import com.bankingapp.model.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Currency;

import static org.junit.jupiter.api.Assertions.*;

class CardTest {
    private Account account;
    private DebitCard card; // Using DebitCard as concrete implementation for testing

    @BeforeEach
    void setUp() {
        User owner = new User(1, "John", "Doe", LocalDate.of(1990, 1, 1),
                "john@example.com", "1234567890", "Password123!".toCharArray());
        account = new Account(1, "Test Account", Currency.getInstance("USD"), owner);
        card = new DebitCard("1234567890123456", LocalDate.now().plusYears(2),
                "123", account, "1234", new BigDecimal("1000.00"));
    }

    @Test
    void testValidPin() {
        Assertions.assertTrue(Card.isValidPin("1234"));
        assertTrue(Card.isValidPin("123456"));
        assertFalse(Card.isValidPin("12"));
        assertFalse(Card.isValidPin("1234567"));
    }

    @Test
    void testSetPin() {
        card.setPin("5678");
        assertEquals("5678", card.getPin());
    }

    @Test
    void testSetInvalidPin() {
        assertThrows(IllegalArgumentException.class, () -> card.setPin("12"));
    }

    @Test
    void testSetDrawLimit() {
        BigDecimal newLimit = new BigDecimal("2000.00");
        card.setDrawLimit(newLimit);
        assertEquals(newLimit, card.getDrawLimit());
    }

    @Test
    void testSetNegativeDrawLimit() {
        assertThrows(IllegalArgumentException.class,
                () -> card.setDrawLimit(new BigDecimal("-1000.00")));
    }

    @Test
    void testMakeOT() {
        card.makeOT();
        assertTrue(card.isOT());
        assertNotNull(card.getOTCard());
    }

    @Test
    void testMultipleMakeOTCalls() {
        card.makeOT();
        OTCard firstOTCard = card.getOTCard();
        card.makeOT();
        assertEquals(firstOTCard, card.getOTCard());
    }
}