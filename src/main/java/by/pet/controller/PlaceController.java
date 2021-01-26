package by.pet.controller;

import by.pet.service.PlaceService;
import by.pet.service.ServiceException;
import by.pet.service.impl.PlaceServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import static by.pet.command.PageMessage.CREATE_PLACE_ERROR_MESSAGE;
import static by.pet.command.PageParameter.ERROR_MESSAGE;
import static by.pet.command.PagePath.JSP_CREATE_JSP;

@RestController("/place")
public class PlaceController {

    private static final Logger logger = LogManager.getLogger();
    private final PlaceService service = new PlaceServiceImpl();

    @PostMapping("/create")
    public ModelAndView create(ModelAndView modelAndView,
                               @RequestParam(value = "placeType") String placeType,
                               @RequestParam(value = "seats") String seats) {
        try {
            service.createPlace(placeType, seats);
        } catch (ServiceException e) {
            logger.error(e);
            modelAndView.addObject(ERROR_MESSAGE, CREATE_PLACE_ERROR_MESSAGE);
        }
        modelAndView.setViewName(JSP_CREATE_JSP);
        return modelAndView;
    }
}
