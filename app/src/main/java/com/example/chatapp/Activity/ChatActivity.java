package com.example.chatapp.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.chatapp.Data.ChatDTO;
import com.example.chatapp.Data.ChatlistItem;
import com.example.chatapp.Adapter.ChattingAdapter;
import com.example.chatapp.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ChatActivity extends AppCompatActivity {
    private String CHAT_NAME;
    private String USER_NAME;

    private ListView chat_view;
    private EditText chat_edit;
    private Button chat_send;

    private TextView chatroom_title;
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();
    private ArrayList<ChatlistItem> mItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        mItems = new ArrayList<ChatlistItem>();
        // 위젯 ID 참조
        chat_view = (ListView) findViewById(R.id.listView);
        chat_edit = (EditText) findViewById(R.id.chat_EditText);
        chat_send = (Button) findViewById(R.id.send);

        // 로그인 화면에서 받아온 채팅방 이름, 유저 이름 저장
        Intent intent = getIntent();
        CHAT_NAME = intent.getStringExtra("chatName");
        USER_NAME = intent.getStringExtra("userName");

        chatroom_title = (TextView)findViewById(R.id.chat_username);
        chatroom_title.setText(CHAT_NAME);
        // 채팅 방 입장
        openChat(CHAT_NAME);

        // 메시지 전송 버튼에 대한 클릭 리스너 지정
        chat_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (chat_edit.getText().toString().equals(""))
                    return;

                ChatDTO chat = new ChatDTO(USER_NAME, chat_edit.getText().toString()); //ChatDTO를 이용하여 데이터를 묶는다.
                databaseReference.child("chat").child(CHAT_NAME).push().setValue(chat); // 데이터 푸쉬
                chat_edit.setText(""); //입력창 초기화

            }
        });
    }

    private void addMessage(DataSnapshot dataSnapshot, ChattingAdapter adapter) {
        ChatlistItem chatlistItem = dataSnapshot.getValue(ChatlistItem.class);
        Log.d("datasnapshot","chatistItem : "+chatlistItem.getUserName()+","+chatlistItem.getMessage());
        adapter.addItem(chatlistItem.getUserName(),chatlistItem.getMessage());
        Log.d("datasnapshot","chatistItem : "+adapter.getCount());
    }

   /* private void removeMessage(DataSnapshot dataSnapshot, ArrayAdapter<String> adapter) {
        ChatDTO chatDTO = dataSnapshot.getValue(ChatDTO.class);
        adapter.remove(chatDTO.getUserName() + " : " + chatDTO.getMessage());
    }*/

    private void openChat(String chatName) {
        // 리스트 어댑터 생성 및 세팅
        final ChattingAdapter adapter = new ChattingAdapter(this,mItems);
        chat_view.setAdapter(adapter);

        // 데이터 받아오기 및 어댑터 데이터 추가 및 삭제 등..리스너 관리
        databaseReference.child("chat").child(chatName).addChildEventListener(new ChildEventListener() {
                @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    Log.d("datasnapshot","data : "+dataSnapshot);
                    addMessage(dataSnapshot, adapter);
                    adapter.notifyDataSetChanged();
                    Log.e("LOG", "s:"+s);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                //removeMessage(dataSnapshot, adapter);
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}