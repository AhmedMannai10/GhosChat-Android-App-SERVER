package com.content.xchat_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;

import entity.User;

public class MainActivity extends AppCompatActivity {

    private RecyclerView chatRV;
    private EditText editSendMsg;
    private ImageView btnSendMsg;
    private ArrayList<Message>  messages;
    private MessageAdapter messageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        User user = (User) intent.getSerializableExtra("user");
        setContentView(R.layout.activity_main);

        chatRV = findViewById(R.id.rv_chats);
        editSendMsg = findViewById(R.id.edit_send_msg);
        btnSendMsg = findViewById(R.id.send_logo);

        messages = new ArrayList<Message>();
        messageAdapter = new MessageAdapter(messages, this);

        LinearLayoutManager manager = new LinearLayoutManager(this);

        chatRV.setLayoutManager(manager);
        chatRV.setAdapter(messageAdapter);

        btnSendMsg.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                if(editSendMsg.getText().toString().isEmpty()){
                    Toast.makeText(MainActivity.this, "Please insert message", Toast.LENGTH_SHORT).show();
                    return;
                }
                messages.add(new Message( editSendMsg.getText().toString(), "currentUser"));
                messageAdapter.notifyDataSetChanged();
                editSendMsg.setText("");
            }
        });

    }
}