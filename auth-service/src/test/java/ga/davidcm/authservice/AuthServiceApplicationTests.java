package ga.davidcm.authservice;

import ga.davidcm.authservice.model.Auth;
import ga.davidcm.authservice.repository.AuthRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@SpringBootTest
class AuthServiceApplicationTests {

	@Autowired
	private AuthRepository authRepository;

	@Test
	void contextLoads() throws NoSuchAlgorithmException {
		Auth auth = new Auth();

		MessageDigest digest = MessageDigest.getInstance("SHA-256");
		byte[] hash = digest.digest("password".getBytes(StandardCharsets.UTF_8));
		StringBuffer result = new StringBuffer();
		for (byte b : hash) result.append(Integer.toString((b & 0xff) + 0x100, 16).substring(1));

		auth.setId("a9806b66-aad4-435d-89a1-be28b536").setUsername("david").setPassword(result.toString());


		authRepository.save(auth);
	}

}
