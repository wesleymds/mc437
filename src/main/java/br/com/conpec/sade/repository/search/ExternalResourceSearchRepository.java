package br.com.conpec.sade.repository.search;

import br.com.conpec.sade.domain.ExternalResource;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the ExternalResource entity.
 */
public interface ExternalResourceSearchRepository extends ElasticsearchRepository<ExternalResource, Long> {
}
