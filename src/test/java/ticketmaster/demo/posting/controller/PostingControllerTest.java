package ticketmaster.demo.posting.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.nio.charset.StandardCharsets;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;
import ticketmaster.demo.posting.dto.Posting;
import ticketmaster.demo.posting.dto.PostingManageDto.PostingCreateRequestDto;
import ticketmaster.demo.posting.dto.PostingManageDto.PostingCreateResponseDto;
import ticketmaster.demo.posting.repository.PostingRepository;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class PostingControllerTest {

    @Autowired
    PostingRepository postingRepository;

    @Autowired
    MockMvc mockMvc;

    private final String url;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public PostingControllerTest() {
        this.url = "/instagram/posting";
    }

    @Test
    @DisplayName("Find posting by posting ID successfully")
    void shouldReturnPosting_whenValidIdProvided() throws Exception {
       // given
        Posting posting = Posting.builder()
                .content("testContent")
                .imageUrl("http://testImageUrl")
                .title("testTitle")
                .build();

        postingRepository.save(posting);

        // when
        ResultActions result = mockMvc.perform(get(url + "?postingId={postingId}", posting.getId()));

        // then
        result.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.content").value(posting.getContent()))
                .andExpect(jsonPath("$.imageUrl").value(posting.getImageUrl()))
                .andExpect(jsonPath("$.title").value(posting.getTitle()))
                .andExpect(jsonPath("$.uploadDate").value(String.valueOf(posting.getUploadDate())))
                .andExpect(jsonPath("$.modifyDate").value(String.valueOf(posting.getModifyDate()))
                );
    }

    @Test
    @DisplayName("Return 404 when posting ID is not found")
    void shouldReturnNotFound_whenInvalidIdProvided() throws Exception {
        // given
        Long invalidPostingId = 999L;
        String expectedErrorMessage = "Posting with ID " + invalidPostingId + " not found";

        // when
        ResultActions result = mockMvc.perform(get(url + "?postingId={postingId}", invalidPostingId));

        // then
        result.andExpect(status().isNotFound())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_PLAIN_VALUE))
                .andExpect(content().string(expectedErrorMessage));
    }

    @Test
    @DisplayName("Find all postings successfully")
    void shouldReturnPostings_whenRequested() throws Exception {
        // given
        Posting posting1 = Posting.builder()
                .content("testContent1")
                .imageUrl("http://test1ImageUrl")
                .title("testTitle1")
                .build();

        Posting posting2 = Posting.builder()
                .content("testContent2")
                .imageUrl("http://test2ImageUrl")
                .title("testTitle2")
                .build();

        postingRepository.saveAll(List.of(posting1,posting2));

        // when
        ResultActions result = mockMvc.perform(get(url + "/all"));

        // then
        result.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()").value(2)).andReturn();
    }

    @Test
    @DisplayName("Create a new posting with an image file successfully")
    void shouldCreatePostingWithAnImageFileSuccessfully_whenValidDataProvided() throws Exception {
       // given
        PostingCreateRequestDto requestDto = PostingCreateRequestDto.builder()
                .content("postingRequestContent")
                .imageUrl("postingRequestImageUrl")
                .title("postingRequestTitle")
                .build();

        MockMultipartFile imageFile = new MockMultipartFile(
                "imageFile",
                "test.png",
                "image/png",
                new byte[2 * 1024 * 1024]
        );

        String requestDtoStr = objectMapper.registerModule(new JavaTimeModule())
                .writeValueAsString(requestDto);

        MockMultipartFile postingInfo = new MockMultipartFile(
                "postingInfo",
                "postingInfo",
                MediaType.APPLICATION_JSON_VALUE,
                requestDtoStr.getBytes(StandardCharsets.UTF_8));

        // when
        ResultActions result = mockMvc.perform(multipart(url)
                        .file(imageFile)
                        .file(postingInfo));

        // then
        List<Posting> postings = postingRepository.findAll();
        Posting posting = postings.get(0);

        PostingCreateResponseDto responseDto = PostingCreateResponseDto.builder()
                .id(posting.getId())
                .content(posting.getContent())
                .imageUrl(posting.getImageUrl())
                .title(posting.getTitle())
                .uploadDate(posting.getUploadDate())
                .modifyDate(posting.getModifyDate())
                .build();

        result.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(responseDto.getId()))
                .andExpect(jsonPath("$.content").value(responseDto.getContent()))
                .andExpect(jsonPath("$.imageUrl").value(responseDto.getImageUrl()))
                .andExpect(jsonPath("$.title").value(responseDto.getTitle()))
                .andExpect(jsonPath("$.uploadDate").value(responseDto.getUploadDate().format(dateTimeFormatter)))
                .andExpect(jsonPath("$.modifyDate").value(responseDto.getModifyDate().format(dateTimeFormatter))
                );
    }
}