package com.example.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.springframework.stereotype.Repository;

import com.example.model.Member;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.firebase.cloud.FirestoreClient;

import lombok.extern.slf4j.Slf4j;

@Repository
@Slf4j
public class MemberDao {

    public static final String COLLECTION_NAME = "Member";

    public List<Member> getMember() throws ExecutionException, InterruptedException {
        List<Member> list = new ArrayList<>();
        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<QuerySnapshot> future = db.collection(COLLECTION_NAME).get();
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();
        System.out.println("documents 받는지 여부: "+documents);
        for (QueryDocumentSnapshot document : documents) {
            list.add(document.toObject(Member.class));
        }
        return list;
    }

}
