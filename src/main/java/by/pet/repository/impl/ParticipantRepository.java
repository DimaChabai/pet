package by.pet.repository.impl;

import by.pet.entity.impl.Participant;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParticipantRepository extends CrudRepository<Participant, Long> {
    List<Participant> findByConfirmedTrue();

    List<Participant> findByConfirmedFalse();
}
