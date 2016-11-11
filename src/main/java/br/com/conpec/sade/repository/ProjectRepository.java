package br.com.conpec.sade.repository;

import br.com.conpec.sade.domain.Project;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the Project entity.
 */
@SuppressWarnings("unused")
public interface ProjectRepository extends JpaRepository<Project,Long> {

    @Query("select distinct project from Project project left join fetch project.developers left join fetch project.assessors")
    List<Project> findAllWithEagerRelationships();

    @Query("select project from Project project left join fetch project.developers left join fetch project.assessors where project.id =:id")
    Project findOneWithEagerRelationships(@Param("id") Long id);

}
