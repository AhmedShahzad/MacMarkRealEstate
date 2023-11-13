package com.example.response;

import com.example.item.Category;
import com.example.item.Detail;
import com.example.item.Gallery;
import com.example.item.Property;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class DetailRP implements Serializable {

    @SerializedName("REAL_ESTATE_APP")
    @Expose
    private Detail detailList;
    @SerializedName("status_code")
    @Expose
    private Integer statusCode;

    public Integer getStatusCode() {
        return statusCode;
    }

    public Detail getDetailList() {
        return detailList;
    }
}
