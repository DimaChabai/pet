package by.pet.service;

import by.pet.entity.impl.User;

/**
 * Interface for working with 'user' table.
 * Provides a methods for creating and authentication a user.
 */
public interface UserService {

    User get(Long id) throws ServiceException;

    User save(User user);
}
