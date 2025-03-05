package fr.eni.filmotheque.service.impl;

import fr.eni.filmotheque.bo.Avis;
import fr.eni.filmotheque.bo.Film;
import fr.eni.filmotheque.bo.Membre;
import fr.eni.filmotheque.dto.ParametresRecherche;
import fr.eni.filmotheque.service.FilmService;
import fr.eni.filmotheque.service.GenreService;
import fr.eni.filmotheque.service.ParticipantService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Profile("dev") // cette classe sera utilisée comme implémentation uniquement si le profil actif est "dev"
@Service // On doit annoter notre classe de service avec @Service afin qu'elle soit dans le contexte Spring
public class FilmServiceBouchon implements FilmService {

	// pas besoin d'@Autowired car attributs injectés dans le constructeur
	GenreService genreService;

	// pas besoin d'@Autowired car attributs injectés dans le constructeur
	ParticipantService participantService;

	// Liste pour gérer les films en mémoire Java
	private static List<Film> lstFilms = new ArrayList<>();
	private static long indexFilms = 1;

	/**
	 * Injection AUTOMATIQUE de dans le constructeur
	 * => ne nécessite pas d'@Autowire
	 */
	public FilmServiceBouchon(GenreService genreService, ParticipantService participantService) {
		this.genreService = genreService;
		this.participantService = participantService;
		simulationCoucheDALetDB(); // initialisation nde la liste des films
	}

	@Override
	public List<Film> consulterFilms() {
		return lstFilms;
	}


	@Override
	public Film consulterFilmParId(long id) {
		return lstFilms.stream().filter(item -> item.getId() == id).findAny().orElse(null);
	}




	@Override
	public void creerFilm(Film film) {
		// Sauvegarde du film
		film.setId(indexFilms++);
		lstFilms.add(film);
	}

	@Override
	public void publierAvis(long filmId, Avis avis) {
		// 1 - on recupère le film correspondant à l'id
		Film film = consulterFilmParId(filmId);

		// 2 - on ajoute l'avis au film
		film.getAvis().add(avis);
	}

	@Override
	public List<Film> rechercherFilms(ParametresRecherche parametresRecherche) {
		return List.of();
	}

	/**
	 * Cette méthode permet de simuler le stockage en base de données et la remontée
	 * d'information
	 */
	public void simulationCoucheDALetDB() {


		// Création de la liste de films
		// 4 films
		Film jurassicPark = new Film(indexFilms++, "Jurassic Park", 1993, 128,
				"Le film raconte l'histoire d'un milliardaire et son équipe de généticiens parvenant à ramener à la vie des dinosaures grâce au clonage.");
		jurassicPark.setGenre(genreService.getGenreById(1));
		jurassicPark.setRealisateur(participantService.getParticipantById(1));
		// Associer les acteurs
		jurassicPark.getActeurs().add(participantService.getParticipantById(4));
		jurassicPark.getActeurs().add(participantService.getParticipantById(5));
		lstFilms.add(jurassicPark);

		Film theFly = new Film(indexFilms++, "The Fly", 1986, 95,
				"Il s'agit de l'adaptation cinématographique de la nouvelle éponyme de l'auteur George Langelaan.");
		theFly.setGenre(genreService.getGenreById(1));
		theFly.setRealisateur(participantService.getParticipantById(2));
		// Associer les acteurs
		theFly.getActeurs().add(participantService.getParticipantById(5));
		theFly.getActeurs().add(participantService.getParticipantById(6));
		lstFilms.add(theFly);

		Film theBFG = new Film(indexFilms++, "The BFG", 2016, 117,
				"Le Bon Gros Géant est un géant bien différent des autres habitants du Pays des Géants.");
		theBFG.setGenre(genreService.getGenreById(4));
		theBFG.setRealisateur(participantService.getParticipantById(1));
		// Associer les acteurs
		theBFG.getActeurs().add(participantService.getParticipantById(7));
		theBFG.getActeurs().add(participantService.getParticipantById(8));
		lstFilms.add(theBFG);

		Film bienvenueChezLesChtis = new Film(indexFilms++, "Bienvenue chez les Ch'tis", 2008, 106,
				"Philippe Abrams est directeur de la poste de Salon-de-Provence. Il est marié à Julie, dont le caractère dépressif lui rend la vie impossible. Pour lui faire plaisir, Philippe fraude afin d'obtenir une mutation sur la Côte d'Azur. Mais il est démasqué: il sera muté à Bergues, petite ville du Nord.");
		bienvenueChezLesChtis.setGenre(genreService.getGenreById(4));
		bienvenueChezLesChtis.setRealisateur(participantService.getParticipantById(3));
		// Associer les acteurs
		bienvenueChezLesChtis.getActeurs().add(participantService.getParticipantById(3));
		bienvenueChezLesChtis.getActeurs().add(participantService.getParticipantById(9));
		lstFilms.add(bienvenueChezLesChtis);

		// Création d'un membre et un avis
		Membre membre1 = new Membre(1, "Baille", "Anne-Lise", "abaille@campus-eni.fr", null);
		Avis avis = new Avis(1L, 4, "On rit du début à la fin", membre1);
		bienvenueChezLesChtis.getAvis().add(avis);
	}
}
