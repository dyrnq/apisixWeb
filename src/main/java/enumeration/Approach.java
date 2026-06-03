package enumeration;

public enum Approach {
    trustCA(1, "Trusted CA"),
    manual(2, "手工上传"),
    privateCA(3, "Private CA(自签)");

    private final String name;
    private final int id;

    Approach(int id, String name) {
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
