package by.beerfest.controller;

import by.beerfest.entity.impl.User;
import by.beerfest.repository.RepositoryException;
import by.beerfest.repository.impl.UserRepository;
import by.beerfest.specification.FestSpecification;
import by.beerfest.specification.impl.FestSpecificationUserFindByEmail;
import by.beerfest.validator.UserDataValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.File;
import java.io.IOException;
import java.util.Base64;
import java.util.UUID;

import static by.beerfest.command.PageMessage.*;
import static by.beerfest.command.PageParameter.*;
import static by.beerfest.command.PagePath.JSP_PROFILE_JSP;

/**
 * Controller for changing avatar and phone number.
 * Marked by annotation {@code @MultipartConfig}.
 */
@WebServlet("/profile")
@MultipartConfig(fileSizeThreshold = 1000 * 1000
        , maxFileSize = 1000 * 1000)
public class UserUpdateController extends HttpServlet {

    private static final String EMPTY_STRING = "";
    private static final String DOT = ".";
    private static final String UPLOAD_DIR = "avatars";
    private static final UserRepository repository = UserRepository.getInstance();
    private static final Logger logger = LogManager.getLogger();
    private static final Base64.Encoder encoder = Base64.getEncoder();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String phoneNumber = request.getParameter(PHONE_NUMBER);
        UserDataValidator validator = new UserDataValidator();
        String password = request.getParameter(PASSWORD);
        Part part = request.getPart(FILE);
        String path = part.getSubmittedFileName();
        boolean passValidated = validator.passwordValidate(password);
        boolean phoneNumberValidated = validator.phoneNumberValidate(phoneNumber);
        boolean avatarValidated = validator.avatarValidate(path);
        if (phoneNumberValidated || avatarValidated || passValidated) {
            HttpSession session = request.getSession();
            String email = (String) session.getAttribute(EMAIL);
            FestSpecification specification = new FestSpecificationUserFindByEmail(email);
            User user;
            try {
                user = repository.query(specification).get(0);
            } catch (RepositoryException e) {
                logger.error(e);
                response.sendError(500);
                return;
            }
            if (phoneNumberValidated) {
                user.setPhoneNumber(phoneNumber);
            }
            if (avatarValidated) {
                String applicationDir = request.getServletContext().getRealPath(EMPTY_STRING);
                String uploadFileDir = applicationDir + UPLOAD_DIR + File.separator;
                File fileSaveDir = new File(uploadFileDir);
                if (!fileSaveDir.exists()) {
                    fileSaveDir.mkdirs();
                }
                String randFilename;
                String fileName;
                randFilename = UUID.randomUUID() + path.substring(path.lastIndexOf(DOT));
                fileName = uploadFileDir + randFilename;
                part.write(fileName);
                session.setAttribute(AVATAR, randFilename);
                user.setAvatar(randFilename);
            }
            if (passValidated) {
                String hash = encoder.encodeToString(password.getBytes());
                user.setPassword(hash);
            }
            try {
                repository.update(user);
                session.setAttribute(PHONE_NUMBER, user.getPhoneNumber());
                session.setAttribute(AVATAR, user.getAvatar());
                request.setAttribute(MESSAGE, USER_UPDATE_SUCCESS);
            } catch (RepositoryException e) {
                logger.error(e);
                request.setAttribute(ERROR_MESSAGE, USER_UPDATE_ERROR);
            }
        } else {
            request.setAttribute(ERROR_MESSAGE, INVALID_USER_DATA);
        }
        getServletContext().getRequestDispatcher(JSP_PROFILE_JSP).forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        getServletContext().getRequestDispatcher(JSP_PROFILE_JSP).forward(request, response);
    }
}
