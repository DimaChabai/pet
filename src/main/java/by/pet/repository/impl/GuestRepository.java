package by.pet.repository.impl;

import by.pet.entity.impl.Guest;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface GuestRepository extends CrudRepository<Guest, Long> {
    @Query(value = "SELECT SUM(default_ticket_number) as bookedDefaultTicket, SUM(medium_ticket_number) as bookedMediumTicket, SUM(large_ticket_number) as bookedLargeTicket FROM GUEST", nativeQuery = true)
    List<String> getTicketsCount();
}
