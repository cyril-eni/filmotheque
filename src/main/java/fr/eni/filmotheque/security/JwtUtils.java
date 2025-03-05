package fr.eni.filmotheque.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class JwtUtils {
    private String jwtSecret = "secret";

    /**
     * Cette méthode crée un token JWT avec l'info "username"
     * pour cela on utilise la librairie JWT : https://github.com/auth0/java-jwt
     * info : on se base sur notre jwtSecret (= "secret")
     */
    public String generateJwtToken(Authentication authentication) {
        UtilisateurSpringSecurity userPrincipal = (UtilisateurSpringSecurity) authentication.getPrincipal();
        return JWT.create().withClaim("username", userPrincipal.getUsername()).sign(Algorithm.HMAC256(jwtSecret));
    }

    /**
     * Cette méthode recupère l'info "username" depuis le token JWT
     * pour cela on utilise la librairie JWT : https://github.com/auth0/java-jwt
     * info : on se base sur notre jwtSecret (= "secret")
     */
    public String getUserNameFromJwtToken(String token) {
        return JWT.require(Algorithm.HMAC256(jwtSecret)).build().verify(token).getClaim("username").asString();
    }

    /**
     * Cette méthode recupère le token JWT depuis la requête HTTP
     */
    public static String parseJwt(HttpServletRequest request) {
        // On va chercher le header "Authorization" de la requête qui doit avoir la forme Bearer XXXXXX
        String headerAuth = request.getHeader("Authorization");
        // On va recupérer ce qu'il y a après Bearer avec un subestring()
        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
            return headerAuth.substring(7, headerAuth.length());
        }
        return null;
    }

    /**
     * Cette méthode valide que le token JWT est valide
     * pour cela on utilise la librairie JWT : https://github.com/auth0/java-jwt
     * info : on se base sur notre jwtSecret (= "secret")
     */
    public boolean validateJwtToken(String authToken) {
        try {
            JWT.require(Algorithm.HMAC256(jwtSecret)).build().verify(authToken);
            return true;
        } catch (Exception e) {
            System.out.println("error : " + e.getStackTrace());
        }
        return false;
    }
}