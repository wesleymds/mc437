package br.com.conpec.sade.repository.search;

import br.com.conpec.sade.domain.Feedback;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Feedback entity.
 */
public interface FeedbackSearchRepository extends ElasticsearchRepository<Feedback, Long> {
}
