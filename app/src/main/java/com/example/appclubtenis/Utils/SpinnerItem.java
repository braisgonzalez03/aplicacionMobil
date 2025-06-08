package com.example.appclubtenis.Utils;

public class SpinnerItem {

    private String name;
    private int iconResId;
    private String languageCode;

    public SpinnerItem(String name, int iconResId, String languageCode) {
        this.name = name;
        this.iconResId = iconResId;
        this.languageCode = languageCode;
    }

    public String getName() {
        return name;
    }

    public int getIconResId() {
        return iconResId;
    }

    public String getLanguageCode() {
        return languageCode;
    }
}
