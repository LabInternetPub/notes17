package cat.tecnocampus;

import cat.tecnocampus.domain.NoteLab;
import cat.tecnocampus.domain.UserLab;
import cat.tecnocampus.persistence.NoteLabDAO;
import cat.tecnocampus.persistence.UserLabDAO;
import cat.tecnocampus.useCases.UserUseCases;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import java.time.LocalDateTime;

@SpringBootApplication
public class Notes17Application implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(Notes17Application.class, args);
	}

	@Autowired
	private NoteLabDAO noteLabDAO;

	@Autowired
	private UserLabDAO userLabDAO;

	@Autowired
	private UserUseCases userUseCases;


	@Override
	public void run(String... strings) throws Exception {
		UserLab user = new UserLab.UserLabBuilder("maristany", "maristany@tecnocampus.cat")
				.name("Marta")
				.secondName("Maristany")
				.build();

		System.out.println(user);

		NoteLab note = new NoteLab.NoteLabBuilder("Primera", "Aquesta es la primera anotacio")
				.dateCreation(LocalDateTime.now())
				.dateEdit(LocalDateTime.now())
				.build();

		System.out.println(note);

		userLabDAO.insert(user);
		noteLabDAO.insert(note, user);

		System.out.println();
		System.out.println(userLabDAO.findByUsername("maristany"));

		System.out.println();
		userLabDAO.findAll().forEach(System.out::println);

		System.out.println();
		userLabDAO.findAllEager().forEach(System.out::println);

		noteLabDAO.findAll().forEach(System.out::println);

		noteLabDAO.findByUsername(user.getUsername()).forEach(System.out::println);

		System.out.println(noteLabDAO.findById(3));

		System.out.println(noteLabDAO.existsNote(note));
		NoteLab n = new NoteLab.NoteLabBuilder("hola", "hola que tal").dateCreation(LocalDateTime.now()).dateEdit(LocalDateTime.now()).build();
		System.out.println(noteLabDAO.existsNote(n));

		userUseCases.getUsers();

		userUseCases.getAllNotes().forEach(System.out::println);

		userUseCases.getUserNotes("roure").forEach(System.out::println);

		System.out.println(userUseCases.getUser("roure"));
	}
}
