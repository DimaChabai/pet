package by.pet.repository.impl;

import by.pet.entity.impl.Place;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlaceRepository extends CrudRepository<Place, Long> {

    List<Place> findByIdPlaceNotIn(List<Long> ids);

    List<Place> findByIdPlaceIn(List<Long> ids);

}
