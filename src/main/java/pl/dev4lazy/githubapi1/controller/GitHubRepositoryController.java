package pl.dev4lazy.githubapi1.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.dev4lazy.githubapi1.dto.RepositoryResponse;
import pl.dev4lazy.githubapi1.service.GitHubRepositoryService;

import java.util.List;

@RestController
@RequestMapping("/users/{username}/repos")
@RequiredArgsConstructor
@Validated
public class GitHubRepositoryController {

    private final GitHubRepositoryService service;

    @GetMapping
    public ResponseEntity<List<RepositoryResponse>> getUserRepos(
            @PathVariable final String username
    ) {
        final List<RepositoryResponse> repos = service.listUserRepos( username );
        return ResponseEntity.ok( repos );
    }
}
