package pl.dev4lazy.githubapi1.dto.github;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;

@JsonIgnoreProperties(ignoreUnknown = true)
@Builder
public record RepoDto(
        String name,
        boolean fork,
        Owner owner
) {
    @JsonIgnoreProperties(ignoreUnknown = true)
    @Builder
    public record Owner(
            String login
    ) {}
}