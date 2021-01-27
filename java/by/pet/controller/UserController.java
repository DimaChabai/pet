package by.pet.controller;

import by.pet.entity.impl.User;
import by.pet.service.UserService;
import by.pet.service.impl.UserServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Base64;

import static by.pet.command.PageMessage.INVALID_USER_DATA;
import static by.pet.command.PageMessage.REGISTRATION_SUCCESS;
import static by.pet.command.PageParameter.ERROR_MESSAGE;
import static by.pet.command.PageParameter.MESSAGE;
import static by.pet.command.PagePath.JSP_REGISTRATION_JSP;
import static by.pet.command.PagePath.ROOT_PAGE;

@RestController
public class UserController {

    private static final Logger logger = LogManager.getLogger();
    private final UserService service = new UserServiceImpl();
    private final Base64.Encoder encoder = Base64.getEncoder();

    @PostMapping("/registration")
    public ModelAndView registration(@RequestBody User user,
                                     ModelAndView modelAndView) {
        User result;
        user.setPassword(encoder.encodeToString(user.getPassword().getBytes()));
        result = service.save(user);

        if (result != null) {
            modelAndView.addObject(MESSAGE, REGISTRATION_SUCCESS);
        } else {
            modelAndView.addObject(ERROR_MESSAGE, INVALID_USER_DATA);
        }
        modelAndView.setViewName(JSP_REGISTRATION_JSP);
        return modelAndView;
    }

    @GetMapping("/logout")
    public ModelAndView logout(ModelAndView modelAndView, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            logger.info(String.format("User with id(%s) logged out", session.getAttribute("id")));
            session.invalidate();
        }
        modelAndView.setViewName(ROOT_PAGE);
        return modelAndView;
    }

    @GetMapping("/login")
    public ModelAndView login(ModelAndView modelAndView) {
        modelAndView.setViewName("login");
        return modelAndView;
    }
}
