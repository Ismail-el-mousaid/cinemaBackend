package ma.emsi.cinema.web;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.Data;
import ma.emsi.cinema.dao.FilmRepository;
import ma.emsi.cinema.dao.TicketRepository;
import ma.emsi.cinema.entities.Film;
import ma.emsi.cinema.entities.Ticket;

@RestController  // pour créer api rest
@CrossOrigin("*")  // autoriser l'accès a les requets http qui arrive de angular
public class CinemaRestController {
	@Autowired
	private FilmRepository filmRepository;
	@Autowired
	private TicketRepository ticketRepository;
	
	//	Consulter la liste des films
	@GetMapping("/listFilms")
	private List<Film> films(){
		return filmRepository.findAll();
	}
	
	
	//Retourner les images (image=tableau du byte)
	@GetMapping(path="/imageFilm/{id}", produces=MediaType.IMAGE_JPEG_VALUE)
	public byte[] image(@PathVariable (name="id")Long id) throws Exception {
		Film f = filmRepository.findById(id).get();
		String photoName = f.getPhoto();
		// recuperer photo a partir de dossier
		File file = new File(System.getProperty("user.home")+"/ImagesCinema/"+photoName);
		Path path = Paths.get(file.toURI());
		return Files.readAllBytes(path);
	}
	
	//method de reserver des tickets et le payer
	@PostMapping("/payerTickets")
	@Transactional
	public List<Ticket> payerTickets(@RequestBody TicketForm ticketForm){
		List<Ticket> listTickets = new ArrayList<>();
		ticketForm.getTickets().forEach(idTicket->{
			System.out.println(idTicket);
			Ticket ticket = ticketRepository.findById(idTicket).get();
			ticket.setNomClient(ticketForm.getNomClient());
			ticket.setReserve(true);
			ticket.setCodePayement(ticketForm.getCodePayement());
			ticketRepository.save(ticket);
			listTickets.add(ticket);
		});
		return listTickets;
	}
	

	
}
@Data
class TicketForm{
	private String nomClient;
	private int codePayement;
	private List<Long> tickets = new ArrayList<>();  // liste des tickets qu'il souhaite reservé
}
