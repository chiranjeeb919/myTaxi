package com.mytaxi.dataaccessobject;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.mytaxi.domainobject.CarDO;
import com.mytaxi.domainvalue.EngineType;

/**
 * Database Access Object for Car table.
 * <p/>
 */
public interface CarRepository extends CrudRepository<CarDO, Long>
{

    List<CarDO> findByEngineType(EngineType engineType);


    //List<CarDO> findByManufacturer(String manufacturer);
}
