package fr.eni.filmotheque.security;


import fr.eni.filmotheque.bo.Membre;
import fr.eni.filmotheque.service.MembreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * On code un service qui va effectuer la gestion personalisée des utilisateurs
 * Cette classe doit implémenter UserDetailsService
 */
@Service // ne pas oublier de mettre Service
public class GestionPersonaliseeUtilisateurs implements UserDetailsService {

    @Autowired
    private MembreService membreService;

    /*
     * Elle doit donc définir comment on recupère un UtilisateurSpringSecurity
     * à partir d'un pseudo
     * => A partir de notre service qui gère les membres
     */

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Membre membreTrouve =  membreService.getMembreByPseudo(username);

        // je retourne le membre dans son "wrapper"
        return new UtilisateurSpringSecurity(membreTrouve);
    }
}
