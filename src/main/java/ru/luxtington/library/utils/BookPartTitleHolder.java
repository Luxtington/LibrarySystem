package ru.luxtington.library.utils;

public class BookPartTitleHolder {
    private String partOfBookTitle;

    public BookPartTitleHolder(String partOfBookTitle) {
        this.partOfBookTitle = partOfBookTitle;
    }

    public BookPartTitleHolder() {
    }

    public String getPartOfBookTitle() {
        return partOfBookTitle;
    }

    public void setPartOfBookTitle(String partOfBookTitle) {
        this.partOfBookTitle = partOfBookTitle;
    }

    @Override
    public String toString() {
        return "BookPartTitleHolder{" +
                "partOfBookTitle='" + partOfBookTitle + '\'' +
                '}';
    }
}
