package enumeration;

public enum Supplier {
    acme(1, "ACME(Let's Encrypt)"),
    ali(2, "Cloud(ali)"),
    tencent(3, "Cloud(tencent)");

    private final String name;
    private final int id;

    Supplier(int id, String name) {
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
