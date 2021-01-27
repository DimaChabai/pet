package by.pet.service.impl;

import by.pet.entity.PlaceType;
import by.pet.entity.impl.Place;
import by.pet.repository.impl.PlaceRepository;
import by.pet.service.PlaceService;
import by.pet.service.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class PlaceServiceImpl implements PlaceService {

    private static final Logger logger = LogManager.getLogger();
    private PlaceRepository repository;

    public PlaceServiceImpl(PlaceRepository repository) {
        this.repository = repository;
    }

    public Place createPlace(String placeType, String seats) throws ServiceException {
        Place place = new Place();
        place.setType(PlaceType.valueOf(placeType));
        place.setSeats(Integer.parseInt(seats));
        repository.save(place);
        logger.info("Place(" + place + ") created");
        return place;
    }

    @Override
    public Place createPlace(Place place) throws ServiceException {
        repository.save(place);
        logger.info("Place(" + place + ") created");
        return place;
    }
}
