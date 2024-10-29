package com.book.pharmacie;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.book.pharmacie.adapter.MessageAdapter;
import com.book.pharmacie.model.Message;

import java.util.ArrayList;
import java.util.List;

public class ChatDoctor extends AppCompatActivity {

    private RecyclerView recyclerView;
    private EditText inputMessage;
    private ImageView sendButton;
    private List<Message> messages;
    private MessageAdapter adapter;

    private String[] questions = {
            "Quel est votre nom complet ?",
            "Quel est votre âge ?",
            "Quel est votre sexe ?",
            "Avez-vous des antécédents médicaux ?",
            "Ressentez-vous de la douleur ?",
            "Avez-vous des difficultés respiratoires ?",
            "Avez-vous des nausées ou des vomissements ?",
            "Avez-vous ressenti des maux de tête ?",
            "Merci d'avoir répondu aux questions. Votre consultation est terminée."
    };
    private int questionIndex = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_chat_doctor);

        recyclerView = findViewById(R.id.recyclerView);
        inputMessage = findViewById(R.id.inputMessage);
        sendButton = findViewById(R.id.sendButton);

        messages = new ArrayList<>();
        adapter = new MessageAdapter(messages);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Démarrer la conversation avec la première question
        sendBotMessage(questions[questionIndex]);

        sendButton.setOnClickListener(v -> {
            String userMessage = inputMessage.getText().toString().trim();
            if (!userMessage.isEmpty()) {
                sendMessage(userMessage);
            }
        });
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void sendMessage(String message) {
        messages.add(new Message(message, false)); // Ajouter la réponse utilisateur
        adapter.notifyItemInserted(messages.size() - 1);
        recyclerView.scrollToPosition(messages.size() - 1);
        inputMessage.setText("");

        // Passer à la question suivante
        questionIndex++;
        if (questionIndex < questions.length) {
            sendBotMessage(questions[questionIndex]);
        } else {
            sendBotMessage("La consultation est terminée. Merci !");
        }
    }

    private void sendBotMessage(String message) {
        messages.add(new Message(message, true)); // Ajouter la question du bot
        adapter.notifyItemInserted(messages.size() - 1);
        recyclerView.scrollToPosition(messages.size() - 1);
    }
}