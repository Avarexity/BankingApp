package com.bankingapp.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.*;
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
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private final Long id;

    @Column(nullable = false)
    private final String name;

    @Column(nullable = false)
    private final String surname;

    @Column(nullable = false, columnDefinition = "DATE")
    private final LocalDate dateOfBirth;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String phone;

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
    private List<Account> accounts = new ArrayList<>();

    private char[] password;

    @ElementCollection
    @CollectionTable(name = "user_security_questions", joinColumns = @JoinColumn(name = "user_id"))
    @MapKeyColumn(name = "question")
    @Column(name = "answer")
    private final Map<String, String> questionsAndAnswers = new HashMap<>();

    /**
     * Creates a loosely checked formatting to check if a string has a formatting similar to an email.
     * Formatting: {username}@{domain}.{host}.
     */
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",
            Pattern.CASE_INSENSITIVE
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

    /**
     * Validates if a phone number is correct.
     *
     * @param phone, the phone number
     * @return Whether the number is valid
     */
    public static boolean isValidPhone(String phone) {
        String digits = phone.replaceAll("\\D", "");
        return digits.length() >= 7 && digits.length() <= 15;
    }

    /**
     * No-arg constructor for JPA
     */
    protected User() {}

    /**
     * Constructs a new user.
     *
     * @param id, the ID of the user
     * @param name, the first name of the user
     * @param surname, the surname of the user
     * @param dateOfBirth, the DOB of the user
     * @param email, the email of the user
     * @param phone, the phone number of the user
     * @param password, the password of the user
     */
    public User(Long id, String name, String surname, LocalDate dateOfBirth, String email, String phone, char[] password) {
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
    public Long getId() { return id; }
    public String getName() { return name; }
    public String getSurname() { return surname; }
    public LocalDate getDateOfBirth() { return dateOfBirth; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }
    public String getPassword() { return new String(password); }
    public Map<String, String> getQuestionsAndAnswers() { return questionsAndAnswers; }
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
        return accounts.add(account) && account.setOwner(this);
    }

    /**
     * Removes an account from the user if they wish to delete an account.
     *
     * @param account, the account to be removed
     * @return true - if the account was found
     * false - if they didn't have such account
     */
    public boolean removeAccount(Account account) {
        return accounts.remove(account) && account.setOwner(null);
    }

    @Override
    public String toString() {
        return String.format("User ID: %d, Name: %s %s | Email: %s | Phone: %s",
                id,
                name,
                surname,
                email,
                phone);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id.equals(user.id) && email.equals(user.email) && phone.equals(user.phone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, phone);
    }
}
