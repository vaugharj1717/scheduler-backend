package com.Entities.enumeration;

public enum MeetingType {
    PRESENTATION,
    MEET_FACULTY,
    NOTSET;

    public static MeetingType getFromName(String name) {
        try {
            return Enum.valueOf(MeetingType.class, name);
        } catch (Exception ex) {
            return MeetingType.NOTSET;
        }
    }
}