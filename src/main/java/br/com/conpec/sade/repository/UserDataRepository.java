package br.com.conpec.sade.repository;

import br.com.conpec.sade.domain.UserData;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the UserData entity.
 */
@SuppressWarnings("unused")
public interface UserDataRepository extends JpaRepository<UserData,Long> {

    @Query("select distinct userData from UserData userData left join fetch userData.skills")
    List<UserData> findAllWithEagerRelationships();

    @Query("select userData from UserData userData left join fetch userData.skills where userData.id =:id")
    UserData findOneWithEagerRelationships(@Param("id") Long id);

}
