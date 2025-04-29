package org.bankingapp.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

/**
 * This class simulates a user of the banking app. Each user can have multiple accounts,
 * with the main identifiers of the account being the user's ID, email, and phone number.
 *
 * This class allows a user to:
 * - Validate emails, passwords, and phone numbers
 * - Add and remove accounts
 * - Validate access by answering safety questions
 * - Change their information
 *
 * This class manages individual members dealing with their banking info and accounts.
 * Useful for enforcement of restrictions and tracking statistics.
 *
 * @author Avarexity - Whard A.
 */
public class User {
    private final int id;
    private final String name;
    private final String surname;
    private final LocalDate dateOfBirth;
    private String email;
    private String phone;
    private char[] password;
    private final HashMap<String, String> questionsAndAnswers = new HashMap<>();
    private List<Account> accounts = new ArrayList<>();

    /**
     * Creates a loosely checked formatting to check if a string has a formatting similar to an email.
     * Formatting: {username}@{domain}.{host}.
     */
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",
            Pattern.CASE_INSENSITIVE
    );

    /**
     * Loosely creates a pattern for a phone number as a string, allowing international
     * number format and standard mobile format with a length between
     * 7 and 15 characters in length
     */
    private static final Pattern PHONE_PATTERN = Pattern.compile(
            "^(\\+\\d{1,3}[- ]?)?\\d{7,15}$"
    );

    /**
     * Creates a pattern for usable passwords that should be used, disallowing
     * weak passwords that do not contain at least one of each type of character
     * and passwords shorter than 8 characters
     */
    private static final Pattern PASSWORD_PATTERN = Pattern.compile(
            "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[^a-zA-Z0-9]).{8,}$"
    );

    // Password Validator
    public static boolean isValidPassword(String password) {
        return PASSWORD_PATTERN.matcher(password).matches();
    }

    // Email Validator
    public static boolean isValidEmail(String email) {
        return EMAIL_PATTERN.matcher(email).matches();
    }

    // Phone validator
    public static boolean isValidPhone(String phone) {
        return PHONE_PATTERN.matcher(phone).matches();
    }

    // Initializer
    public User(int id, String name, String surname, LocalDate dateOfBirth, String email, String phone, char[] password) {
        if (!isValidEmail(email)) {
            throw new IllegalArgumentException("Invalid email format");
        }
        if (!isValidPhone(phone)) {
            throw new IllegalArgumentException("Invalid phone number format");
        }
        if (!isValidPassword(new String(password))) {
            throw new IllegalArgumentException("Password must be at least 8 characters long and include at least" +
                    " one uppercase letter, one lowercase letter, one number, and one special character.");
        }

        this.id = id;
        this.name = name;
        this.surname = surname;
        this.dateOfBirth = dateOfBirth;
        this.email = email;
        this.phone = phone;
        this.password = password;
    }

    // ------------ Getters ------------
    public int getId() { return id; }
    public String getName() { return name; }
    public String getSurname() { return surname; }
    public LocalDate getDateOfBirth() { return dateOfBirth; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }
    public String getPassword() { return new String(password); }
    public HashMap<String, String> getQuestionsAndAnswers() { return questionsAndAnswers; }
    public List<Account> getAccounts() { return accounts; }
    // ---------------------------------

    // ------------ Setters -------------
    public void setEmail(String email) {
        if (!isValidEmail(email)) {
            throw new IllegalArgumentException("Invalid email format");
        }
        this.email = email;
    }

    public void setPhone(String phone) {
        if (!isValidPhone(phone)) {
            throw new IllegalArgumentException("Invalid phone number format");
        }
        this.phone = phone;
    }

    public void setPassword(char[] password) {
        if (!isValidPassword(new String(password))) {
            throw new IllegalArgumentException("Password must be at least 8 characters long and include" +
                    " at least one uppercase letter, one lowercase letter, one number, and one special character.");
        }
        this.password = password;
    }

    public void setAccounts(List<Account> accounts) { this.accounts = accounts; }
    // ---------------------------------

    /**
     * This function takes a question given to a user and the user's answer in order
     * to add them as security questions to help validate who's using the account.
     *
     * @param question, the question asked by the bank
     * @param answer, the answer given by the user
     */
    public void addQAndA(String question, String answer) {
        questionsAndAnswers.put(question, answer);
    }

    /**
     * Adds an account to the user's list of accounts if they wish to add an account.
     *
     * @param account, the account to be added
     * @return true - if the account was created successfully,
     * false - if the account was not created successfully
     */
    public boolean addAccount(Account account) {
        return accounts.add(account);
    }

    /**
     * Removes an account from the user if they wish to delete an account.
     *
     * @param account, the account to be removed
     * @return true - if the account was found
     * false - if they didn't have such account
     */
    public boolean removeAccount(Account account) {
        return accounts.remove(account);
    }
}
