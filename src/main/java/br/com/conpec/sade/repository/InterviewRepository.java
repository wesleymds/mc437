package br.com.conpec.sade.repository;

import br.com.conpec.sade.domain.Interview;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the Interview entity.
 */
@SuppressWarnings("unused")
public interface InterviewRepository extends JpaRepository<Interview,Long> {

    @Query("select distinct interview from Interview interview left join fetch interview.skills")
    List<Interview> findAllWithEagerRelationships();

    @Query("select interview from Interview interview left join fetch interview.skills where interview.id =:id")
    Interview findOneWithEagerRelationships(@Param("id") Long id);

}
