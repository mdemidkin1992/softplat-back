package ru.softplat.security.server.web.validation;

import org.springframework.web.multipart.MultipartFile;
import ru.softplat.security.server.exception.ImageFormatException;
import ru.softplat.security.server.message.ExceptionMessage;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class MultipartFileFormatValidator implements ConstraintValidator<MultipartFileFormat, MultipartFile> {
    @Override
    public boolean isValid(MultipartFile file, ConstraintValidatorContext context) {
        if (file.isEmpty()) {
            throw new ImageFormatException(ExceptionMessage.NO_IMAGE_CONTENT_EXCEPTION.label);
        }

        String contentType = file.getContentType();
        if (contentType == null || (!contentType.equals("image/jpeg") && !contentType.equals("image/png"))) {
            throw new ImageFormatException(ExceptionMessage.IMAGE_FORMAT_EXCEPTION.label);
        }

        return true;
    }
}
