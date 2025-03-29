package ticketmaster.demo.util.fileservice;

import lombok.Builder;
import lombok.Getter;

public class FileServiceDto {

    @Getter
    @Builder
    public static class FileServiceUploadDto {
        private String path;
    }
}
