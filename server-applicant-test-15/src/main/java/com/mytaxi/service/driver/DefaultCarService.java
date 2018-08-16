package com.mytaxi.service.driver;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mytaxi.dataaccessobject.CarRepository;
import com.mytaxi.dataaccessobject.ManufacturerRepository;
import com.mytaxi.domainobject.CarDO;
import com.mytaxi.domainobject.ManufacturerDO;
import com.mytaxi.exception.ConstraintsViolationException;
import com.mytaxi.exception.EntityNotFoundException;

/**
 * Service to encapsulate the link between DAO and controller and to have business logic for some car specific things.
 * <p/>
 */
@Service
public class DefaultCarService implements CarService
{
    private static org.slf4j.Logger LOG = LoggerFactory.getLogger(DefaultDriverService.class);

    private CarRepository carRepository;

    private ManufacturerRepository mafaRepository;


    @Autowired
    public DefaultCarService(final CarRepository carRepository, ManufacturerRepository mafaRepository)
    {
        this.carRepository = carRepository;
        this.mafaRepository = mafaRepository;
    }


    /**
     * Creates a new car.
     *
     * @param carDO
     * @param manufacturerId
     * @return
     * @throws ConstraintsViolationException if a car already exists with the given id ... .
     * @throws EntityNotFoundException 
     */
    @Override
    @Transactional
    public CarDO create(CarDO carDO, Long manufacturerId) throws ConstraintsViolationException, EntityNotFoundException
    {
        CarDO car;
        
        try
        {
            ManufacturerDO manufacturerDO = findManufacturerById(manufacturerId);
            carDO.setManufacturer(manufacturerDO);
            car = carRepository.save(carDO);
        }
        catch (DataIntegrityViolationException exe)
        {
            LOG.warn("Some constraints are thrown due to car creation", exe);
            throw new ConstraintsViolationException(exe.getMessage());
        }
        return car;
    }


    /**
     * Updates car with the given id.
     *
     * @param carId
     * @param carDO
     * @return
     * @throws EntityNotFoundException.
     */
    @Override
    @Transactional
    public void update(Long carId, CarDO carDO) throws EntityNotFoundException
    {
        CarDO persistanceCarDO = find(carId);
        carDO.setId(carId);
        carDO.setManufacturer(persistanceCarDO.getManufacturerDO());
        carRepository.save(carDO);
    }


    /**
     * Delete the existing car by id.
     *
     * @param carId
     * @throws EntityNotFoundException.
     */
    @Override
    @Transactional
    public void delete(Long carId) throws EntityNotFoundException
    {
        CarDO carDO = find(carId);
        carDO.setDeleted(true);
        carDO.setDateUpdated(ZonedDateTime.now());
    }


    /**
     * Select a car by id.
     *
     * @param carId
     * @throws EntityNotFoundException if no car with the given id was found.
     */
    @Override
    public CarDO find(Long carId) throws EntityNotFoundException
    {
        return carRepository
            .findById(carId)
            .orElseThrow(() -> new EntityNotFoundException("Could not find entity with id: " + carId));
    }
    
    /**
     * Select a manufacturer by id.
     *
     * @param manufacturerId
     * @throws EntityNotFoundException if no manufacturer with the given id was found.
     */
    private ManufacturerDO findManufacturerById(Long manufacturerId) throws EntityNotFoundException
    {
        return mafaRepository
            .findById(manufacturerId)
            .orElseThrow(() -> new EntityNotFoundException("Could not find entity with id: " + manufacturerId));
    }


    /**
     * Get all Manufacturers.
     *
     */
    public List<ManufacturerDO> getAllManufacturers()
    {
        List<ManufacturerDO> manufacturerList = new ArrayList<ManufacturerDO>();
        mafaRepository.findAll().forEach(manufacturerList::add);
        return manufacturerList;
    }

}
