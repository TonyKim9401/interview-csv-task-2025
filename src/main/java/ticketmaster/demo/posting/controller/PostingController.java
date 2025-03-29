package ticketmaster.demo.posting.controller;

import static ticketmaster.demo.posting.dto.PostingManageDto.PostingCreateRequestDto;

import jakarta.validation.Valid;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import ticketmaster.demo.posting.dto.PostingManageDto.PostingCreateResponseDto;
import ticketmaster.demo.posting.dto.PostingManageDto.PostingGetResponseDto;
import ticketmaster.demo.posting.service.PostingService;


@Slf4j
@RestController
@RequestMapping("/instagram/posting")
public class PostingController {

    private final PostingService postingService;

    public PostingController(PostingService postingService) {
        this.postingService = postingService;
    }

    @GetMapping("")
    public ResponseEntity<PostingGetResponseDto> getPosting(@RequestParam("postingId") Long postingId) {
        PostingGetResponseDto responseDto = postingService.getPosting(postingId);
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/all")
    public ResponseEntity<List<PostingGetResponseDto>> getAllPosting() {
        List<PostingGetResponseDto> responseDtos = postingService.getAllPosting();
        return ResponseEntity.ok(responseDtos);
    }

    @PostMapping(value = "", consumes = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.MULTIPART_FORM_DATA_VALUE
    })
    public ResponseEntity<PostingCreateResponseDto> createPosting(
            @RequestPart(value="imageFile", required = false) MultipartFile imageFile,
            @RequestPart(value="postingInfo", required = true) @Valid PostingCreateRequestDto requestDto
    ) {
        PostingCreateResponseDto responseDto = postingService.createPosting(imageFile, requestDto);
        return ResponseEntity.ok(responseDto);
    }
}
