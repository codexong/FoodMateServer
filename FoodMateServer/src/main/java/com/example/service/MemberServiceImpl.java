package com.example.service;

import java.util.Base64;
import java.util.concurrent.ExecutionException;

import org.springframework.stereotype.Service;

import com.example.model.Member;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
   
   public static final String COLLECTION_NAME="Member";
   
   
   @Override
    public String insertMember(Member member) throws Exception {
           Firestore firestore = FirestoreClient.getFirestore();
           ApiFuture<WriteResult> apiFuture = firestore.collection(COLLECTION_NAME).document(member.getId()).set(member);
           return apiFuture.get().getUpdateTime().toString();
    }
   

    @Override
    public Member getMemberDetail(String id) throws Exception {
           Firestore firestore = FirestoreClient.getFirestore();
           DocumentReference documentReference = firestore.collection(COLLECTION_NAME).document(id);
           ApiFuture<DocumentSnapshot> apiFuture = documentReference.get();
           DocumentSnapshot documentSnapshot = apiFuture.get();
           Member member = null;

           if(documentSnapshot.exists()) {
                   member = documentSnapshot.toObject(Member.class);
                   return member;
           } else {
                   return null;
           }
    }

    @Override
    public String updateMember(Member member) throws Exception {
           Firestore firestore = FirestoreClient.getFirestore();
           ApiFuture<WriteResult> apiFuture = firestore.collection(COLLECTION_NAME).document(member.getId()).set(member);
           return apiFuture.get().getUpdateTime().toString();
    }

    @Override
    public String deleteMember(String id) throws Exception {
           Firestore firestore = FirestoreClient.getFirestore();
           ApiFuture<WriteResult> apiFuture = firestore.collection(COLLECTION_NAME).document(id).delete();         
           return "Document id :" + id + " delete";
    }
    
    @Override
    public boolean verifyLogin(String id, String pw) {
        Firestore firestore = FirestoreClient.getFirestore();
        DocumentReference documentReference = firestore.collection(COLLECTION_NAME).document(id);
        ApiFuture<DocumentSnapshot> apiFuture = documentReference.get();

        try {
            DocumentSnapshot documentSnapshot = apiFuture.get();
            if (documentSnapshot.exists()) {
                Member member = documentSnapshot.toObject(Member.class);
                String encodedPassword = member.getPw();
                // 패스워드 검증 로직 구현
                boolean matches = pw.equals(encodedPassword);
                return matches;
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        return false;
    }
    

    @Override
    public String getNickname(String id) throws Exception {
        Firestore firestore = FirestoreClient.getFirestore();
        DocumentReference documentReference = firestore.collection(COLLECTION_NAME).document(id);
        ApiFuture<DocumentSnapshot> apiFuture = documentReference.get();
        DocumentSnapshot documentSnapshot = apiFuture.get();
        Member member = null;

        if (documentSnapshot.exists()) {
            member = documentSnapshot.toObject(Member.class);
            return member.getNickname();
        } else {
            return "DefaultNickname"; // 기본값 설정
        }
    }


    @Override
    public byte[] getImageData(String id) {
        Firestore firestore = FirestoreClient.getFirestore();
        DocumentReference documentReference = firestore.collection(COLLECTION_NAME).document(id);
        ApiFuture<DocumentSnapshot> apiFuture = documentReference.get();

        try {
            DocumentSnapshot documentSnapshot = apiFuture.get();
            if (documentSnapshot.exists()) {
                Member member = documentSnapshot.toObject(Member.class);
                String encodedImage = member.getDecodedImage();
                return Base64.getDecoder().decode(encodedImage);
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        return null;
    }

   
}