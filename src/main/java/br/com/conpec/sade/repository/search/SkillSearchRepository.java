package br.com.conpec.sade.repository.search;

import br.com.conpec.sade.domain.Skill;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Skill entity.
 */
public interface SkillSearchRepository extends ElasticsearchRepository<Skill, Long> {
}
