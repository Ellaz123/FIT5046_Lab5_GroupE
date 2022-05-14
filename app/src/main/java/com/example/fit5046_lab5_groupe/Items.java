package com.example.fit5046_lab5_groupe;


import com.google.gson.annotations.SerializedName;

public class Items {
    @SerializedName("snippet")
    public String snippet;
    public String getSnippet() {
        return snippet;
    }
    public void setSnippet(String snippet) {
        this.snippet = snippet;
    } }