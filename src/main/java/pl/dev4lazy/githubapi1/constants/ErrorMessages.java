package pl.dev4lazy.githubapi1.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ErrorMessages {
    public static final String USER_NOT_FOUND = "User '%s' not found";
    public static final String REPOSITORY_NOT_FOUND = "Repository '%s/%s' not found";
    public static final String EMPTY_BODY_FOR_USER =
            "GitHub API returned empty body for user '%s'";
    public static final String SUPPORTED_ENDPOINTS =
            "Supported: GET /users/{username}/repos";
    public static final String ENDPOINT_NOT_FOUND =
            "Endpoint '%s' not found. "+SUPPORTED_ENDPOINTS;
    public static final String HTTP_METHOD_NOT_ALLOWED =
            "HTTP method '%s' is not allowed for this endpoint. "+SUPPORTED_ENDPOINTS;
}