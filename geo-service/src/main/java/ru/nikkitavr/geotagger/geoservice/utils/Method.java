package ru.nikkitavr.geotagger.geoservice.utils;

public enum Method {
    START, STOP, POST;

    public static Method getCommandFromString(String command){
        for (Method e : Method.values()) {
            if (e.name().equalsIgnoreCase(command)) {
                return e;
            }
        }
        throw new IllegalArgumentException("No command found with name'" + command + "'");
    }

    @Override
    public String toString() {
        return this.name();
    }
}
