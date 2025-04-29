package org.bankingapp.model;

import java.util.Objects;
import java.util.UUID;

public class Institute {
    private final String id = UUID.randomUUID().toString();
    private final String name;
    private final InstituteType type; // Enum: BANK, MERCHANT, etc.

    public Institute(String name, InstituteType type) {
        this.name = Objects.requireNonNull(name);
        this.type = Objects.requireNonNull(type);
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public InstituteType getType() { return type; }
}
