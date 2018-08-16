package com.mytaxi.domainobject;

import java.time.ZonedDateTime;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import com.mytaxi.domainvalue.EngineType;

@Entity
@Table(
    name = "car",
    uniqueConstraints = @UniqueConstraint(name = "uc_licenseplate", columnNames = {"license_plate"}))
public class CarDO
{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private ZonedDateTime dateCreated = ZonedDateTime.now();

    @Column
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private ZonedDateTime dateUpdated;

    @Column(nullable = false, name = "license_plate")
    @NotNull(message = "License plate can't be null!")
    private String licensePlate;

    @Column(nullable = false, name = "seat_count")
    @NotNull(message = "Seat count can not be null!")
    private Integer seatCount;

    @Column(nullable = false)
    private Boolean convertible = false;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "engine_type")
    private EngineType engineType;

    @Column(nullable = false, name = "rating")
    private Float rating;

    @NotNull(message = "Manufacturer can not be null")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manufacturer", nullable = false)
    private ManufacturerDO manufacturerDO;

    @Column(nullable = false)
    private Boolean deleted = false;


    public CarDO()
    {}


    public CarDO(
        String licensePlate, Integer seatCount, Boolean convertible,
        EngineType engineType, Float rating, ManufacturerDO manufacturerDO, Boolean deleted)
    {
        this.licensePlate = licensePlate;
        this.seatCount = seatCount;
        this.convertible = convertible;
        this.engineType = engineType;
        this.rating = rating;
        this.manufacturerDO = manufacturerDO;
        this.deleted = deleted;
    }


    public CarDO(String licensePlate, Integer seatCount, Boolean convertible, EngineType engineType, ManufacturerDO manufacturerDO)
    {
        this.licensePlate = licensePlate;
        this.seatCount = seatCount;
        this.convertible = convertible;
        this.engineType = engineType;
        this.rating = 0f;
        this.manufacturerDO = manufacturerDO;
        this.deleted = false;
    }


    public Long getId()
    {
        return id;
    }


    public void setId(Long id)
    {
        this.id = id;
    }


    public ZonedDateTime getDateUpdated()
    {
        return dateUpdated;
    }


    public void setDateUpdated(ZonedDateTime dateUpdated)
    {
        this.dateUpdated = dateUpdated;
    }


    public String getLicensePlate()
    {
        return licensePlate;
    }


    public void setLicensePlate(String licensePlate)
    {
        this.licensePlate = licensePlate;
    }


    public Integer getSeatCount()
    {
        return seatCount;
    }


    public void setSeatCount(Integer seatCount)
    {
        this.seatCount = seatCount;
    }


    public Boolean getConvertible()
    {
        return convertible;
    }


    public void setConvertible(Boolean convertible)
    {
        this.convertible = convertible;
    }


    public EngineType getEngineType()
    {
        return engineType;
    }


    public void setEngineType(EngineType engineType)
    {
        this.engineType = engineType;
    }


    public Float getRating()
    {
        return rating;
    }


    public void setRating(Float rating)
    {
        this.rating = rating;
    }


    public ManufacturerDO getManufacturerDO()
    {
        return manufacturerDO;
    }


    public void setManufacturer(ManufacturerDO manufacturerDO)
    {
        this.manufacturerDO = manufacturerDO;
    }


    public Boolean getDeleted()
    {
        return deleted;
    }


    public void setDeleted(Boolean deleted)
    {
        this.deleted = deleted;
    }


    public boolean equals(Object object)
    {
        if (this == object)
            return true;
        if (!(object instanceof CarDO))
            return false;
        CarDO car = (CarDO) object;
        return Objects.equals(getLicensePlate(), car.getLicensePlate());
    }


    @Override
    public int hashCode()
    {
        return Objects.hash(getLicensePlate());
    }

}
