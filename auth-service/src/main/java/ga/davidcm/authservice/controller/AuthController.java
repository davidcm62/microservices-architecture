package ga.davidcm.authservice.controller;

import ga.davidcm.authservice.exception.UnauthorizedException;
import ga.davidcm.authservice.model.Credentials;
import ga.davidcm.authservice.model.TokenResponse;
import ga.davidcm.authservice.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class AuthController {

    private final AuthenticationService authenticationService;

    @Autowired
    public AuthController(AuthenticationService authenticationService){
        this.authenticationService = authenticationService;
    }

    @PostMapping(
            path = "/login",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    ResponseEntity<TokenResponse> login(@Valid @RequestBody Credentials credentials){
        try{
            String token = this.authenticationService.authenticate(credentials.getUsername(),credentials.getPassword());
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .header("Authorization",token)
                    .body(new TokenResponse(TokenResponse.ConstructorType.TOKEN_CONSTRUCTOR,token));
        }catch (UnauthorizedException e){
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(new TokenResponse(TokenResponse.ConstructorType.MESSAGE_CONSTRUCTOR,"login failed"));
        }
    }
}
