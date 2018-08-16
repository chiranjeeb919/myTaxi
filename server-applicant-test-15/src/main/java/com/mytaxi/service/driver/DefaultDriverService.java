package com.mytaxi.service.driver;

import java.time.ZonedDateTime;
import java.util.List;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.mytaxi.dataaccessobject.DriverRepository;
import com.mytaxi.datatransferobject.DriverSearchDTO;
import com.mytaxi.domainobject.CarDO;
import com.mytaxi.domainobject.DriverDO;
import com.mytaxi.domainvalue.GeoCoordinate;
import com.mytaxi.domainvalue.OnlineStatus;
import com.mytaxi.exception.CarAlreadyInUseException;
import com.mytaxi.exception.ConstraintsViolationException;
import com.mytaxi.exception.EntityNotFoundException;

/**
 * Service to encapsulate the link between DAO and controller and to have business logic for some driver specific things.
 * <p/>
 */
@Service
public class DefaultDriverService implements DriverService
{

    private static org.slf4j.Logger LOG = LoggerFactory.getLogger(DefaultDriverService.class);

    private final DriverRepository driverRepository;

    private final DefaultCarService carService;


    @Autowired
    public DefaultDriverService(final DriverRepository driverRepository, final DefaultCarService carService)
    {
        this.driverRepository = driverRepository;
        this.carService = carService;
    }


    /**
     * Selects a driver by id.
     *
     * @param driverId
     * @return found driver
     * @throws EntityNotFoundException if no driver with the given id was found.
     */
    @Override
    public DriverDO find(Long driverId) throws EntityNotFoundException
    {
        return findDriverChecked(driverId);
    }


    /**
     * Creates a new driver.
     *
     * @param driverDO
     * @return
     * @throws ConstraintsViolationException if a driver already exists with the given username, ... .
     */
    @Override
    @Transactional
    public DriverDO create(DriverDO driverDO) throws ConstraintsViolationException
    {
        DriverDO driver;
        try
        {
            driver = driverRepository.save(driverDO);
        }
        catch (DataIntegrityViolationException e)
        {
            LOG.warn("Some constraints are thrown due to driver creation", e);
            throw new ConstraintsViolationException(e.getMessage());
        }
        return driver;
    }


    /**
     * Deletes an existing driver by id.
     *
     * @param driverId
     * @throws EntityNotFoundException if no driver with the given id was found.
     */
    @Override
    @Transactional
    public void delete(Long driverId) throws EntityNotFoundException
    {
        DriverDO driverDO = findDriverChecked(driverId);
        driverDO.setDeleted(true);
        driverDO.setDateUpdated(ZonedDateTime.now());
    }


    /**
     * Update the location for a driver.
     *
     * @param driverId
     * @param longitude
     * @param latitude
     * @throws EntityNotFoundException
     */
    @Override
    @Transactional
    public void updateLocation(long driverId, double longitude, double latitude) throws EntityNotFoundException
    {
        DriverDO driverDO = findDriverChecked(driverId);
        driverDO.setCoordinate(new GeoCoordinate(latitude, longitude));
    }


    /**
     * Select a car for the driver.If the car is already selected then 
     * throw CarAlreadyInUseException.
     *
     * @param driverId
     * @param carId
     * @throws EntityNotFoundException
     * @throws CarAlreadyInUseException
     */
    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void selectCar(Long driverId, Long carId) throws EntityNotFoundException, CarAlreadyInUseException
    {
        CarDO carDO = carService.find(carId);
        if (!isCarAlreadyInUse(carDO))
        {
            DriverDO driverDO = findDriverChecked(driverId);
            driverDO.setCarDO(carDO);

        }
        else
        {
            LOG.error("Car already in use with id: " + carId);
            throw new CarAlreadyInUseException("Car already in use with id: " + carId);
        }
    }


    /**
     * De-select the car for the driver.
     *
     * @param driverId
     * @param carId
     * @throws EntityNotFoundException
     */
    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void deSelectCar(Long driverId, Long carId) throws EntityNotFoundException
    {
        CarDO carDO = carService.find(carId);

        if (isCarAssociateWithDriver(driverId, carDO))
        {
            DriverDO driverDO = findDriverChecked(driverId);
            driverDO.setCarDO(null);
        }
        else
        {
            LOG.error("No car selected with driverId: " + driverId);
            throw new EntityNotFoundException("No car selected with driverId" + driverId);
        }

    }


    /**
     * Find all drivers by online state.
     *
     * @param onlineStatus
     */
    @Override
    public List<DriverDO> find(OnlineStatus onlineStatus)
    {
        return driverRepository.findByOnlineStatus(onlineStatus);
    }


    private DriverDO findDriverChecked(Long driverId) throws EntityNotFoundException
    {
        return driverRepository
            .findById(driverId)
            .orElseThrow(() -> new EntityNotFoundException("Could not find entity with id: " + driverId));
    }


    private boolean isCarAlreadyInUse(CarDO carDO) throws EntityNotFoundException
    {
        return driverRepository
            .findByCarDO(carDO).isPresent();
    }


    private boolean isCarAssociateWithDriver(Long driverId, CarDO carDO) throws EntityNotFoundException
    {
        return driverRepository
            .findByIdAndCarDO(driverId, carDO).isPresent();
    }


    @Override
    public List<DriverDO> find(DriverSearchDTO driverSearchDTO)
    {
        return driverRepository.find(driverSearchDTO.getUsername(), driverSearchDTO.getOnlineStatus(), driverSearchDTO.getLicensePlate(), driverSearchDTO.getRating());
    }

}
