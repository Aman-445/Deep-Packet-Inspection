package model;

public enum AppType {
    UNKNOWN, HTTP, HTTPS, DNS, YOUTUBE, FACEBOOK;

    @Override
    public String toString() {
        return name();
    }
}