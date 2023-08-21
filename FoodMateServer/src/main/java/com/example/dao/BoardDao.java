package com.example.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import com.example.model.Board;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.firebase.cloud.FirestoreClient;

public class BoardDao {
	 public static final String COLLECTION_NAME = "Board";

	    public List<Board> getMember() throws ExecutionException, InterruptedException {
	        List<Board> list = new ArrayList<>();
	        Firestore db = FirestoreClient.getFirestore();
	        ApiFuture<QuerySnapshot> future = db.collection(COLLECTION_NAME).get();
	        List<QueryDocumentSnapshot> documents = future.get().getDocuments();
	        System.out.println("documents 받는지 여부: "+documents);
	        for (QueryDocumentSnapshot document : documents) {
	            list.add(document.toObject(Board.class));
	        }
	        return list;
	    }
}


