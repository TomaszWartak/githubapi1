package pl.dev4lazy.githubapi1.dto;

public record BranchInfo(
        String name,
        String lastCommitSha
) {}
