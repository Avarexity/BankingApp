package com.bankingapp.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Currency;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue(generator = "UUID")
    private final String id = UUID.randomUUID().toString();

    @Column(nullable = false)
    private final LocalDateTime timestamp = LocalDateTime.now();

    @Column(nullable = false)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    private final TransactionType type;

    @Enumerated(EnumType.STRING)
    private TransactionState state;

    @ManyToOne
    @JoinColumn(name = "card_id", nullable = true)
    private Card card;

    private final Account from;
    private Account to;
    private Institute institute;
    private final Currency currency;
    private String note;

    /**
     * No-arg constructor for JPA
     */
    protected Transaction() {}

    /**
     * Constructor for transfers
     *
     * @param from, the account the transfer was sent from
     * @param to, the recepient of the trnasfer
     * @param currency, the currency used
     * @param amount, the amount to be transferred
     * @param note, any notes
     */
    public Transaction(Account from, Account to, Currency currency,
                       BigDecimal amount, String note) {
        this.type = TransactionType.TRANSFER;
        this.from = Objects.requireNonNull(from);
        this.to = Objects.requireNonNull(to);
        this.currency = Objects.requireNonNull(currency);
        setAmount(amount);
        this.note = note;
        this.state = TransactionState.PENDING;
    }

    /**
     * Constructor for card payments
     *
     * @param from, the maker of the transaction
     * @param merchant, the institute to which the transaction was made
     * @param currency, the currency of the transaciton
     * @param amount, the amount of the transaction
     * @param note, any notes
     */
    public Transaction(Account from, Institute merchant, Currency currency,
                       BigDecimal amount, String note) {
        this.type = TransactionType.CARD_PAYMENT;
        this.from = Objects.requireNonNull(from);
        this.institute = Objects.requireNonNull(merchant);
        this.currency = Objects.requireNonNull(currency);
        setAmount(amount);
        this.note = note;
        this.state = TransactionState.PENDING;
    }

    // ------------ GETTERS ------------
    public String getId() { return id; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public TransactionType getType() { return  type; }
    public Account getSender() { return from; }
    public Account getRecipient() { return to; }
    public Institute getInstitute() { return institute; }
    public Currency getCurrency() { return currency; }
    public BigDecimal getAmount() { return amount; }
    public TransactionState getState() { return state; }
    public String getNote() { return note; }
    // ---------------------------------

    // ------------ SETTERS ------------
    public void setAmount(BigDecimal amount) {
        Objects.requireNonNull(amount, "Amount cannot be null.");
        if (amount.compareTo(BigDecimal.ZERO) >= 0) {
            this.amount = amount;
        } else throw new IllegalArgumentException("Amount must be positive.");
    }

    public void setState(TransactionState state) {
        Objects.requireNonNull(state, "Transaction state cannot be null.");
        this.state = state;
    }

    public void setNote(String note) {
        Objects.requireNonNull(note, "Transaction note cannot be null.");
        this.note = note;
    }
    // ---------------------------------

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder()
                .append("Transaction [")
                .append("ID: ").append(id)
                .append(" | Type: ").append(type)
                .append(" | Amount: ").append(currency.getSymbol())
                .append(amount.stripTrailingZeros().toPlainString())
                .append("]")
                .append("\nTime: ").append(timestamp.format(DateTimeFormatter.ISO_LOCAL_TIME));

        switch (type) {
            case TRANSFER:
                sb.append("\nFrom: ").append(from.getName())
                        .append("\nTo: ").append(to.getName());
                break;
            case CARD_PAYMENT:
                sb.append("\nAccount: ").append(from.getName())
                        .append("\nMerchant: ").append(institute.getName());
                break;
            case WITHDRAWAL:
                sb.append("\nFrom: ").append(from.getName())
                        .append("\nATM/Cash");
                break;
            case DEPOSIT:
                // 'To' is 'from' for deposit-type transactions. It's weird. It works.
                sb.append("\nTo: ").append(from.getName())
                        .append(" (Deposit)");
                break;
            case FEE:
                sb.append("\nCharged to: ").append(from.getName())
                        .append("\nReason: ").append(note);
                break;
            default:
                throw new UnsupportedOperationException("Transaction type not supported.");
        }

        sb.append("\nStatus: ").append(state);

        if (note != null && !note.isBlank()) {
            sb.append("\nNote: '").append(note).append("'");
        }

        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
