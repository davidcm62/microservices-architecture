package ga.davidcm.authservice.service;


import ga.davidcm.authservice.exception.UnauthorizedException;
import ga.davidcm.authservice.model.Auth;
import ga.davidcm.authservice.repository.AuthRepository;
import ga.davidcm.authservice.utils.SHA256;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class AuthenticationService {

    @Value("${jwt.TOKEN_DURATION}")
    private long TOKEN_DURATION;

    @Value("${jwt.TOKEN_SIGN_KEY}")
    private String TOKEN_SIGN_KEY;

    private final AuthRepository authRepository;

    @Autowired
    public AuthenticationService(AuthRepository authRepository) {
        this.authRepository = authRepository;

    }

    public String authenticate(String username, String password) throws UnauthorizedException {
        final Optional<Auth> authOk = this.authRepository.findByUsernameAndPassword(username, SHA256.hash(password));
        final long now = System.currentTimeMillis();

        if (authOk.isEmpty()) throw new UnauthorizedException();

        Auth auth = authOk.get();
        String token = Jwts.builder()
                .setSubject(auth.getUsername())
                .setIssuedAt(new Date(now))
                .setExpiration(new Date(now + TOKEN_DURATION))
                .claim("username", auth.getUsername())
                .claim("userId", auth.getId())
                .signWith(Keys.hmacShaKeyFor(TOKEN_SIGN_KEY.getBytes()), SignatureAlgorithm.HS512)
                .compact();

        return String.format("Bearer %s",token);
    }

    public void save(String id, String username, String password){
        this.authRepository.save(new Auth().setId(id).setUsername(username).setPassword(SHA256.hash(password)));
    }
}