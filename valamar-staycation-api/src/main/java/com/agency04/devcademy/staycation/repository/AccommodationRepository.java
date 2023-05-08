package com.agency04.devcademy.staycation.repository;

import com.agency04.devcademy.staycation.model.Accommodation;
import com.agency04.devcademy.staycation.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AccommodationRepository extends JpaRepository<Accommodation, Long> {
    //filter all accomodations by same city

    @Query("select a from Accommodation a where upper(a.location.country) = upper(:country)")
    List<Accommodation> findByLocation_CountryIgnoreCase(@Param("country") String country);

    @Query("select a from Accommodation a where upper(a.location.title) = upper(:title)")
    List<Accommodation> findByLocation_TitleIgnoreCase(@Param("title") String title);

    @Query("select a from Accommodation a where upper(a.accommodationType) = upper(:type)")
    List<Accommodation> findByTypeIgnoreCase(@Param("type") String type);

    @Query("select a from Accommodation a where a.location = :id")
    Accommodation findByLocation(@Param("id") long id);

    List<Accommodation> findByOwner(User owner);

    List<Accommodation> findByTitle(String title);

}
