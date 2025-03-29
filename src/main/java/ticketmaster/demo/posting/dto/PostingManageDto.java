package ticketmaster.demo.posting.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class PostingManageDto {

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PostingCreateRequestDto {

        @NotNull(message = "Please enter posting title")
        private String title;

        private String imageUrl;

        private String content;

        public Posting toEntity() {
            return Posting.builder()
                    .title(title)
                    .imageUrl(imageUrl)
                    .content(content)
                    .build();
        }
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PostingCreateResponseDto {

        private Long id;

        private String title;

        private String imageUrl;

        private String content;

        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private LocalDateTime uploadDate;

        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private LocalDateTime modifyDate;

        public static PostingCreateResponseDto fromEntity(Posting posting) {
            return PostingCreateResponseDto.builder()
                    .id(posting.getId())
                    .title(posting.getTitle())
                    .imageUrl(posting.getImageUrl())
                    .content(posting.getContent())
                    .uploadDate(posting.getUploadDate())
                    .modifyDate(posting.getModifyDate())
                    .build();
        }
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PostingGetResponseDto {

        private Long id;

        private String title;

        private String imageUrl;

        private String content;

        private LocalDateTime uploadDate;

        private LocalDateTime modifyDate;

        public static PostingGetResponseDto fromEntity(Posting posting) {
            return PostingGetResponseDto.builder()
                    .id(posting.getId())
                    .title(posting.getTitle())
                    .imageUrl(posting.getImageUrl())
                    .content(posting.getContent())
                    .uploadDate(posting.getUploadDate())
                    .modifyDate(posting.getModifyDate())
                    .build();
        }
    }
}
