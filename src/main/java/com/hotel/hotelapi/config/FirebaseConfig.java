package com.hotel.hotelapi.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.io.FileInputStream;
import java.io.IOException;

@Configuration
public class FirebaseConfig {
    private static final String SERVICE_ACCOUNT_KEY_PATH = System.getenv("GOOGLE_APPLICATION_CREDENTIALS");

    private static boolean firebaseAppInitialized = false;

    @PostConstruct
    public void initialize() {
        if (!firebaseAppInitialized) {
            try { // Init firebase app, will automatically call by @PostConstruct
                FileInputStream serviceAccount = new FileInputStream(SERVICE_ACCOUNT_KEY_PATH);
                FirebaseOptions options = new FirebaseOptions.Builder()
                        .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                        .build();
                FirebaseApp.initializeApp(options, "HapBee Hotel");
                firebaseAppInitialized = true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Bean
    public Storage firebaseStorage() throws IOException {
        // Ensure FirebaseApp is initialized before accessing it
        initialize();

        String projectId = FirebaseApp.getInstance("HapBee Hotel").getOptions().getProjectId();
        GoogleCredentials credentials = GoogleCredentials.fromStream(new FileInputStream(SERVICE_ACCOUNT_KEY_PATH));
        StorageOptions storageOptions = StorageOptions.newBuilder()
                .setProjectId(projectId)
                .setCredentials(credentials)
                .build();
        return storageOptions.getService();
    }
}