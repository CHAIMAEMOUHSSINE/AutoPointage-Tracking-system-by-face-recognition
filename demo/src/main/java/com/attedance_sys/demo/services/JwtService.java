package com.attedance_sys.demo.services;


import com.attedance_sys.demo.entity.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.Claims;
import javax.crypto.SecretKey;
import java.util.Date;
import java.util.function.Function;

@Service
public class JwtService {
    private String SECRET_KEY = "a6061b167d7fdc685d0b20ccae4d1656c51cadbbdd320368243bfd6b64c5f6c7"; //La clé secrète est utilisée pour générer une signature numérique qui est ajoutée au JWT. Cette signature est ensuite utilisée pour vérifier l'intégrité du jeton et s'assurer qu'il n'a pas été modifié par des tiers non autorisés.

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject); //indique à extractClaim d'extraire le sujet du token JWT, qui est généralement le nom d'utilisateur.
    } //Cette méthode extrait le nom d'utilisateur du sujet du jeton JWT.
    public boolean isValid(String token, UserDetails user) { // vérifie si un jeton JWT est valide pour un utilisateur donné
        String username = extractUsername(token);

        return (username.equals(user.getUsername())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date()); //Si la date d'expiration est antérieure à la date actuelle, cela signifie que le jeton est expiré, donc la méthode retourne true. Sinon, elle retourne false
    } //vérifie si un jeton JWT est expiré en comparant la date d'expiration extraite du jeton avec la date actuelle

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    } //extraire la date d'expiration du jeton JWT passé en paramètre. Cela permet de récupérer la date à laquelle le jeton expire.

    public <T> T extractClaim(String token, Function<Claims, T> resolver) { //une méthode générique qui permet d'extraire une revendication spécifique d'un jeton JWT en utilisant une fonction de résolution.
        Claims claims = extractAllClaims(token);
        return resolver.apply(claims);
    }


    private Claims extractAllClaims(String token) { //'extraire toutes les revendications (claims) contenues dans un jeton JWT.
        return Jwts
                .parser() //Cela crée un parseur JWT à partir de la bibliothèque JJWT. Le parseur est utilisé pour interpréter et décoder le contenu du jeton JWT.
                .verifyWith(getSigninKey()) //pécifie la clé de signature utilisée pour vérifier l'authenticité du jeton JWT. Elle utilise la méthode getSigninKey() pour récupérer la clé de signature.
                .build()
                .parseSignedClaims(token) // Cette méthode analyse le jeton JWT pour extraire les revendications signées.
                .getPayload(); // Cette méthode récupère les revendications extraites du jeton JWT sous forme d'un objet de type Payload
    }
    public String generateToken(User user){ //créer un nouveau jeton JWT à partir des informations fournies par un utilisateur.
        String token = Jwts
                .builder()
                .subject(user.getUsername())
                .claim("id",user.getId())
                .claim("role",user.getRole())
                .claim("username",user.getUsername())
                .claim("email",user.getEmail())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 24*60*60*1000))
                .signWith(getSigninKey())
                .compact();
        return token;
    }
    private SecretKey getSigninKey() { // obtenir la clé de signature utilisée pour signer et vérifier les jetons JWT
        byte[] keyBytes = Decoders.BASE64URL.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

}
