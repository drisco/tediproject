package com.book.pharmacie;

import android.content.Context;
import android.content.SharedPreferences;

import com.book.pharmacie.model.User;

public class SharedPreferencesHelper {

    // Nom du fichier SharedPreferences
    private static final String PREF_NAME = "user_pref";

    // Clés pour stocker les informations de l'utilisateur
    private static final String KEY_USER_ID = "user_id";
    private static final String KEY_USER_NAME = "user_name";
    private static final String KEY_USER_EMAIL = "user_email";
    private static final String KEY_USER_PHONE = "user_phone";
    private static final String KEY_USER_PASSWORD = "user_password";

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    // Constructeur de la classe
    public SharedPreferencesHelper(Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    // Ajouter ou mettre à jour les données utilisateur
    public void addUser(String userId, String name, String email, String phone, String password) {
        editor.putString(KEY_USER_ID, userId);
        editor.putString(KEY_USER_NAME, name);
        editor.putString(KEY_USER_EMAIL, email);
        editor.putString(KEY_USER_PHONE, phone);
        editor.putString(KEY_USER_PASSWORD, password);
        editor.apply(); // Sauvegarder les modifications
    }

    // Afficher les données utilisateur (retourner un objet User)
    public User getUser() {
        String userId = sharedPreferences.getString(KEY_USER_ID, null);
        String name = sharedPreferences.getString(KEY_USER_NAME, null);
        String email = sharedPreferences.getString(KEY_USER_EMAIL, null);
        String phone = sharedPreferences.getString(KEY_USER_PHONE, null);
        String password = sharedPreferences.getString(KEY_USER_PASSWORD, null);

        if (userId != null && name != null && email != null && phone != null && password != null) {
            return new User(userId, name, email, phone, password);
        }

        // Retourner null si aucune donnée n'est trouvée
        return null;
    }

    // Supprimer les données utilisateur
    public void deleteUser() {
        editor.clear();
        editor.apply(); // Sauvegarder les modifications
    }

    // Méthode pour vérifier si les données utilisateur existent déjà
    public boolean isUserLoggedIn() {
        return sharedPreferences.contains(KEY_USER_ID);
    }
}

