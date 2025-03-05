package fr.eni.filmotheque.api;

import fr.eni.filmotheque.bo.Membre;
import fr.eni.filmotheque.security.GestionPersonaliseeUtilisateurs;
import fr.eni.filmotheque.security.JwtUtils;
import fr.eni.filmotheque.security.UtilisateurSpringSecurity;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private AuthenticationConfiguration authenticationConfiguration;
    @Autowired
    private GestionPersonaliseeUtilisateurs gestionPersoUtilisateurs;

    @GetMapping
    public Membre getConnectedUser(HttpServletRequest request) throws Exception {
        String username = jwtUtils.getUserNameFromJwtToken(jwtUtils.parseJwt(request));
        UtilisateurSpringSecurity user = (UtilisateurSpringSecurity) gestionPersoUtilisateurs.loadUserByUsername(username);
        return user.getMembre();
    }
}