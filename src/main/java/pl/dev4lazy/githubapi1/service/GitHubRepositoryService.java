package pl.dev4lazy.githubapi1.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.dev4lazy.githubapi1.client.GitHubClient;
import pl.dev4lazy.githubapi1.dto.BranchInfo;
import pl.dev4lazy.githubapi1.dto.RepositoryResponse;
import pl.dev4lazy.githubapi1.dto.github.RepoDto;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GitHubRepositoryService {

    private final GitHubClient client;

    public List<RepositoryResponse> listUserRepos( final String username ) {
        final List<RepoDto> repos = client.getRepos( username );

        return repos.stream()
                .filter( repo -> !repo.fork() )
                .map( repo -> {
                    final List<BranchInfo> branches = client.getBranches( repo.owner().login(), repo.name() )
                            .stream()
                            .map( branchDto -> new BranchInfo( branchDto.name(), branchDto.commit().sha() ) )
                            .toList();

                    return new RepositoryResponse(
                            repo.name(),
                            repo.owner().login(),
                            branches
                    );
                } )
                .toList();
    }
}
