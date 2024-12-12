package hu.elte.thesis.exception;

public class ImageProcessingException extends RuntimeException {

    private static final long serialVersionUID = -6606040690383882325L;

    public ImageProcessingException(String fileName) {
        super("Error during image processing: " + fileName);
    }

}
