package by.pet.service.impl;

import by.pet.entity.PlaceType;
import by.pet.entity.impl.Guest;
import by.pet.entity.impl.Participant;
import by.pet.entity.impl.Place;
import by.pet.repository.impl.GuestRepository;
import by.pet.repository.impl.ParticipantRepository;
import by.pet.repository.impl.PlaceRepository;
import by.pet.service.ServiceException;
import by.pet.service.TicketService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static by.pet.command.PageParameter.*;
import static by.pet.entity.TicketType.*;

public class TicketServiceImpl implements TicketService {

    private static final Logger logger = LogManager.getLogger();
    private static GuestRepository guestRepository;
    private static PlaceRepository placeRepository;
    private static ParticipantRepository participantRepository;

    public boolean addGuest(int defaultTicketCount, int mediumTicketCount, int largeTicketCount, long id) throws ServiceException {
        Map<String, Integer> ticketNumber = calculateTicketNumber();
        if ((defaultTicketCount == 0 && mediumTicketCount == 0 && largeTicketCount == 0)
                || ticketNumber.get(DEFAULT_TICKET_NUMBER) < defaultTicketCount
                || ticketNumber.get(MEDIUM_TICKET_NUMBER) < mediumTicketCount
                || ticketNumber.get(LARGE_TICKET_NUMBER) < largeTicketCount) {
            return false;
        }
        Guest guest = new Guest();
        guest.setId(id);
        guest.setDefaultTicketNumber(defaultTicketCount);
        guest.setMediumTicketNumber(mediumTicketCount);
        guest.setLargeTicketNumber(largeTicketCount);
        guestRepository.save(guest);
        logger.info("User with id( " + id + ") booked tickets: default ticket: " + defaultTicketCount
                + ", medium ticket: " + mediumTicketCount
                + ", large ticket: " + largeTicketCount);
        return true;
    }

    public Map<String, Integer> calculateTicketNumber() throws ServiceException {
        List<Place> reservedPlace;
        Iterable<Participant> all = participantRepository.findAll();
        List<Long> ids = new ArrayList<>();
        all.forEach(participant -> ids.add(participant.getId()));
        reservedPlace = placeRepository.findByIdPlaceNotIn(ids);
        Map<String, Long> bookedTickets = (Map<String, Long>) guestRepository.getTicketsCount();

        int defaultTicketCount = reservedPlace.stream().filter(v -> v.getType().equals(PlaceType.SMALL)).reduce(0,
                (v, p) -> v + p.getSeats(),
                Integer::sum);
        int mediumTicketCount = reservedPlace.stream().filter(v -> v.getType().equals(PlaceType.MEDIUM)).reduce(0,
                (v, p) -> v + p.getSeats(),
                Integer::sum);
        int largeTicketCount = reservedPlace.stream().filter(v -> v.getType().equals(PlaceType.LARGE)).reduce(0,
                (v, p) -> v + p.getSeats(),
                Integer::sum);

        if (!bookedTickets.isEmpty()) {
            defaultTicketCount -= bookedTickets.get(DEFAULT.name());
            mediumTicketCount -= bookedTickets.get(MEDIUM.name());
            largeTicketCount -= bookedTickets.get(LARGE.name());
        }
        Map<String, Integer> result = new HashMap<>();
        result.put(DEFAULT_TICKET_NUMBER, defaultTicketCount);
        result.put(MEDIUM_TICKET_NUMBER, mediumTicketCount);
        result.put(LARGE_TICKET_NUMBER, largeTicketCount);
        return result;
    }
}
