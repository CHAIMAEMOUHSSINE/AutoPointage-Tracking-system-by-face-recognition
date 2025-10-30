package com.attedance_sys.demo.entity;

public class AuthenticationResponse {
    private String token;//Une chaîne de caractères représentant le JWT généré suite à une authentification réussie.
    private String message; //Une chaîne de caractères qui peut contenir un message d'information, par exemple, un message de succès ou d'échec d'authentification.

    public AuthenticationResponse(String token, String message) {
        this.token = token;
        this.message = message;
    }

    public String getToken() {
        return token;
    }

    public String getMessage() {
        return message;
    }
}
//Cette classe AuthenticationResponse est utilisée généralement pour encapsuler la réponse d'une opération d'authentification dans une application Spring utilisant JWT. Après qu'un utilisateur se soit authentifié avec succès, cette classe pourrait être utilisée pour retourner le JWT (dans token) ainsi qu'un message de succès (dans message). Cette réponse peut ensuite être envoyée au client qui a initié la demande d'authentification. Le client peut alors utiliser le JWT pour effectuer des requêtes protégées vers l'application, tandis que le message peut fournir des informations supplémentaires sur le résultat de l'opération d'authentification.

