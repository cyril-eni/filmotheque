package fr.eni.filmotheque.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class PasswordEncoderConfiguration {
    @Bean // on définit un bean pour l'utilitaire d'encryption de mot de passe
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}