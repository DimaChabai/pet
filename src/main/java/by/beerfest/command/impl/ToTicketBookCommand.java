package by.beerfest.command.impl;

import by.beerfest.command.Command;
import by.beerfest.controller.SessionRequestContent;
import by.beerfest.service.ServiceException;
import by.beerfest.service.TicketService;
import by.beerfest.service.impl.TicketServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;

import static by.beerfest.command.PageMessage.TICKET_LOAD_ERROR_MESSAGE;
import static by.beerfest.command.PageParameter.ERROR_MESSAGE;
import static by.beerfest.command.PagePath.JSP_TICKET_JSP;

/**
 * Realization of {@code Command} interface.
 * Has {@code Logger} object for logging error.
 */
public class ToTicketBookCommand implements Command {

    private static final Logger logger = LogManager.getLogger();
    private final TicketService service = new TicketServiceImpl();

    /**
     * Passes an array of tickets to the request.
     *
     * @param content object that contain request, response and session information.
     * @return forward page
     */
    @Override
    public String execute(SessionRequestContent content) {
        try {
            Map<String, Integer> ticketNumber = service.calculateTicketNumber();
            ticketNumber.forEach(content::setRequestAttribute);
        } catch (ServiceException e) {
            logger.error(e);
            content.setRequestAttribute(ERROR_MESSAGE, TICKET_LOAD_ERROR_MESSAGE);
        }
        return JSP_TICKET_JSP;
    }
}
