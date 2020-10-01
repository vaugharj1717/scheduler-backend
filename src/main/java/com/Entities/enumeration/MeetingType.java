package com.Entities.enumeration;

public enum MeetingType {
    CANDIDATE,
    DEPARTMENT_ADMIN,
    NOTSET;

    public static MeetingType getFromName(String name) {
        try {
            return Enum.valueOf(MeetingType.class, name);
        } catch (Exception ex) {
            return MeetingType.NOTSET;
        }
    }
}