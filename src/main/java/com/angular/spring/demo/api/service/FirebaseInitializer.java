package com.angular.spring.demo.api.service;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;

@Service
public class FirebaseInitializer implements FirebaseInit {
    private static final Logger logger = LogManager.getLogger(DataServiceImp.class);

    @Override
    @PostConstruct
    public void initialization() throws IOException {

        logger.info("FIREBASE RUNNING IN APPLICATION");

        ClassLoader classLoader = getClass().getClassLoader();
        InputStream serviceAccount = classLoader.getResourceAsStream("serviceAccountKey.json");

        assert serviceAccount != null;
        FirebaseOptions options = FirebaseOptions.builder().setCredentials(GoogleCredentials.fromStream(serviceAccount)).build();

        if (FirebaseApp.getApps().isEmpty()) {
            FirebaseApp.initializeApp(options);
        }

    }

    @Override
    public Firestore getFireStore() {
        return FirestoreClient.getFirestore();
    }

}
