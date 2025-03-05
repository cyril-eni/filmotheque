package fr.eni.filmotheque.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

// on a une classe dans laquelle on va définir la configuration de Spring Security

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Autowired
    private JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf( (csrf) -> csrf.ignoringRequestMatchers("/api/**"));
        http.authorizeHttpRequests((authorize) -> authorize
                        // on autorise les requêtes OPTIONS
                        .requestMatchers(HttpMethod.OPTIONS,"/**").permitAll()
                        // les requêtes de création d'avis sont réservés aux utilisateurs connextés
                        .requestMatchers("/avis").authenticated()
                        // les requêtes de création de film et de gestion de membres sont réservés aux admin
                        .requestMatchers("/films/nouveau", "/membres", "/genres", "/participants").hasRole("admin")
                        // toutes les autres requêtes qui n'ont pas eu de match avec les précédentes expressions
                        // on laisse accéder à la resource (permitAll())
                        .requestMatchers("/**").permitAll()
                )
                // on effectue une authentification basique (user/mdp)
                .httpBasic(Customizer.withDefaults())
                // on utilise le formulaire par défaut de Spring
                .formLogin(Customizer.withDefaults())
                // quand on se déconnecte=> on redirige vers l'accueil
                .logout((logout) -> logout.logoutSuccessUrl("/"));

        /*****************************************************************
         * AVANT DE FAIRE LA VERIFICATION DE SECURITE, ON AJOUTE UN FILTRE
         * qui va vérifier la présence ou non d'un Json Web Token
         ******************************************************************/
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }



}