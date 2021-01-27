package by.pet.service.impl;

import by.pet.entity.impl.Beer;
import by.pet.entity.impl.Participant;
import by.pet.entity.impl.Place;
import by.pet.repository.impl.BeerRepository;
import by.pet.repository.impl.ParticipantRepository;
import by.pet.repository.impl.PlaceRepository;
import by.pet.service.ParticipantService;
import by.pet.service.ServiceException;
import by.pet.validator.ParticipantDataValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ParticipantServiceImpl implements ParticipantService {
    @Autowired
    private final BeerRepository beerRepository;
    @Autowired
    private final ParticipantRepository participantRepository;
    @Autowired
    private final PlaceRepository placeRepository;

    public ParticipantServiceImpl(BeerRepository beerRepository, ParticipantRepository participantRepository, PlaceRepository placeRepository) {
        this.beerRepository = beerRepository;
        this.participantRepository = participantRepository;
        this.placeRepository = placeRepository;
    }

    private static final String ID_PLACE_REGEX = "№\\d*";
    private static final Logger logger = LogManager.getLogger();

    public boolean addParticipant(String name, String placeName, Long id, String beerType) throws ServiceException {
        List<String> beers = (List<String>) getBeers().stream().map(Beer::getBeerType);
        ParticipantDataValidator validator = new ParticipantDataValidator();
        if (!validator.companyNameValidate(name) || !beers.contains(beerType)) {
            return false;
        }
        Participant participant = new Participant();
        Pattern pattern = Pattern.compile(ID_PLACE_REGEX);
        Matcher matcher = pattern.matcher(placeName);
        String idPlace = "";
        if (matcher.find()) {
            idPlace = matcher.group().substring(1);
        } else {
            logger.error("Impossible state");
        }
        Place place;
        Optional<Place> optionalPlace = placeRepository.findById(Long.parseLong(idPlace));
        if (optionalPlace.isPresent()) {
            place = optionalPlace.get();
        } else {
            throw new ServiceException();
        }
        participant.setPlace(place);
        participant.setConfirmed(false);
        participant.setName(name);
        participant.setId(id);
        participant.setBeerType(beerType);
        participantRepository.save(participant);
        logger.info("Participant registered: " + participant);

        return true;
    }

    public List<Beer> getBeers() {
        List<Beer> beers = new ArrayList<>();
        Iterable<Beer> iterable = beerRepository.findAll();
        iterable.forEach((beers::add));
        return beers;
    }

    public List<Place> getPlaces() {
        List<Place> resultList;
        Iterable<Participant> all = participantRepository.findAll();
        List<Long> ids = new ArrayList<>();
        all.forEach(participant -> ids.add(participant.getId()));
        resultList = placeRepository.findByIdPlaceNotIn(ids);
        return resultList;
    }

    @Override
    public List<Participant> getParticipants() {
        List<Participant> resultList = new ArrayList<>();
        Iterable<Participant> all = participantRepository.findAll();
        all.forEach(resultList::add);
        return resultList;
    }

    public void accept(long id) throws ServiceException {
        Participant participant;
        Optional<Participant> byId = participantRepository.findById(id);
        if (byId.isPresent()) {
            participant = byId.get();
        } else {
            throw new ServiceException();
        }
        participant.setConfirmed(true);
        participantRepository.save(participant);
        logger.info("Participant: " + participant + " accepted");
    }

    public void decline(long id) throws ServiceException {
        Participant participant;
        Optional<Participant> byId = participantRepository.findById(id);
        if (byId.isPresent()) {
            participant = byId.get();
        } else {
            throw new ServiceException();
        }
        participantRepository.delete(participant);
        logger.info("Participant: " + participant + " declined");
    }

    public List<Participant> getVerificationPageContent() throws ServiceException {
        List<Participant> resultList;
        resultList = participantRepository.findByConfirmedFalse();
        return resultList;
    }
}
