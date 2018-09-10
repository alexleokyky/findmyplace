package ua.softserve.rv036.findmeplace.repository;

import org.springframework.data.repository.CrudRepository;
import ua.softserve.rv036.findmeplace.model.Place;
import ua.softserve.rv036.findmeplace.model.PlaceType;

public interface PlaceRepository extends CrudRepository<Place, Long> {
    Iterable<Place> findByPlaceType(PlaceType placeType);
}
