package com.hotel.hotelapi.service;


import com.google.cloud.storage.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Service
public class FirebaseServiceImpl {
    @Value("${firebase.storage.bucket}")
    private String bucketName;

    @Autowired
    private Storage firebaseStorage;

    public String uploadImagesToFirebase(MultipartFile imageFile) throws IOException {
        Storage storage = firebaseStorage;

        // Generate unique image name
        String imageName = UUID.randomUUID() + "-" + imageFile.getOriginalFilename();

        // Upload image to bucket
        BlobId blobId = BlobId.of(bucketName, imageName);

        try (InputStream inputStream = imageFile.getInputStream()) {
            BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType(imageFile.getContentType()).build();
            Blob blob = storage.create(blobInfo, inputStream.readAllBytes());
            return blob.getMediaLink();
        }
    }
}
