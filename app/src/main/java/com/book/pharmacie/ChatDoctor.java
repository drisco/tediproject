package com.book.pharmacie;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.book.pharmacie.adapter.MessageAdapter;
import com.book.pharmacie.model.Message;
import com.book.pharmacie.model.QuestionResponse;
import com.book.pharmacie.model.User;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class ChatDoctor extends AppCompatActivity {

    private RecyclerView recyclerView;
    private EditText inputMessage;
    private ImageView sendButton,regisButton;
    private List<Message> messages;
    private MessageAdapter adapter;
    SharedPreferencesHelper preferencesHelper;
    User user;

    private String[] questions  = {
            // Premières questions
            "Bonjour ! Pour commencer, pouvez-vous me dire votre nom et votre âge ?",
            "Merci ! Quelle est votre taille et votre poids ?",
            "Pouvez-vous me confirmer votre adresse actuelle, au cas où nous aurions besoin d'envoyer une assistance ?",
            "Avez-vous des maladies comme le diabète, l'hypertension ou des allergies ?",
            "Prenez-vous des médicaments actuellement ? Si oui, lesquels ?",

            // Questions sur le motif de consultation
            "Merci pour ces informations. Pour mieux comprendre votre problème, quel est le motif principal de votre consultation aujourd'hui ?",
            "Depuis combien de temps ressentez-vous ces symptômes ?",
            "Sur une échelle de 1 à 10, comment évalueriez-vous l'intensité de la douleur ou de l'inconfort ?",
            "Avez-vous déjà consulté un médecin pour ce problème auparavant ?",
            "Est-ce la première fois que vous ressentez ce type de symptômes ?",

            // Symptômes spécifiques
            "Merci. Avez-vous actuellement de la fièvre ? Si oui, à combien est votre température ?",
            "Avez-vous des nausées, vomissements ou douleurs abdominales ?",
            "Ressentez-vous des difficultés respiratoires ou une lourdeur dans la poitrine ?",
            "Avez-vous eu des maux de tête, vertiges ou évanouissements récemment ?",
            "Avez-vous remarqué une perte d'appétit ou une perte de poids dernièrement ?",

            // Questions sur le mode de vie
            "Passons à vos habitudes de vie. Consommez-vous du tabac ou de l'alcool ?",
            "Pratiquez-vous une activité physique régulière ?",
            "Avez-vous des habitudes de sommeil régulières ou des troubles du sommeil ?",
            "Suivez-vous un régime alimentaire particulier ?",
            "Avez-vous des allergies connues, qu'elles soient alimentaires ou médicamenteuses ?",

            // Message final
    };
    private int questionIndex = 0;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_chat_doctor);
        preferencesHelper = new SharedPreferencesHelper(this);
        user = preferencesHelper.getUser();
        recyclerView = findViewById(R.id.recyclerView);
        inputMessage = findViewById(R.id.inputMessage);
        sendButton = findViewById(R.id.sendButton);
        regisButton = findViewById(R.id.regisButton);

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

        regisButton.setOnClickListener(v -> {
            saveConversationToFirebase();
        });
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void saveConversationToFirebase() {
        // Initialiser une liste de QuestionResponse
        List<QuestionResponse> questionResponses = new ArrayList<>();

        // Remplir la liste avec les questions et réponses
        for (int i = 0; i < messages.size(); i += 2) { // Les questions sont à des indices pairs
            String question = messages.get(i).getText(); // Texte du bot (question)
            String response = (i + 1 < messages.size()) ? messages.get(i + 1).getText() : ""; // Texte de l'utilisateur (réponse)
            questionResponses.add(new QuestionResponse(question, response));
        }

        // Enregistrer dans Firebase
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("ChatDoctorpatient");

        // Sauvegarder la conversation
        ref.child(user.getUserId()).setValue(questionResponses)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(this, "Conversation sauvegardée avec succès.", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(this,MainActivity.class));
                        finish();
                    } else {
                        Toast.makeText(this, "Erreur lors de la sauvegarde de la conversation.", Toast.LENGTH_SHORT).show();
                    }
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
            sendButton.setVisibility(View.GONE);
            regisButton.setVisibility(View.VISIBLE);
            sendBotMessage("Merci pour toutes ces informations ! Nous les analyserons et les transmettrons au docteur pour une analyse plus claire. Un médecin va bientôt vous rejoindre pour la consultation en vidéo. Veuillez patienter, il sera avec vous très bientôt.");
        }
    }

    private void sendBotMessage(String message) {
        messages.add(new Message(message, true)); // Ajouter la question du bot
        adapter.notifyItemInserted(messages.size() - 1);
        recyclerView.scrollToPosition(messages.size() - 1);
    }
}