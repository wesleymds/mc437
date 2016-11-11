package br.com.conpec.sade.repository.search;

import br.com.conpec.sade.domain.UserData;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the UserData entity.
 */
public interface UserDataSearchRepository extends ElasticsearchRepository<UserData, Long> {
}
