import org.bankingapp.model.Account;
import org.bankingapp.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.util.Currency;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {
    private User user;

    @BeforeEach
    void setUp() {
        user = new User(1, "John", "Doe", LocalDate.of(1990, 1, 1),
                "john@example.com", "1234567890", "Password123!".toCharArray());
    }

    @Test
    void testValidEmail() {
        assertTrue(User.isValidEmail("test@example.com"));
        assertFalse(User.isValidEmail("invalid-email"));
    }

    @Test
    void testValidPhone() {
        assertTrue(User.isValidPhone("1234567890"));
        assertTrue(User.isValidPhone("+1-234-567-8900"));
        assertFalse(User.isValidPhone("123"));
    }

    @Test
    void testValidPassword() {
        assertTrue(User.isValidPassword("Password123!"));
        assertFalse(User.isValidPassword("weak"));
    }

    @Test
    void testAddAccount() {
        Account account = new Account(1, "Test Account", Currency.getInstance("USD"), user);
        assertTrue(user.addAccount(account));
        assertTrue(user.getAccounts().contains(account));
    }

    @Test
    void testRemoveAccount() {
        Account account = new Account(1, "Test Account", Currency.getInstance("USD"), user);
        user.addAccount(account);
        assertTrue(user.removeAccount(account));
        assertFalse(user.getAccounts().contains(account));
    }

    @Test
    void testAddQAndA() {
        user.addQAndA("What is your pet's name?", "Rex");
        assertEquals("Rex", user.getQuestionsAndAnswers().get("What is your pet's name?"));
    }

    @Test
    void testSetInvalidEmail() {
        assertThrows(IllegalArgumentException.class, () -> user.setEmail("invalid-email"));
    }

    @Test
    void testSetInvalidPhone() {
        assertThrows(IllegalArgumentException.class, () -> user.setPhone("123"));
    }

    @Test
    void testSetInvalidPassword() {
        assertThrows(IllegalArgumentException.class, () -> user.setPassword("weak".toCharArray()));
    }
}