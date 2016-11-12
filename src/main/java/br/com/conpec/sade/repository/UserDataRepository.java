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

    @Query("select ud from UserData ud\n" +
        " left join fetch ud.skills as s\n" +
        " where (:searchByName = false or lower(ud.user.firstName) in (:names) or lower(ud.user.lastName) in (:names))\n" +
        " and (:searchBySkills = false or lower(s.name) in (:skills))\n" +
        " and (:searchByAvailability = false or :available = ud.available)")
    List<UserData> searchWithEagerRelationships(@Param("searchByName") boolean searchByName,
                                                @Param("names") List<String> names,
                                                @Param("searchBySkills") boolean searchBySkills,
                                                @Param("skills") List<String> skills,
                                                @Param("searchByAvailability") boolean searchByAvailability,
                                                @Param("available") Boolean available);
}
