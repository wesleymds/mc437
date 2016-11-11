package br.com.conpec.sade.service;

import br.com.conpec.sade.domain.UserData;
import br.com.conpec.sade.repository.UserDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserSearchService {

    private UserDataRepository userDataRepository;

    @Autowired
    public UserSearchService(final UserDataRepository userDataRepository) {
        this.userDataRepository = userDataRepository;
    }

    public List<UserData> search(final String name) {
        return userDataRepository.searchWithEagerRelationships(
            Optional.ofNullable(name)
                .map(String::toLowerCase)
                .map(s -> s.split("\\s"))
                .map(s -> '%' + s + '%')
                .orElseGet(null)
        );
    }
}
