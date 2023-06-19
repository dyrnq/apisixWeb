package enumeration;

public enum Challenge {
    http(80, "HTTP-01"),
    dns(53, "DNS-01");

    private final String name;
    private final int id;

    Challenge(int id, String name) {
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
