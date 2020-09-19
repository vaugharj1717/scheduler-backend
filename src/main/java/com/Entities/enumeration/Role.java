package com.Entities.enumeration;

public enum  Role {
    CANDIDATE,
    DEPRATEMENT_ADMIN,
    NOTSET;

    public static Role getFromName(String name) {
        try {
            return Enum.valueOf(Role.class, name);
        } catch (Exception ex) {
            return Role.NOTSET;
        }
    }
}
