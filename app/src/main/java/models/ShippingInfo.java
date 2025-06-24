package models;

public class ShippingInfo {
    public String name;
    public String phone;
    public String email;
    public String address;
    public String ward;
    public String district;
    public String province;
    public String notes;

    public ShippingInfo() {
    }

    public ShippingInfo(String name, String phone, String email, String address, String ward, String district, String province, String notes) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.ward = ward;
        this.district = district;
        this.province = province;
        this.notes = notes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getWard() {
        return ward;
    }

    public void setWard(String ward) {
        this.ward = ward;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
