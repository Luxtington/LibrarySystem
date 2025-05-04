package ru.luxtington.library.utils;

public class PersonInitialsHolder {

    private String initialsPart;

    public PersonInitialsHolder() {
    }

    public PersonInitialsHolder(String initialsPart) {
        this.initialsPart = initialsPart;
    }

    public String getInitialsPart() {
        return initialsPart;
    }

    public void setInitialsPart(String initialsPart) {
        this.initialsPart = initialsPart;
    }

    @Override
    public String toString() {
        return "PersonInitialsHolder{" +
                "initialsPart='" + initialsPart + '\'' +
                '}';
    }
}
