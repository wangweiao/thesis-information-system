package hu.elte.thesis.mapper;

import hu.elte.thesis.exception.ImageProcessingException;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Component
public class MultipartFileMapper {
    public byte[] fileToByteArray(MultipartFile file) {
        if (file == null) {
            return new byte[0];
        }
        try {
            return file.getBytes();
        } catch (IOException e) {
            throw new ImageProcessingException(e.getMessage());
        }

    }
}
