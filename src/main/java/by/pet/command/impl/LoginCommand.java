//package by.pet.command.impl;
//
//import by.pet.controller.SessionRequestContent;
//import by.pet.entity.impl.User;
//import by.pet.service.ServiceException;
//import by.pet.service.UserService;
//import by.pet.service.impl.UserServiceImpl;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//
//import java.util.Base64;
//import java.util.Optional;
//
//import static by.pet.command.PageMessage.INCORRECT_LOGIN_OR_PASSWORD;
//import static by.pet.command.PageMessage.MESSAGE_DATABASE_ERROR;
//import static by.pet.command.PageParameter.*;
//import static by.pet.command.PagePath.JSP_LOGIN_JSP;
//import static by.pet.command.PagePath.JSP_MAIN_JSP;
//
///**
// * Realization of {@code Command} interface.
// * Has {@code Logger} object for logging error.
// * Login {@code User}
// * using {@code LoginServiceImpl}.
// */
//public class LoginCommand implements Command {
//
//    private static final Logger logger = LogManager.getLogger();
//    private final UserService service = new UserServiceImpl();
//    private final Base64.Encoder encoder = Base64.getEncoder();
//    /**
//     * Gets user parameters from request to pass to the {@code LoginServiceImpl}
//     *
//     * @param content object that contain request, response and session information.
//     * @return forward page
//     */
//    @Override
//    public String execute(SessionRequestContent content) {
//        Optional<User> optionalUser = Optional.empty();
//        boolean isCatch = false;
//        try {
//            String email = content.getRequestParameter(EMAIL)[0];
//            String password = content.getRequestParameter(PASSWORD)[0];
//            String hash = encoder.encodeToString(password.getBytes());
//            optionalUser = service.authenticate(email, hash);
//        } catch (ServiceException e) {
//            logger.error(e);
//            isCatch = true;
//        }
//        if (optionalUser.isPresent()) {
//            User user = optionalUser.get();
//            content.setSessionAttribute(ID, user.getId());
//            content.setSessionAttribute(EMAIL, user.getEmail());
//            content.setSessionAttribute(ROLE_NAME, user.getRole());
//            content.setSessionAttribute(PHONE_NUMBER, user.getPhoneNumber());
//            content.setSessionAttribute(AVATAR, user.getAvatar());
//            logger.info(user + " logged in");
//            return JSP_MAIN_JSP;
//        } else {
//            String message = isCatch ? MESSAGE_DATABASE_ERROR : INCORRECT_LOGIN_OR_PASSWORD;
//            content.setRequestAttribute(ERROR_MESSAGE, message);
//            return JSP_LOGIN_JSP;
//        }
//    }
//}
