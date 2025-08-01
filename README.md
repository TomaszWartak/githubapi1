# GitHubApi1

## Overview

GitHubApi1 is a REST API service that acts as a proxy for GitHub's API. It provides a simplified interface to retrieve information about a GitHub user's repositories and their branches.

### Features

- Retrieve non-fork repositories for a specific GitHub user
- Get branch information for each repository, including the last commit SHA
- Error handling for non-existent users and other common issues

## API Documentation

### Endpoints

The API provides the following endpoint:

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/users/{username}/repos` | Retrieves all non-fork repositories for the specified GitHub user along with branch information |

### Request Format

#### Get User Repositories

```
GET /users/{username}/repos
```

**Path Parameters:**

| Parameter | Type | Description |
|-----------|------|-------------|
| username | String | GitHub username |

**Example Request:**

```
GET /users/octocat/repos
```

### Response Format

#### Successful Response

A successful request returns a JSON array of repository objects with HTTP status code 200 (OK).

**Response Body:**

```json
[
  {
    "name": "repository-name",
    "ownerLogin": "username",
    "branches": [
      {
        "name": "branch-name",
        "lastCommitSha": "commit-hash"
      }
    ]
  }
]
```

**Response Fields:**

| Field | Type | Description |
|-------|------|-------------|
| name | String | Repository name |
| ownerLogin | String | GitHub username of the repository owner |
| branches | Array | List of branches in the repository |
| branches[].name | String | Branch name |
| branches[].lastCommitSha | String | SHA hash of the last commit on the branch |

### Error Handling

The API returns appropriate HTTP status codes and error messages for different error scenarios:

#### User Not Found (404)

If the specified GitHub user does not exist, the API returns a 404 status code.

**Example Response:**

```json
{
  "status": 404,
  "message": "User 'non-existent-user' not found"
}
```

#### Method Not Allowed (405)

If an unsupported HTTP method is used, the API returns a 405 status code.

**Example Response:**

```json
{
  "status": 405,
  "message": "HTTP method 'POST' is not allowed for this endpoint. Supported: GET /users/{username}/repos"
}
```

#### Endpoint Not Found (404)

If an invalid endpoint is accessed, the API returns a 404 status code.

**Example Response:**

```json
{
  "status": 404,
  "message": "Endpoint '/invalid/path' not found. Supported: GET /users/{username}/repos"
}
```

## Examples

### Example Request

```
GET /users/octocat/repos
```

### Example Response

```json
[
  {
    "name": "hello-world",
    "ownerLogin": "octocat",
    "branches": [
      {
        "name": "main",
        "lastCommitSha": "7fd1a60b01f91b314f59955a4e4d4e80d8edf11d"
      },
      {
        "name": "develop",
        "lastCommitSha": "553c2077f0edc3d5dc5d17262f6aa498e69d6f8e"
      }
    ]
  },
  {
    "name": "test-repo",
    "ownerLogin": "octocat",
    "branches": [
      {
        "name": "master",
        "lastCommitSha": "a84d88e7554fc1fa21bcbc4efae3c782a70d2b9d"
      }
    ]
  }
]
```

## Notes

- The API only returns repositories that are not forks
- The API uses GitHub's public API, so standard GitHub API rate limits may apply
- All responses are in JSON format
- The API requires no authentication for accessing public repositories

## Technical Details

- Built with Spring Boot
- Uses Spring RestTemplate for communication with GitHub API
- Implements proper error handling for various scenarios