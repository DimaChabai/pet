package by.pet.service;

import by.pet.entity.impl.Beer;
import by.pet.entity.impl.Participant;
import by.pet.entity.impl.Place;

import java.util.List;

/**
 * The interface for working with the 'participant' table and its associated ones.
 * Provides a methods for accepting or declining participant.
 */
public interface ParticipantService {

    boolean addParticipant(String name, String placeName, Long id, String beerType) throws ServiceException;

    List<Place> getPlaces() throws ServiceException;

    List<Beer> getBeers() throws ServiceException;

    List<Participant> getParticipants() throws ServiceException;

    void accept(long id) throws ServiceException;

    void decline(long id) throws ServiceException;

    List<Participant> getVerificationPageContent() throws ServiceException;
}
