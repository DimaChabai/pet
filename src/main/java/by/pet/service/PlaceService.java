package by.pet.service;

import by.pet.entity.impl.Place;

/**
 * Interface for working with 'place' table.
 * Provides a method for creating a place.
 */
public interface PlaceService {

    Place createPlace(String placeType, String seats) throws ServiceException;

    Place createPlace(Place place) throws ServiceException;
}
