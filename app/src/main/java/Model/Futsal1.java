package Model;

public class Futsal1 {
    String name;
    Float longitude,latitude;
    String district;
    Integer province;

    public Futsal1(String name, Float longitude, Float latitude, String district, Integer province) {
        this.name = name;
        this.longitude = longitude;
        this.latitude = latitude;
        this.district = district;
        this.province = province;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getLongitude() {
        return longitude;
    }

    public void setLongitude(Float longitude) {
        this.longitude = longitude;
    }

    public Float getLatitude() {
        return latitude;
    }

    public void setLatitude(Float latitude) {
        this.latitude = latitude;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public int getProvince() {
        return province;
    }

    public void setProvince(Integer province) {
        this.province = province;
    }
}
