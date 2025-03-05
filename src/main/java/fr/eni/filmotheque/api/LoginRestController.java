package fr.eni.filmotheque.api;

import fr.eni.filmotheque.dto.MembreDto;
import fr.eni.filmotheque.security.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api/login")
public class LoginRestController {
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    AuthenticationConfiguration authenticationConfiguration;

    /**
     * Comme y'a JSONIgnore sur l'attribut motDePasse de Membre, le @RequestBody ne va pas bien fonctionner
     * => on va utiliser une structure speciale DTO (Data Transfer Object)
     */
    @PostMapping
    public String login(@RequestBody MembreDto membre) throws Exception {
        Authentication authenticationToken = new UsernamePasswordAuthenticationToken(membre.getPseudo(), membre.getMotDePasse());
        Authentication authentication = authenticationConfiguration.getAuthenticationManager().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);
        return jwt;
    }
}