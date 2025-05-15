package com.bankingapp.model;

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

    @Override
    public String toString() {
        return "Institute [ID: " + id + ", Name: " + name + ", Type: " + type + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Institute that = (Institute) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
