package ma.emsi.cinema.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

import ma.emsi.cinema.entities.Categorie;
import ma.emsi.cinema.entities.Cinema;


@RepositoryRestResource  // tous les methodes herités de jpaRepository sont accessible via une API Rest
@CrossOrigin("*")
public interface CategoryRepository extends JpaRepository<Categorie, Long> {

}
