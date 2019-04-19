package fr.destiny.vacuum.web.utils;

public enum ClassType {

    TITAN(0),
    HUNTER(1),
    WARLOCK(2),
    ANY(3);

    private int hash;

    ClassType(int hash) {
        this.hash = hash;
    }

    public int getHash() {
        return hash;
    }

    public static ClassType fromHash(int hash) {
        for (ClassType classType : ClassType.values()) {
            if (classType.hash == hash) {
                return classType;
            }
        }
        return null;
    }
}
