package com.mytaxi.service.driver;

import java.util.List;

import com.mytaxi.domainobject.CarDO;
import com.mytaxi.domainobject.ManufacturerDO;
import com.mytaxi.exception.ConstraintsViolationException;
import com.mytaxi.exception.EntityNotFoundException;

public interface CarService
{

    CarDO create(CarDO carDO, Long manufacturerId) throws ConstraintsViolationException, EntityNotFoundException;
    
    void update(Long carId, CarDO carDO) throws EntityNotFoundException;
    
    void delete(Long carId) throws EntityNotFoundException;
    
    CarDO find(Long carId) throws EntityNotFoundException;
    
    public List<ManufacturerDO> getAllManufacturers();

}
