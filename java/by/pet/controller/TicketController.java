package by.pet.controller;

import by.pet.entity.impl.User;
import by.pet.service.ServiceException;
import by.pet.service.TicketService;
import by.pet.service.UserService;
import by.pet.service.impl.TicketServiceImpl;
import by.pet.service.impl.UserServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

import static by.pet.command.PageMessage.*;
import static by.pet.command.PageParameter.ERROR_MESSAGE;
import static by.pet.command.PageParameter.MESSAGE;
import static by.pet.command.PagePath.JSP_MAIN_JSP;
import static by.pet.command.PagePath.JSP_TICKET_JSP;
import static by.pet.entity.UserRole.GUEST;

@RestController
@RequestMapping("/ticket")
public class TicketController {

    private static final Logger logger = LogManager.getLogger();
    private final TicketService service = new TicketServiceImpl();
    private final UserService userService = new UserServiceImpl();

    @GetMapping("/book")
    public ModelAndView book(ModelAndView modelAndView) {
        try {
            Map<String, Integer> ticketNumber = service.calculateTicketNumber();
            ticketNumber.forEach(modelAndView::addObject);
        } catch (ServiceException e) {
            logger.error(e);
            modelAndView.addObject(ERROR_MESSAGE, TICKET_LOAD_ERROR_MESSAGE);
        }
        modelAndView.setViewName(JSP_TICKET_JSP);
        return modelAndView;
    }

    @PostMapping("/book")
    public ModelAndView book(ModelAndView modelAndView,
                             @RequestParam(value = "defaultTicketNumber") int defaultTicketNumber,
                             @RequestParam(value = "mediumTicketNumber") int mediumTicketNumber,
                             @RequestParam(value = "largeTicketNumber") int largeTicketNumber,
                             HttpServletRequest request) throws ServiceException {
        boolean result = false;
        boolean isCatch = false;
        HttpSession session = request.getSession();
        User user = userService.get((Long) session.getAttribute("id"));
        try {
            long id = user.getId();
            result = service.addGuest(defaultTicketNumber, mediumTicketNumber, largeTicketNumber, id);
        } catch (ServiceException e) {
            logger.error(e);
            isCatch = true;
        }
        if (result) {
            //content.setSessionAttribute(ROLE_NAME, GUEST); todo update user
            user.setRole(GUEST);
            modelAndView.addObject(MESSAGE, TICKET_RESERVATION_SUCCESS);
            modelAndView.setViewName(JSP_MAIN_JSP);
        } else {
            String message = isCatch ? DATABASE_ERROR : TICKET_BOOK_ERROR_MESSAGE;
            modelAndView.addObject(ERROR_MESSAGE, message);
            modelAndView.setViewName(JSP_TICKET_JSP);
        }
        return modelAndView;
    }
}
