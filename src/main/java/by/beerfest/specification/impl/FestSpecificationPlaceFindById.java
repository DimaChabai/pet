package by.beerfest.specification.impl;

import by.beerfest.specification.FestSpecification;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static by.beerfest.specification.Query.FIND_PLACE_BY_ID;

public class FestSpecificationPlaceFindById implements FestSpecification {
    private final long id;

    public FestSpecificationPlaceFindById(long id) {
        this.id = id;
    }

    @Override
    public PreparedStatement specified(Connection connection) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(FIND_PLACE_BY_ID);
        statement.setLong(1, id);
        return statement;
    }
}
