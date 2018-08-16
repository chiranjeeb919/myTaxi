package com.mytaxi.controller.mapper;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import com.mytaxi.datatransferobject.CarDTO;
import com.mytaxi.datatransferobject.ManufacturerDTO;
import com.mytaxi.domainobject.CarDO;
import com.mytaxi.domainobject.ManufacturerDO;

public class CarMapper
{
    public static CarDO makeCarDO(CarDTO carDTO)
    {
        return new CarDO(carDTO.getLicensePlate(), carDTO.getSeatCount(), carDTO.getConvertible(), carDTO.getEngineType(), null);
    }


    public static CarDTO makeCarDTO(CarDO carDO)
    {
        CarDTO.CarDTOBuilder carDTOBuilder =
            CarDTO
                .newBuilder()
                .setId(carDO.getId())
                .setLicensePlate(carDO.getLicensePlate())
                .setSeatCount(carDO.getSeatCount())
                .setConvertible(carDO.getConvertible())
                .setEngineType(carDO.getEngineType())
                .setManufacturer(carDO.getManufacturerDO().getId())
                .setRating(carDO.getRating());

        return carDTOBuilder.createCarDTO();
    }


    public static List<CarDTO> makeDriverDTOList(Collection<CarDO> cars)
    {
        return cars
            .stream()
            .map(CarMapper::makeCarDTO)
            .collect(Collectors.toList());
    }
    public static ManufacturerDTO makeCarDTO(ManufacturerDO makeManufacturerDO)
    {
        ManufacturerDTO.ManufacturerDTOBuilder dtoBuilder =
            ManufacturerDTO
                .newBuilder()
                .setId(makeManufacturerDO.getId())
                .setName(makeManufacturerDO.getName());
                
        return dtoBuilder.createManufacturerDTO();
    }
    
    public static List<ManufacturerDTO> makeManufacturerDTO(List<ManufacturerDO> makeManufacturerDTOS) {
        return makeManufacturerDTOS.stream()
            .map(CarMapper::makeCarDTO)
            .collect(Collectors.toList());
    }
    
    
}
