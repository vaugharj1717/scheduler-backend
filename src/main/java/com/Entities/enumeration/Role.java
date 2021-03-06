package com.Entities.enumeration;

public enum  Role {
    CANDIDATE,
    DEPARTMENT_ADMIN,
    PARTICIPANT,
    SCHEDULER,
    ADMIN,
    SUPER_ADMIN,
    NOTSET;

    public static Role getFromName(String name) {
        try {
            return Enum.valueOf(Role.class, name);
        } catch (Exception ex) {
            return Role.NOTSET;
        }
    }
}
