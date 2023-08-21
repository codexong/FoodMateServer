package com.example.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.model.Meeting;
import com.example.model.Member;
import com.example.model.Message;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MeetingServiceImpl implements MeetingService {

	public static final String COLLECTION_NAME="Meeting";
	
	@Override
	public List<Meeting> getMeetingByNickname(String nickname) {
		Firestore firestore = FirestoreClient.getFirestore();
		CollectionReference collectionReference = firestore.collection(COLLECTION_NAME);
		ApiFuture<QuerySnapshot> querySnapshotApiFuture = collectionReference.get();
		QuerySnapshot querySnapshot;
		try {
			querySnapshot = querySnapshotApiFuture.get();
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
			return new ArrayList<>();
		}
		
		return querySnapshot.getDocuments().stream()
				.map(documentSnapshot -> documentSnapshot.toObject(Meeting.class))
				.filter(meeting -> meeting.getUser().stream().anyMatch(member -> member.getNickname().equals(nickname)))
				.collect(Collectors.toList());
	}

	@Override
	public String insertMeeting(String boardid, Meeting meeting) {
		Firestore firestore = FirestoreClient.getFirestore();
		DocumentReference addedDocRef = firestore.collection(COLLECTION_NAME).document(boardid);
	
        ApiFuture<WriteResult> apiFuture = addedDocRef.set(meeting);
        try {
			return apiFuture.get().getUpdateTime().toString();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		return null;
		
	}

	@Override
	public Meeting getOneMeeting(String boardid) {
		Firestore firestore = FirestoreClient.getFirestore();
        DocumentReference documentReference = firestore.collection(COLLECTION_NAME).document(boardid);
        ApiFuture<DocumentSnapshot> apiFuture = documentReference.get();
        DocumentSnapshot documentSnapshot = null;
		try {
			documentSnapshot = apiFuture.get();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
        Meeting meeting = null;

        if(documentSnapshot.exists()) {
        	meeting = documentSnapshot.toObject(Meeting.class);
        	System.out.println("meeting의 변경전 속성값 제목 : " + meeting);
        	System.out.println("meeting의 변경전 속성값 제목 : " + meeting.getMeeting_title());
    		System.out.println("meeting의 변경전  속성값 내용 : " + meeting.getMeeting_content());
    		System.out.println("meeting의 변경전  속성값 날짜 : " + meeting.getDate());
                return meeting;
        } else {
                return null;
        }
	}
	
	@Override
    public String deleteMeeting(String boardid) {
           Firestore firestore = FirestoreClient.getFirestore();
           ApiFuture<WriteResult> apiFuture = firestore.collection(COLLECTION_NAME).document(boardid).delete();         
           return "Meeting id :" + boardid + " delete";
    }
	
	@Override
	public String updateMeeting(String boardid, Meeting meeting) {
		Firestore firestore = FirestoreClient.getFirestore();
		DocumentReference documentReference = firestore.collection(COLLECTION_NAME).document(boardid);
		Meeting beforeMeeting = getOneMeeting(boardid);		
		beforeMeeting.setMeeting_title(meeting.getMeeting_title());
		beforeMeeting.setMeeting_content(meeting.getMeeting_content());		
		ApiFuture<WriteResult> apiFuture = documentReference.set(beforeMeeting);				
		try {
			return apiFuture.get().getUpdateTime().toString();
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
    public String addMember(String boardid, Member member) {
        Firestore firestore = FirestoreClient.getFirestore();
        DocumentReference documentReference = firestore.collection(COLLECTION_NAME).document(boardid);

        // Get the meeting document
        ApiFuture<DocumentSnapshot> apiFuture = documentReference.get();
        DocumentSnapshot documentSnapshot;
        try {
            documentSnapshot = apiFuture.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return null;
        }

        if (documentSnapshot.exists()) {
            Meeting meeting = documentSnapshot.toObject(Meeting.class);
            List<Member> user = meeting.getUser();
            user.add(member);
            meeting.setUser(user);

            ApiFuture<WriteResult> updateFuture = documentReference.set(meeting);
            try {
                updateFuture.get();
                return "Participant added successfully";
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
                return null;
            }
        } else {
            return "Meeting not found";
        }
    }

	@Override
    public String removeMember(String boardid, Member member) {
        Firestore firestore = FirestoreClient.getFirestore();
        DocumentReference documentReference = firestore.collection(COLLECTION_NAME).document(boardid);

        // Get the meeting document
        ApiFuture<DocumentSnapshot> apiFuture = documentReference.get();
        DocumentSnapshot documentSnapshot;
        try {
            documentSnapshot = apiFuture.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return null;
        }

        if (documentSnapshot.exists()) {
            Meeting meeting = documentSnapshot.toObject(Meeting.class);

            // Remove the participant from the meeting
            List<Member> user = meeting.getUser();
            if (user != null) {
            	user.remove(member);
            }

            // Update the meeting document
            ApiFuture<WriteResult> updateFuture = documentReference.set(meeting);
            try {
                updateFuture.get();
                return "Participant removed successfully";
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
                return null;
            }
        } else {
            return "Meeting not found";
        }
    }

	@Override
    public String addMessage(String boardid, Message message) {
        Firestore firestore = FirestoreClient.getFirestore();
        DocumentReference documentReference = firestore.collection(COLLECTION_NAME).document(boardid);

        // Get the meeting document
        ApiFuture<DocumentSnapshot> apiFuture = documentReference.get();
        DocumentSnapshot documentSnapshot;
        try {
            documentSnapshot = apiFuture.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return null;
        }

        if (documentSnapshot.exists()) {
            Meeting meeting = documentSnapshot.toObject(Meeting.class);

            // Add the message to the meeting
            List<Message> messages = meeting.getMessages();
            if (messages == null) {
                messages = new ArrayList<>();
            }
            messages.add(message);
            meeting.setMessages(messages);

            // Update the meeting document
            ApiFuture<WriteResult> updateFuture = documentReference.set(meeting);
            try {
                updateFuture.get();
                return "Message sent successfully";
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
                return null;
            }
        } else {
            return "Meeting not found";
        }
    }

		
}
