package by.pet.controller;

import by.pet.entity.impl.Place;
import by.pet.service.PlaceService;
import by.pet.service.ServiceException;
import by.pet.service.impl.PlaceServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

import static by.pet.command.PageMessage.CREATE_PLACE_ERROR_MESSAGE;
import static by.pet.command.PageParameter.ERROR_MESSAGE;
import static by.pet.command.PagePath.JSP_CREATE_JSP;

@RestController
@RequestMapping("/place")
@CrossOrigin(origins = "http://localhost:4200")
public class PlaceController {

    private static final Logger logger = LogManager.getLogger();
    private PlaceService service;

    public PlaceController(PlaceService service) {
        this.service = service;
    }

    @PostMapping("/create")
    public Place create(@RequestBody Place place) {
        try {
            service.createPlace(place);
        } catch (ServiceException e) {
            logger.error(e);
        }
        return place;
    }
}
