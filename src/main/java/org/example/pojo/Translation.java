package org.example.pojo;

import com.google.gson.annotations.SerializedName;

public class Translation {
    @SerializedName("translatedText")
    private String translatedText;

    public String getTranslatedText() {
        return translatedText;
    }
    public void setTranslatedText(String translatedText) {
        this.translatedText = translatedText;
    }

    @Override
    public String toString() {
        return "Translation{" +
                "translatedText='" + translatedText + '\'' +
                '}';
    }
}
