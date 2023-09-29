package com.mira.webServices.repository;

import com.mira.webServices.model.PostcodeLatitudeLongitude;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PostcodeLatitudeLongitudeRepository extends JpaRepository<PostcodeLatitudeLongitude, Long> {

    PostcodeLatitudeLongitude findByPostcode(String postcode);
}
