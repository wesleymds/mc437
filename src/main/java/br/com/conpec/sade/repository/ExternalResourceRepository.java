package br.com.conpec.sade.repository;

import br.com.conpec.sade.domain.ExternalResource;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the ExternalResource entity.
 */
@SuppressWarnings("unused")
public interface ExternalResourceRepository extends JpaRepository<ExternalResource,Long> {

}
