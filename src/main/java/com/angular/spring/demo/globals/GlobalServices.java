package com.angular.spring.demo.globals;

import com.angular.spring.demo.api.service.DataService;
import com.angular.spring.demo.api.service.FirebaseInit;
import com.google.cloud.firestore.Firestore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GlobalServices {

    public static FirebaseInit firebaseInit;
    public static Firestore firestore;
    public static DataService dataService;

    @Autowired
    public void setFirebaseInit(FirebaseInit firebaseInit) {
        GlobalServices.firebaseInit = firebaseInit;
    }

    @Autowired
    public void setFirestore(FirebaseInit firebaseInit) {
        GlobalServices.firestore = firebaseInit.getFireStore();
    }

    @Autowired
    public void setDataService(DataService dataService) {
        GlobalServices.dataService = dataService;
    }

}
