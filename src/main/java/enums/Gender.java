package enums;

public enum Gender {
    MALE("M"),
    FEMALE("F");

    public final String db;

    Gender(String db) {
        this.db = db;
    }

    public static Gender of(String db) {
        if (db == null) {
            return null;
        }
        for (Gender gender : values()) {
            if (gender.db.equals(db)) {
                return gender;
            }
        }
        throw new IllegalArgumentException("No Gender is defined for db-value " + db);
    }
}
