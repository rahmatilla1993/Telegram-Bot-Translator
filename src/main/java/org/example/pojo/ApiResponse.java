package org.example.pojo;

import com.google.gson.annotations.SerializedName;

public class ApiResponse {
    @SerializedName("data")
    private Translations data;

    public Translations getData() {
        return data;
    }

    public void setData(Translations data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ApiResponse{" +
                "data=" + data +
                '}';
    }
}
