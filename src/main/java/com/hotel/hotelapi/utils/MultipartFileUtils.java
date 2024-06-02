package com.hotel.hotelapi.utils;

import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class MultipartFileUtils {
    public static MultipartFile createDefaultImage() {
        try {
            byte[] defaultImageData = Files.readAllBytes(Paths.get("src/main/resources/images/default_service.png"));
            return new MockMultipartFile("default_service.png", "default_service.png", "image/png", defaultImageData);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
