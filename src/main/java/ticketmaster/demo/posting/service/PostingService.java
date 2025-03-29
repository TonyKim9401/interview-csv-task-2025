package ticketmaster.demo.posting.service;

import java.util.List;
import org.springframework.web.multipart.MultipartFile;
import ticketmaster.demo.posting.dto.PostingManageDto.PostingCreateRequestDto;
import ticketmaster.demo.posting.dto.PostingManageDto.PostingCreateResponseDto;
import ticketmaster.demo.posting.dto.PostingManageDto.PostingGetResponseDto;

public interface PostingService {

    PostingGetResponseDto getPosting(Long postingId);

    PostingCreateResponseDto createPosting(MultipartFile imageFile, PostingCreateRequestDto requestDto);

    List<PostingGetResponseDto> getAllPosting();
}
