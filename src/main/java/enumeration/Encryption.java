package enumeration;

public enum Encryption {
    RSA(1, "RSA"),
    ECC(2, "ECC");

    private final String name;
    private final int id;

    Encryption(int id, String name) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }
}
