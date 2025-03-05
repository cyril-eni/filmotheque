package fr.eni.filmotheque.service.impl;

import fr.eni.filmotheque.bo.Avis;
import fr.eni.filmotheque.bo.Film;
import fr.eni.filmotheque.dto.ParametresRecherche;
import fr.eni.filmotheque.repository.AvisRepository;
import fr.eni.filmotheque.repository.FilmRepository;
import fr.eni.filmotheque.service.FilmService;
import fr.eni.filmotheque.service.GenreService;
import fr.eni.filmotheque.service.ParticipantService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;

@Profile("prod") // cette classe sera utilisée comme implémentation uniquement si le profil actif est "dev"
@Service // On doit annoter notre classe de service avec @Service afin qu'elle soit dans le contexte Spring
public class FilmServiceJpaImpl implements FilmService {


	// pas besoin d'@Autowired car attributs injectés dans le constructeur
	private GenreService genreService;

	// pas besoin d'@Autowired car attributs injectés dans le constructeur
	private  ParticipantService participantService;

	// pas besoin d'@Autowired car attributs injectés dans le constructeur
	private FilmRepository filmRepository;

	// pas besoin d'@Autowired car attributs injectés dans le constructeur
	private AvisRepository avisRepository;

	/**
	 * Injection AUTOMATIQUE de dans le constructeur
	 * => ne nécessite pas d'@Autowire
	 */
	public FilmServiceJpaImpl(GenreService genreService, ParticipantService participantService, FilmRepository filmRepository, AvisRepository avisRepository) {
		this.genreService = genreService;
		this.participantService = participantService;
		this.filmRepository = filmRepository;
		this.avisRepository = avisRepository;

		/*si aucun film n'est présent en base de donéne, on ajoute*/
		if (filmRepository.findAll().isEmpty()) {
			simulationCoucheDALetDB(); // initialisation nde la liste des films
		}


	}

	@Override
	public List<Film> consulterFilms() {
		return filmRepository.findAll();
	}


	@Override
	public Film consulterFilmParId(long id) {
		return filmRepository.findById(id).orElse(null);
	}

	@Override
	public void creerFilm(Film film) {
		filmRepository.save(film);
	}

	@Override
	public void publierAvis(long filmId, Avis avis) {
		// 1 - on recupère le film correspondant à l'id
		Film film = consulterFilmParId(filmId);

		// 2 - on SAUVEGARDE l'avis en base de donnée (passe de l'état transient -> managé)
		avisRepository.save(avis);

		// 3 - on ajoute l'avis au film (c'est maintenant un objet managé)
		film.getAvis().add(avis);

		// 4 - on sauvegarde le film (si on ne sauvegardait pas l'avis avant, on aurait une erreur)
		// en fonction de si on a une table ou colonne de jointure, ca crée une nouvelle ligne dans notre table de jointure
		// ou cela update la colonne de jointure (ici c'est le cas car on a mis @JoinColumn)
		filmRepository.save(film);
	}

	@Override
	public List<Film> rechercherFilms(ParametresRecherche parametresRecherche) {
		return filmRepository.rechercherFilms(parametresRecherche);
	}

	/**
	 * Cette méthode permet de simuler le stockage en base de données et la remontée
	 * d'information
	 */
	public void simulationCoucheDALetDB() {


		// Création de la liste de films
		// 4 films
		Film jurassicPark = new Film("Jurassic Park", 1993, 128,
				"Le film raconte l'histoire d'un milliardaire et son équipe de généticiens parvenant à ramener à la vie des dinosaures grâce au clonage.");
		jurassicPark.setGenre(genreService.getGenreById(1));
		jurassicPark.setRealisateur(participantService.getParticipantById(1));
		// Associer les acteurs
		jurassicPark.getActeurs().add(participantService.getParticipantById(4));
		jurassicPark.getActeurs().add(participantService.getParticipantById(5));
		creerFilm(jurassicPark);

		Film theFly = new Film( "The Fly", 1986, 95,
				"Il s'agit de l'adaptation cinématographique de la nouvelle éponyme de l'auteur George Langelaan.");
		theFly.setGenre(genreService.getGenreById(1));
		theFly.setRealisateur(participantService.getParticipantById(2));
		// Associer les acteurs
		theFly.getActeurs().add(participantService.getParticipantById(5));
		theFly.getActeurs().add(participantService.getParticipantById(6));
		creerFilm(theFly);

		Film theBFG = new Film( "The BFG", 2016, 117,
				"Le Bon Gros Géant est un géant bien différent des autres habitants du Pays des Géants.");
		theBFG.setGenre(genreService.getGenreById(4));
		theBFG.setRealisateur(participantService.getParticipantById(1));
		// Associer les acteurs
		theBFG.getActeurs().add(participantService.getParticipantById(7));
		theBFG.getActeurs().add(participantService.getParticipantById(8));
		creerFilm(theBFG);

		Film bienvenueChezLesChtis = new Film( "Bienvenue chez les Ch'tis", 2008, 106,
				"Philippe Abrams est directeur de la poste de Salon-de-Provence. Il est marié à Julie, dont le caractère dépressif lui rend la vie impossible. Pour lui faire plaisir, Philippe fraude afin d'obtenir une mutation sur la Côte d'Azur. Mais il est démasqué: il sera muté à Bergues, petite ville du Nord.");
		bienvenueChezLesChtis.setGenre(genreService.getGenreById(4));
		bienvenueChezLesChtis.setRealisateur(participantService.getParticipantById(3));
		// Associer les acteurs
		bienvenueChezLesChtis.getActeurs().add(participantService.getParticipantById(3));
		bienvenueChezLesChtis.getActeurs().add(participantService.getParticipantById(9));
		creerFilm(bienvenueChezLesChtis);
	}
}
