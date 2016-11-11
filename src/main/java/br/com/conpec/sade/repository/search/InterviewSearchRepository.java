package br.com.conpec.sade.repository.search;

import br.com.conpec.sade.domain.Interview;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Interview entity.
 */
public interface InterviewSearchRepository extends ElasticsearchRepository<Interview, Long> {
}
