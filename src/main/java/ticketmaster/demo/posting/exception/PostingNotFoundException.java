package ticketmaster.demo.posting.exception;

public class PostingNotFoundException extends RuntimeException{
    public PostingNotFoundException(String message) {
        super(message);
    }
}
