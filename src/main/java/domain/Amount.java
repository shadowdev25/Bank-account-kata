package domain;

class Amount {
    private final long value;

    Amount(long value) {
        if (value < 0) {
            throw new IllegalArgumentException("You cannot withdraw or deposit a negative amount.");
        }
        this.value = value;
    }

    long getValue() {
        return value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
