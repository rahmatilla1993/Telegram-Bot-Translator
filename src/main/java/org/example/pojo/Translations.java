package org.example.pojo;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Translations {
    @SerializedName("translations")
    private List<Translation> translations;

    public List<Translation> getTranslations() {
        return translations;
    }

    public void setTranslations(List<Translation> translations) {
        this.translations = translations;
    }

    @Override
    public String toString() {
        return "Translations{" +
                "translations=" + translations +
                '}';
    }
}
