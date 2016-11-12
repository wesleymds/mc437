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

@Service
public class UserSearchService {

    private UserDataRepository userDataRepository;

    @Inject
    public UserSearchService(final UserDataRepository userDataRepository) {
        this.userDataRepository = userDataRepository;
    }

    public List<UserData> search(final String name,
                                 final List<String> skills,
                                 final Boolean available,
                                 final Integer minAvailableHours,
                                 final Integer maxCostPerHour) {

        final Set<String> dummyQuery = Sets.newHashSet("*");

        final List<String> namesList = Optional.ofNullable(name)
            .map(s -> s.split("\\s"))
            .map(Arrays::stream)
            .orElseGet(dummyQuery::stream)
            .map(String::toLowerCase)
            .collect(Collectors.toList());

        final List<String> skillsList = Optional.ofNullable(skills)
            .map(Collection::stream)
            .orElseGet(dummyQuery::stream)
            .map(String::trim)
            .map(String::toLowerCase)
            .filter(s -> !Strings.isNullOrEmpty(s))
            .collect(Collectors.toList());

        return userDataRepository.searchWithEagerRelationships(
            // searchByName
            namesList.equals(dummyQuery),

            // namePattern
            namesList,

            // searchBySkills
            skillsList.equals(dummyQuery),

            // skills
            skillsList,

            // available
            available,

            // minAvailableHours
            minAvailableHours,

            // maxCostPerHour
            maxCostPerHour
        );
    }
}
