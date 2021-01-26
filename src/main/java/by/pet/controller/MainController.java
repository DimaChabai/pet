package by.pet.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * The main controller of the application. Defines a command and initializes its execution.
 * Receive and send data from request and session
 */
@RestController
public class MainController {

    @RequestMapping("/controller")
    private ModelAndView processRequest(ModelAndView modelAndView, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        SessionRequestContent content = new SessionRequestContent();
        content.extractValues(request);
        content.insertAttributes(request);
        return modelAndView;
    }
}
