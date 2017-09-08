package cat.tecnocampus;

import cat.tecnocampus.domain.NoteLab;
import cat.tecnocampus.domain.UserLab;
import cat.tecnocampus.persistence.NoteLabDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDateTime;

@SpringBootApplication
public class Notes17Application implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(Notes17Application.class, args);
	}

	@Autowired
	private NoteLabDAO noteLabDAO;


	@Override
	public void run(String... strings) throws Exception {
		UserLab user = new UserLab.UserLabBuilder("roure", "roure@tecnocampus.cat")
				.name("Josep")
				.secondName("Roure")
				.build();

		System.out.println(user);

		NoteLab note = new NoteLab.NoteLabBuilder("Primera", "Aquesta es la primera anotacio")
				.dateCreation(LocalDateTime.now())
				.dateEdit(LocalDateTime.now())
				.build();

		System.out.println(note);

		noteLabDAO.insert(note);
	}
}
