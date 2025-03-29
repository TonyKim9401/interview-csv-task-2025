package ticketmaster.demo.util.fileservice;

import org.springframework.web.multipart.MultipartFile;
import ticketmaster.demo.util.fileservice.FileServiceDto.FileServiceUploadDto;

public interface FileService {

    FileServiceUploadDto saveFile(String path, MultipartFile multipartFile)
            throws FileServiceException;

    void fileSizeCheck(MultipartFile multipartFile, Long max);

    void checkIsImageFile(MultipartFile multipartFile);
}
