
package com.mytaxi.datatransferobject;

import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.mytaxi.domainvalue.OnlineStatus;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class DriverSearchDTO
{

    //@Pattern(regexp = "^[\\\\p{L} .'-]+$", message = "No special charecter allowed in username")
    private String username;
    
   // @Pattern(regexp = "^[A-Z]{1,3}-[A-Z]{1,2}-[0-9]{1,4}$", message = "No special charecter allowed for licensePlate")
    private String licensePlate;
    
    private Float rating;
    
    private OnlineStatus onlineStatus;


    private DriverSearchDTO()
    {}


    private DriverSearchDTO(String username, String licensePlate, Float rating, OnlineStatus onlineStatus)
    {
        super();
        this.username = username;
        this.licensePlate = licensePlate;
        this.rating = rating;
        this.onlineStatus = onlineStatus;
    }


    public static DriverSearchDTOBuilder newBuilder()
    {
        return new DriverSearchDTOBuilder();
    }


    public String getUsername()
    {
        return username;
    }


    public void setUsername(String username)
    {
        this.username = username;
    }


    public OnlineStatus getOnlineStatus()
    {
        return onlineStatus;
    }


    public void setOnlineStatus(OnlineStatus onlineStatus)
    {
        this.onlineStatus = onlineStatus;
    }


    public String getLicensePlate()
    {
        return licensePlate;
    }


    public void setLicensePlate(String licensePlate)
    {
        this.licensePlate = licensePlate;
    }


    public Float getRating()
    {
        return rating;
    }


    public void setRating(Float rating)
    {
        this.rating = rating;
    }

    public static class DriverSearchDTOBuilder
    {
        private String username;
        private OnlineStatus onlineStatus;
        private String licensePlate;
        private Float rating;


        public DriverSearchDTOBuilder setUsername(String username)
        {
            this.username = username;
            return this;
        }


        public DriverSearchDTOBuilder setOnlineStatus(OnlineStatus onlineStatus)
        {
            this.onlineStatus = onlineStatus;
            return this;
        }


        public DriverSearchDTOBuilder setLicensePlate(String licensePlate)
        {
            this.licensePlate = licensePlate;
            return this;
        }


        public DriverSearchDTOBuilder setRating(Float rating)
        {
            this.rating = rating;
            return this;
        }


        public DriverSearchDTO createDriverDTO()
        {
            return new DriverSearchDTO(username, licensePlate, rating, onlineStatus);
        }

    }

}
