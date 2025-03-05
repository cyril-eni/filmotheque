package fr.eni.filmotheque.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * Ce filtre va être appliqué avant chaque vérification Spring Security
 * => c'est lui qui va regarder ce qu'il y a dans le token JWT de la requête HTTP
 * et mettre à jour le contexte Spring Security en fonction de ce qu'il trouve (ou pas) dans le token JWT
 */
@Component
public class JwtFilter extends OncePerRequestFilter {
    @Autowired
    private JwtUtils jwtUtils;

    // service qui permte de récupérer un utilisateur Spring security à partir d'un pseudo (username)
    @Autowired
    private GestionPersonaliseeUtilisateurs gestionPersoUtilisateurs;

    /**
     * Méthode appelée avant la verification de Spring Security
     * C'est là qu'on vérifie le Json Web Token et met à jour le Contexte de sécurité
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            // 1 - on récupère le token JWT depuis la requête HTTP
            String jwt = jwtUtils.parseJwt(request);

            // 2 - si jamais on a bien un token et qu'il a bien été encrypté avec notre algo/mot de passe
            if (jwt != null && jwtUtils.validateJwtToken(jwt)) {
                // 2.1 - alors on recupère l'information du pseudo (username) de l'utilisateur contenu dans le token
                String username = jwtUtils.getUserNameFromJwtToken(jwt);

                // 2.2 - on vérifie que cet utilisateur existe dans notre base de donnée
                UserDetails userDetails = gestionPersoUtilisateurs.loadUserByUsername(username);

                // 2.3 - si il existe : on met à jour le contexte Spring Security avce les infos liées (rôle, etc...)
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
            // si pas de token ou token invalide ou utilisateur non recoonu
            // => on ne met pas à jour le contexte Spring Security
            // => on va considérer qu'il n'y a pas d'utilisateur connecté
        } catch (Exception e) {
            logger.error("Cannot set user authentication: {}", e);
        }
        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilterAsyncDispatch() {
        return false;
    }

    @Override
    protected boolean shouldNotFilterErrorDispatch() {
        return false;
    }
}