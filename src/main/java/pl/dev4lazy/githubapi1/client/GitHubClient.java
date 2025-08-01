package pl.dev4lazy.githubapi1.client;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import pl.dev4lazy.githubapi1.dto.github.BranchDto;
import pl.dev4lazy.githubapi1.dto.github.RepoDto;
import pl.dev4lazy.githubapi1.exception.UserNotFoundException;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;

import static pl.dev4lazy.githubapi1.constants.ErrorMessages.*;
import static pl.dev4lazy.githubapi1.constants.GitHubApiConstants.*;

@Component
@RequiredArgsConstructor
public class GitHubClient {

    private RestTemplate restTemplate;
    private final RestTemplateBuilder builder;

    @PostConstruct
    public void init() {
        this.restTemplate = builder
                .rootUri( BASE_URI )
                .connectTimeout( Duration.ofSeconds( 5 ))
                .readTimeout( Duration.ofSeconds( 5 ))
                .build();
    }

    public List<RepoDto> getRepos( final String username) throws UserNotFoundException {
        try {
            final ResponseEntity<RepoDto[]> resp = restTemplate
                    .getForEntity( USER_REPOS_PATH, RepoDto[].class, username );
            final RepoDto[] body = resp.getBody();
            if ( body == null ) {
                throw new IllegalStateException(
                        String.format( EMPTY_BODY_FOR_USER, username )
                );
            }
            return Arrays.asList( body );
        } catch ( final HttpClientErrorException.NotFound ex ) {
            throw new UserNotFoundException( String.format(USER_NOT_FOUND, username ));
        }
    }

    public List<BranchDto> getBranches( final String owner, final String repo) {
        try {
            final BranchDto[] branches = restTemplate
                    .getForObject( REPO_BRANCHES_PATH, BranchDto[].class, owner, repo );
            return Arrays.asList(branches != null ? branches : new BranchDto[0] );
        } catch ( final HttpClientErrorException.NotFound ex ) {
            throw new UserNotFoundException(
                    String.format( REPOSITORY_NOT_FOUND, owner, repo ));
        }
    }
}