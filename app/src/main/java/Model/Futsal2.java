package Model;

public class Futsal2 {
    String NAME;
    Float LONGITUDE,LATITUDE;
    String DISTRICT;
    Integer PROVINCE;

    public Futsal2(String NAME, Float LONGITUDE, Float LATITUDE, String DISTRICT, Integer PROVINCE) {
        this.NAME = NAME;
        this.LONGITUDE = LONGITUDE;
        this.LATITUDE = LATITUDE;
        this.DISTRICT = DISTRICT;
        this.PROVINCE = PROVINCE;
    }

    public String getNAME() {
        return NAME;
    }

    public void setNAME(String NAME) {
        this.NAME = NAME;
    }

    public Float getLONGITUDE() {
        return LONGITUDE;
    }

    public void setLONGITUDE(Float LONGITUDE) {
        this.LONGITUDE = LONGITUDE;
    }

    public Float getLATITUDE() {
        return LATITUDE;
    }

    public void setLATITUDE(Float LATITUDE) {
        this.LATITUDE = LATITUDE;
    }

    public String getDISTRICT() {
        return DISTRICT;
    }

    public void setDISTRICT(String DISTRICT) {
        this.DISTRICT = DISTRICT;
    }

    public Integer getPROVINCE() {
        return PROVINCE;
    }

    public void setPROVINCE(Integer PROVINCE) {
        this.PROVINCE = PROVINCE;
    }
}
