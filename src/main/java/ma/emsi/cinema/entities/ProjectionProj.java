package ma.emsi.cinema.entities;

import java.util.Collection;
import java.util.Date;

import org.springframework.data.rest.core.config.Projection;

// pour specifier les infos que le serveur me donner 

@Projection(name = "p1", types = {ma.emsi.cinema.entities.Projection.class} )
public interface ProjectionProj {
	public Long getId();
	public double getPrix();
	public Date getDateProjection();
	public Salle getSalle();
	public Film getFilm();
	public Seance getSeance();
	public Collection<Ticket> getTickets();
}
