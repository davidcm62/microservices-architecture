package ga.davidcm.gateway;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.bouncycastle.jcajce.provider.digest.SHA256;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.security.Key;

@SpringBootTest
class GatewayApplicationTests {

    @Test
    void contextLoads() {

        Key key = Keys.secretKeyFor(SignatureAlgorithm.HS512);

    }

}
