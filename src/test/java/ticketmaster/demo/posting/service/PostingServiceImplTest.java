package ticketmaster.demo.posting.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import ticketmaster.demo.config.DatabaseCleanUp;
import ticketmaster.demo.posting.dto.Posting;
import ticketmaster.demo.posting.dto.PostingManageDto.PostingCreateRequestDto;
import ticketmaster.demo.posting.dto.PostingManageDto.PostingCreateResponseDto;
import ticketmaster.demo.posting.dto.PostingManageDto.PostingGetResponseDto;
import ticketmaster.demo.posting.repository.PostingRepository;

@SpringBootTest
class PostingServiceImplTest {

    @Autowired
    PostingService postingService;

    @Autowired
    PostingRepository postingRepository;

    @Autowired
    private DatabaseCleanUp databaseCleanUp;

    @AfterEach
    void tearDown() {
        this.databaseCleanUp.truncateAllEntity();
    }

    @Test
    @DisplayName("Should retrieve a posting by its ID")
    void shouldRetrievePostingById() throws Exception {
        // given
        Posting savePosting = Posting.builder()
                .content("testContent")
                .imageUrl("http://testImageUrl")
                .title("testTitle")
                .build();

        postingRepository.save(savePosting);

        // when
        PostingGetResponseDto posting = postingService.getPosting(savePosting.getId());

        // then
        assertThat(posting.getId()).isEqualTo(savePosting.getId());
        assertThat(posting.getContent()).isEqualTo(savePosting.getContent());
        assertThat(posting.getTitle()).isEqualTo(savePosting.getTitle());
        assertThat(posting.getImageUrl()).isEqualTo(savePosting.getImageUrl());
        assertThat(posting.getUploadDate()).isNotNull();
        assertThat(posting.getModifyDate()).isNotNull();
    }

    @Test
    @DisplayName("Should retrieve all postings")
    void shouldRetrieveAllPostings() throws Exception {
        // given
        List<Posting> postings = new ArrayList<>();
        for (int i = 1; i <= 10; i++ ) {
            Posting posting = Posting.builder()
                    .content("testContent" + i)
                    .imageUrl("http://testImageUrl" + i)
                    .title("testTitle" + i)
                    .build();
            postings.add(posting);
        }

        postingRepository.saveAll(postings);

        // when
        List<PostingGetResponseDto> allPosting = postingService.getAllPosting();

        // then
        assertThat(allPosting).hasSize(postings.size());
    }

    @Test
    @DisplayName("Should create a new posting with an image")
    void shouldCreateNewPostingWithImage() throws Exception {
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

        // when
        PostingCreateResponseDto responsePosting = postingService.createPosting(imageFile, requestDto);

        // then
        assertThat(requestDto.getContent()).isEqualTo(responsePosting.getContent());
        assertThat(requestDto.getImageUrl()).isEqualTo(responsePosting.getImageUrl());
        assertThat(requestDto.getTitle()).isEqualTo(responsePosting.getTitle());
    }
}