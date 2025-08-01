package pl.dev4lazy.githubapi1.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class GitHubApiConstants {
    public static final String BASE_URI = "https://api.github.com";
    public static final String USER_REPOS_PATH = "/users/{username}/repos";
    public static final String REPO_BRANCHES_PATH = "/repos/{owner}/{repo}/branches";
}