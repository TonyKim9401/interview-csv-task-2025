package ticketmaster.demo.posting.service;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;
import ticketmaster.demo.posting.dto.Posting;
import ticketmaster.demo.posting.dto.PostingManageDto.PostingCreateRequestDto;
import ticketmaster.demo.posting.dto.PostingManageDto.PostingCreateResponseDto;
import ticketmaster.demo.posting.dto.PostingManageDto.PostingGetResponseDto;
import ticketmaster.demo.posting.exception.PostingNotFoundException;
import ticketmaster.demo.posting.repository.PostingRepository;
import ticketmaster.demo.util.fileservice.FileService;
import ticketmaster.demo.util.fileservice.FileServiceDto.FileServiceUploadDto;

@Slf4j
@Service
public class PostingServiceImpl implements PostingService {

    private final PostingRepository postingRepository;
    private final FileService fileService;

    public PostingServiceImpl(
            PostingRepository postingRepository,
            FileService fileService) {
        this.postingRepository = postingRepository;
        this.fileService = fileService;
    }

    @Transactional(readOnly = true)
    @Override
    public PostingGetResponseDto getPosting(Long postingId) {
        Posting posting = postingRepository.findById(postingId)
                .orElseThrow(() -> new PostingNotFoundException(
                        "Posting with ID " + postingId + " not found"));
        return PostingGetResponseDto.fromEntity(posting);
    }

    @Transactional(readOnly = true)
    @Override
    public List<PostingGetResponseDto> getAllPosting() {
        return postingRepository.findAll().stream()
                .map(PostingGetResponseDto::fromEntity)
                .toList();
    }

    @Transactional
    @Override
    public PostingCreateResponseDto createPosting(MultipartFile imageFile,
            PostingCreateRequestDto requestDto) {
        validateImageFile(imageFile);

        FileServiceUploadDto fileServiceUploadDto = uploadImageFile(imageFile);
        requestDto.setImageUrl(fileServiceUploadDto.getPath());

        Posting posting = requestDto.toEntity();
        postingRepository.save(posting);

        return PostingCreateResponseDto.fromEntity(posting);
    }

    private void validateImageFile(MultipartFile... imageFiles) {
        if (ObjectUtils.isEmpty(imageFiles)) {
            log.info("Image file does not exist");
            return;
        }

        List.of(imageFiles).forEach(
                imageFile -> {
                    fileService.fileSizeCheck(imageFile, 2L);
                    fileService.checkIsImageFile(imageFile);
                }
        );
    }

    private FileServiceUploadDto uploadImageFile(MultipartFile imageFile) {
        return fileService.saveFile("/posting/img/", imageFile);
    }
}
