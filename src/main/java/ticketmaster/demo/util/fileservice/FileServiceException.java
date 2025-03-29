package ticketmaster.demo.util.fileservice;

import lombok.Getter;

@Getter
public class FileServiceException extends RuntimeException{

    public FileServiceException(String message) {
        super(message);
    }
}
