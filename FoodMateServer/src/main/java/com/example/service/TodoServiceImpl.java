package com.example.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.model.Board;
import com.example.model.Todo;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.Query;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TodoServiceImpl implements TodoService{
	 public static final String COLLECTION_NAME="Todo";
	 
	 @Override
     public String insertTodo(Todo todo) throws Exception {
             Firestore firestore = FirestoreClient.getFirestore();
             DocumentReference addedDocRef = firestore.collection(COLLECTION_NAME).document();
             String id = addedDocRef.getId();
             todo.setTodoid(id);
             ApiFuture<WriteResult> apiFuture = addedDocRef.set(todo);          
             return apiFuture.get().getUpdateTime().toString();
      }

	@Override
	public List<Todo> getAlltodo() throws Exception {
		 Firestore firestore = FirestoreClient.getFirestore();
	        CollectionReference collectionReference = firestore.collection(COLLECTION_NAME);
	        ApiFuture<QuerySnapshot> querySnapshot = collectionReference.get();
	        List<Todo> todoList = new ArrayList<>();

	        for (DocumentSnapshot document : querySnapshot.get().getDocuments()) {
	           Todo todo = document.toObject(Todo.class);

	           todoList.add(todo);
	        }
	        return todoList;
	}
	
	@Override
    public List<Todo> getMyTodo(String userNicname) throws Exception {
        Firestore firestore = FirestoreClient.getFirestore();
        CollectionReference collectionReference = firestore.collection(COLLECTION_NAME);

        Query query = collectionReference.whereEqualTo("userNicname", userNicname);
        ApiFuture<QuerySnapshot> querySnapshot = query.get();

        List<Todo> myTodoList = new ArrayList<>();
        QuerySnapshot snapshot = querySnapshot.get();
        List<QueryDocumentSnapshot> documents = snapshot.getDocuments();
        for (QueryDocumentSnapshot document : documents) {
            Todo todo = document.toObject(Todo.class);
            myTodoList.add(todo);
        }

        return myTodoList;
    }
	
    @Override
    public String deleteTodo(String id) throws Exception {
           Firestore firestore = FirestoreClient.getFirestore();
           ApiFuture<WriteResult> apiFuture = firestore.collection(COLLECTION_NAME).document(id).delete();         
           return "Document id :" + id + " delete";
    }
}
