package by.pet.controller;

import by.pet.entity.UserRole;
import by.pet.entity.impl.Beer;
import by.pet.entity.impl.Participant;
import by.pet.entity.impl.Place;
import by.pet.service.ParticipantService;
import by.pet.service.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

import static by.pet.command.PageMessage.*;
import static by.pet.command.PageParameter.*;
import static by.pet.command.PagePath.JSP_BECOME_PARTICIPANT_JSP;
import static by.pet.command.PagePath.JSP_MAIN_JSP;

@RestController
@RequestMapping("/participant")
@CrossOrigin(origins = "http://localhost:4200")
public class ParticipantController {

    private static final Logger logger = LogManager.getLogger();
    private static final int cardPerPage = 6;
    @Autowired
    private static ParticipantService service;

    public ParticipantController(ParticipantService service) {
        ParticipantController.service = service;
    }

    @GetMapping("/verification")
    public List<Participant> verification() {
        List<Participant> result = null;
        try {
            result = service.getVerificationPageContent();
        } catch (ServiceException e) {
            logger.error(e);
        }
        return result;
    }

    @GetMapping("/{id}/accept")
    public List<Participant> accept(@PathVariable long id) {
        List<Participant> result = null;
        try {
            service.accept(id);
            result = service.getVerificationPageContent();
        } catch (ServiceException e) {
            logger.error(e);
        }
        return result;
    }

    @GetMapping("/{id}/decline")
    public List<Participant> decline(@PathVariable long id) {
        List<Participant> result = null;
        try {
            service.decline(id);
            result = service.getVerificationPageContent();
        } catch (ServiceException e) {
            logger.error(e);
        }
        return result;
    }

    @GetMapping("/list")
    public List<Participant> list() {
        List<Participant> participants = null;
        try {
            participants = service.getParticipants();
        } catch (ServiceException e) {
            logger.error(e);
        }
        return participants;
    }

    @GetMapping("/become")
    public ModelAndView index(ModelAndView modelAndView) {
        try {
            List<Beer> beers = service.getBeers();
            modelAndView.addObject(BEERTYPE, beers);
            List<Place> resultList = service.getPlaces();
            modelAndView.addObject(PLACES, resultList);
        } catch (ServiceException e) {
            logger.error(e);
            modelAndView.addObject(ERROR_MESSAGE, PLACES_LOAD_ERROR_MESSAGE);
        }
        modelAndView.setViewName(JSP_BECOME_PARTICIPANT_JSP);
        return modelAndView;
    }

    @PostMapping("/become")
    public ModelAndView becomeParticipant(
            @RequestParam(value = "company_name") String companyName,
            @RequestParam(value = "place") String placeName,
            @RequestParam(value = "id") Long id,
            @RequestParam(value = "beer_type") String beerType,
            ModelAndView modelAndView) {
        boolean result = false;
        try {
            result = service.addParticipant(companyName, placeName, id, beerType);
            modelAndView.addObject(MESSAGE, PARTICIPANT_MESSAGE);
        } catch (ServiceException e) {
            logger.error(e);
            modelAndView.addObject(ERROR_MESSAGE, PARTICIPANT_ERROR_MESSAGE);
        }
        if (result) {
            modelAndView.addObject(ROLE_NAME, UserRole.PARTICIPANT);
        } else {
            modelAndView.addObject(ERROR_MESSAGE, INVALID_PARTICIPANT_DATA);
        }
        modelAndView.setViewName(JSP_MAIN_JSP);
        return modelAndView;
    }
}
