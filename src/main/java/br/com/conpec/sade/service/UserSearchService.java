package br.com.conpec.sade.service;

import br.com.conpec.sade.domain.UserData;
import br.com.conpec.sade.repository.UserDataRepository;
import com.google.common.base.Strings;
import com.google.common.collect.Sets;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Danilo Valente <danilovalente96@gmail.com>
 */
@Service
public class UserSearchService {

    private final String DUMMY_STRING = "*";

    private final Set<String> DUMMY_QUERY = Sets.newHashSet(DUMMY_STRING);

    private UserDataRepository userDataRepository;

    @Inject
    public UserSearchService(final UserDataRepository userDataRepository) {
        this.userDataRepository = userDataRepository;
    }

    public List<UserData> search(final String name,
                                 final String skills,
                                 final Boolean available,
                                 final Integer minAvailableHours,
                                 final Integer maxCostPerHour) {

        final Set<String> skillsSet = Optional.ofNullable(skills)
            .map(s -> s.split("[\\s,]"))
            .map(Arrays::stream)
            .orElseGet(Stream::empty)
            .map(String::toLowerCase)
            .collect(Collectors.toSet());

        return search(name, skillsSet, available, minAvailableHours, maxCostPerHour);
    }

    public List<UserData> search(final String name,
                                 final Set<String> skills,
                                 final Boolean available,
                                 final Integer minAvailableHours,
                                 final Integer maxCostPerHour) {

        final Set<String> namesSet = Optional.ofNullable(name)
            .map(s -> s.isEmpty() ? DUMMY_STRING : s)
            .map(s -> s.split("\\s"))
            .map(Arrays::stream)
            .orElseGet(DUMMY_QUERY::stream)
            .map(String::toLowerCase)
            .map(s -> s.isEmpty() ? DUMMY_STRING : s)
            .collect(Collectors.toSet());

        final Set<String> skillsSet = Optional.ofNullable(skills)
            .map(s -> s.isEmpty() ? DUMMY_QUERY : s)
            .map(Collection::stream)
            .orElseGet(DUMMY_QUERY::stream)
            .map(String::trim)
            .map(String::toLowerCase)
            .map(s -> s.isEmpty() ? DUMMY_STRING : s)
            .filter(s -> !Strings.isNullOrEmpty(s))
            .collect(Collectors.toSet());

        return userDataRepository.searchWithEagerRelationships(
            // searchByName
            !namesSet.equals(DUMMY_QUERY),

            // namePattern
            namesSet,

            // searchBySkills
            !skillsSet.equals(DUMMY_QUERY),

            // skills
            skillsSet,

            // available
            available,

            // minAvailableHours
            minAvailableHours,

            // maxCostPerHour
            maxCostPerHour
        );
    }
}
