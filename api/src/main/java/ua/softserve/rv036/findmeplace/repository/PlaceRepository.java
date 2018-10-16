package ua.softserve.rv036.findmeplace.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ua.softserve.rv036.findmeplace.model.Place;
import ua.softserve.rv036.findmeplace.model.enums.PlaceType;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlaceRepository extends JpaRepository<Place, Long> {
    @Query("SELECT p FROM Place p WHERE p.approved = true and p.rejected = false")
    List<Place> findAll();

    @Query("SELECT p FROM Place p WHERE p.approved = true and p.rejected = false")
    List<Place> findByPlaceType(PlaceType placeType);

    @Query("select p from Place p where p.approved = true and p.rejected = false")
    List<Place> findByPlaceTypeIn(List<PlaceType> placeTypes);

    @Query("select p from Place p where p.approved = true and p.rejected = false")
    List<Place> findByNameIn(String searchValue);

    @Query("select p from Place p where p.approved = true and p.rejected = false and p.ownerId = :id")
    List<Place> findAllByOwnerId(@Param("id") Long id);

    @Query("select p from Place p where p.ownerId = :id")
    List<Place> findAllAndRejectedByOwnerId(@Param("id") Long id);

    @Query("select p from Place p where p.approved = false and p.rejected = false")
    List<Place> findAllNotApproved();

    @Query("select p from Place p where p.approved = false and p.rejected = false and p.id = :id")
    Optional<Place> findNotApprovedById(@Param("id") Long id);

    Boolean existsByName(String name);
}
