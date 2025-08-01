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

    @Test
    void givenTomaszWartak_whenGetRepos_thenReturnReposWithBranches() throws Exception {
        // given
        final RepositoryResponse expectedUmsRepoResponse = createUmsRepoResponse();
        final RepositoryResponse expectedTaskManagerRepoResponse = createTaskManagerRepoResponse();
        final List<RepositoryResponse> expectedResponses = List.of( expectedUmsRepoResponse, expectedTaskManagerRepoResponse );

        // when
        final MvcResult mvcResult = mockMvc.perform( get( "/users/TomaszWartak/repos" ) )
                .andExpect( status().isOk() )
                .andReturn();

        // then
        final String json = mvcResult.getResponse().getContentAsString();
        final List<RepositoryResponse> actual = objectMapper.readValue(
                json,
                new TypeReference<>() {}
        );

        assertThat(actual)
                .usingRecursiveComparison()
                .ignoringCollectionOrder()
                .isEqualTo( expectedResponses );
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
                .ownerLogin( "TomaszWartak" )
                .name( "ums" )
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
                .ownerLogin( "TomaszWartak" )
                .name( "TaskManager" )
                .branches( repoTaskManagerBranchInfos )
                .build();
    }
}
