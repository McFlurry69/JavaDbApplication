package domain;

import java.util.Date;

public class Vehicle {
    private Integer id;
    private String carModel;
    private Date ManufactureDate;
    private Integer UserId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCarModel() {
        return carModel;
    }

    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }

    public Date getManufactureDate() {
        return ManufactureDate;
    }

    public void setManufactureDate(Date manufactureDate) {
        ManufactureDate = manufactureDate;
    }

    public Integer getUserId() {
        return UserId;
    }

    public void setUserId(Integer userId) {
        UserId = userId;
    }
}
