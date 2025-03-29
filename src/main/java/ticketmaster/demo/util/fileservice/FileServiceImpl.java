package ticketmaster.demo.util.fileservice;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import ticketmaster.demo.util.fileservice.FileServiceDto.FileServiceUploadDto;

@Slf4j
@Service
public class FileServiceImpl implements FileService {

    private final Path directoryLocation;
    private final String BASE_URL;

    public FileServiceImpl(
            @Value("${file.upload.base-url}") String baseUrl,
            @Value("${file.upload.location}") String rootPath) {
//        this.directoryLocation = Paths.get(rootPath).toAbsolutePath().normalize();
        this.directoryLocation = Paths.get(rootPath);
        this.BASE_URL = baseUrl;
    }

    @Override
    public FileServiceUploadDto saveFile(String path, MultipartFile multipartFile) {
        if (multipartFile.isEmpty()) {
            throw new IllegalArgumentException("");
        }

        String fileName = multipartFile.getOriginalFilename();
        // replace specific symbols to underscore
        String normalizedFileName = fileName.replaceAll("[\\\\/:*?=&\"<> |]", "_");
        // extract extension
        String extension = StringUtils.getFilenameExtension(normalizedFileName);
        // remove file extension from its name
        normalizedFileName = StringUtils.stripFilenameExtension(normalizedFileName);
        // add UUID to avoid duplicate problem
        String realName = normalizedFileName + "_" + UUID.randomUUID() + "." + extension;

        String sanitizedPath = path.startsWith("/") ? path.substring(1) : path;
        Path location = this.directoryLocation.resolve(sanitizedPath).resolve(realName);

        try {
            // Create dir if doesn't exist
            Files.createDirectories(location);
            Files.copy(multipartFile.getInputStream(), location,
                    StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e){
            log.error("Fail to save the file: {}", e.getMessage());
            throw new FileServiceException("Fail to upload the file");
        }

        return FileServiceUploadDto.builder()
                .path(path + "/" + realName).build();
    }

    @Override
    public void fileSizeCheck(MultipartFile multipartFile, Long max) {
        long maxSize = max * 1024 * 1024;
        if (multipartFile.getSize() > maxSize) {
            throw new IllegalArgumentException("Only under " + max + "can be uploaded");
        }
    }

    @Override
    public void checkIsImageFile(MultipartFile imageFile) {
        if (!imageFile.getContentType().startsWith("image")) {
            throw new IllegalArgumentException("Only image file can be uploaded");
        }
    }
}

