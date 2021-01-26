package by.pet.service.impl;

import by.pet.entity.impl.User;
import by.pet.repository.impl.UserRepository;
import by.pet.service.ServiceException;
import by.pet.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

public class UserServiceImpl implements UserService {

    private static UserRepository repository;
    private static final Logger logger = LogManager.getLogger();

    public User get(Long id) throws ServiceException {
        Optional<User> byId = repository.findById(id);
        if (byId.isPresent()) {
            return byId.get();
        } else {
            throw new ServiceException();
        }
    }

    @Override
    public User save(User user) {
        return repository.save(user);
    }


}
