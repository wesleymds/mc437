package br.com.conpec.sade.repository;

import br.com.conpec.sade.domain.UserData;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

/**
 * Spring Data JPA repository for the UserData entity.
 */
@SuppressWarnings("unused")
public interface UserDataRepository extends JpaRepository<UserData,Long> {

    @Query("select distinct userData from UserData userData left join fetch userData.skills")
    List<UserData> findAllWithEagerRelationships();

    @Query("select userData from UserData userData left join fetch userData.skills where userData.id =:id")
    UserData findOneWithEagerRelationships(@Param("id") Long id);

    @Query(value = "select distinct ud from UserData ud" +
        " left join fetch ud.skills as s" +
        " where (:searchByName = false or lower(ud.user.firstName) in (:names) or lower(ud.user.lastName) in (:names))" +
        " and (:searchBySkills = false or lower(s.name) in (:skills))" +
        " and (:available is null or :available = ud.available)" +
        " and (:minAvailableHours is null or ud.availableHoursPerWeek >= :minAvailableHours)" +
        " and (:maxCostPerHour is null or ud.initialCostPerHour <= :maxCostPerHour)")
    List<UserData> searchWithEagerRelationships(@Param("searchByName") boolean searchByName,
                                                @Param("names") Set<String> names,
                                                @Param("searchBySkills") boolean searchBySkills,
                                                @Param("skills") Set<String> skills,
                                                @Param("available") Boolean available,
                                                @Param("minAvailableHours") Integer minAvailableHours,
                                                @Param("maxCostPerHour") Integer maxCostPerHour);
}
