package pl.dev4lazy.githubapi1.dto.github;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;

@JsonIgnoreProperties(ignoreUnknown = true)
@Builder
public record BranchDto(
        String name,
        Commit commit
) {
    @JsonIgnoreProperties(ignoreUnknown = true)
    @Builder
    public record Commit(
            String sha
    ) {}
}