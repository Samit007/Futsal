package com.example.mylocation;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
public class TraningCentre {

    @SerializedName("ID")
    @Expose
    private String iD;
    @SerializedName("NAME")
    @Expose
    private String nAME;
    @SerializedName("LONGITUDE")
    @Expose
    private String lONGITUDE;
    @SerializedName("LATITUDE")
    @Expose
    private String lATITUDE;

    public Integer getiD() {
        return Integer.parseInt(iD);
    }

    public String getnAME() {

        return nAME;
    }

    public Double getlONGITUDE() {
        return Double.parseDouble(lONGITUDE);
    }

    public Double getlATITUDE() {
        return Double.parseDouble(lATITUDE);
    }

    public void setiD(Integer iD) {
        this.iD = String.valueOf(iD);
    }

    public void setnAME(String nAME) {
        this.nAME = nAME;
    }

    public void setlONGITUDE(Double lONGITUDE) {
        this.lONGITUDE = String.valueOf(lONGITUDE);
    }

    public void setlATITUDE(Double lATITUDE) {
        this.lATITUDE = String.valueOf(lATITUDE);
    }
}
