package com.angular.spring.demo.api.service;

import com.google.cloud.firestore.Firestore;

import java.io.IOException;

public interface FirebaseInit {

    void initialization() throws IOException;
    
    Firestore getFireStore();

}
