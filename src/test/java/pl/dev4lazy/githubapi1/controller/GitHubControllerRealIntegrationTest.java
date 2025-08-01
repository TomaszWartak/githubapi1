package pl.dev4lazy.githubapi1.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import pl.dev4lazy.githubapi1.dto.BranchInfo;
import pl.dev4lazy.githubapi1.dto.RepositoryResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Tag( "integration" )
class GitHubControllerRealIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    final String username = "TomaszWartak";
    final String umsRepoName = "ums";
    final String taskManagerRepoName = "TaskManager";
    final String githubApi1RepoName = "githubapi1";


    @Test
    void givenTomaszWartak_whenGetRepos_thenReturnReposWithBranches() throws Exception {
        // Attention: test created for a specific case where there are 3 specific repositories of user TomaszWartak

        // given
        final String uri = String.format( "/users/%s/repos", username );

        // when
        final MvcResult mvcResult = mockMvc.perform( get( uri ) )
                .andExpect( status().isOk() )
                .andReturn();

        // then
        final String json = mvcResult.getResponse().getContentAsString();
        final List<RepositoryResponse> actualResponses = objectMapper.readValue(
                json,
                new TypeReference<>() {}
        );

        final List<RepositoryResponse> expectedResponsesForFullCheck = List.of(
                createUmsRepoResponse(),
                createTaskManagerRepoResponse() );

        assertThat( actualResponses )
                .filteredOn(r -> Set.of( umsRepoName, taskManagerRepoName ).contains( r.name() ))
                .usingRecursiveComparison()
                .ignoringCollectionOrder()
                .isEqualTo( expectedResponsesForFullCheck );

        final RepositoryResponse expectedResponseWithoutShaCheck = actualResponses.stream()
                .filter(r -> githubApi1RepoName.equals( r.name() ))
                .findFirst()
                .orElseThrow( () -> new AssertionError( String.format("Repo '%s' should be present", githubApi1RepoName )));

        assertThat( expectedResponseWithoutShaCheck.branches() )
                .isNotEmpty()
                .extracting( BranchInfo::lastCommitSha )
                .doesNotContainNull();
    }

    private RepositoryResponse createUmsRepoResponse() {
        final ArrayList<BranchInfo> repoUmsBranchInfos = new ArrayList<>();
        repoUmsBranchInfos.add(
                new BranchInfo(
                        "main",
                        "3217b9b6b3fa6c100ab59cef21ccb5759bf9c065"
                )
        );
        repoUmsBranchInfos.add(
                new BranchInfo(
                        "feature/get-user",
                        "1f4a653ddbb19c06c4b266f7875ff4d2c2da5237"
                )
        );

        return RepositoryResponse.builder()
                .ownerLogin( username )
                .name( umsRepoName )
                .branches( repoUmsBranchInfos )
                .build();
    }

    private RepositoryResponse createTaskManagerRepoResponse() {
        final ArrayList<BranchInfo> repoTaskManagerBranchInfos = new ArrayList<>();
        repoTaskManagerBranchInfos.add(
                new BranchInfo(
                        "main",
                        "01a663fc576de614bfaa18b1e0040dffd742afc3"
                )
        );

        return RepositoryResponse.builder()
                .ownerLogin( username )
                .name( taskManagerRepoName )
                .branches( repoTaskManagerBranchInfos )
                .build();
    }

}
