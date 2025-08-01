package pl.dev4lazy.githubapi1.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record RepositoryResponse(
        String name,
        String ownerLogin,
        List<BranchInfo> branches
) {}
