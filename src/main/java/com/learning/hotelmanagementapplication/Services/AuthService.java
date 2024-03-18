package com.learning.hotelmanagementapplication.Services;

import com.learning.hotelmanagementapplication.DTOs.UserResponseDTO;
import com.learning.hotelmanagementapplication.Exceptions.AlreadyPresentException;
import com.learning.hotelmanagementapplication.Exceptions.BadCredentialException;
import com.learning.hotelmanagementapplication.Exceptions.NotFoundException;
import com.learning.hotelmanagementapplication.Models.Session;
import com.learning.hotelmanagementapplication.Models.SessionStatus;
import com.learning.hotelmanagementapplication.Models.User;
import com.learning.hotelmanagementapplication.Models.UserType;
import com.learning.hotelmanagementapplication.Repositories.SessionRepository;
import com.learning.hotelmanagementapplication.Repositories.UserRepository;
import io.jsonwebtoken.Jwts;
import org.aspectj.weaver.ast.Not;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.util.MultiValueMapAdapter;

import javax.crypto.SecretKey;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class AuthService {
    private final UserRepository userRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final SecretKey secretKey;
    private final SessionRepository sessionRepository;

    public AuthService(UserRepository userRepository,
                       BCryptPasswordEncoder bCryptPasswordEncoder,
                       SessionRepository sessionRepository) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.secretKey = Jwts.SIG.HS256.key().build();
        this.sessionRepository = sessionRepository;
    }

    public void signUp(String name, String email, String password,
                       String mobile, UserType userType) throws AlreadyPresentException {
        Optional<User> optionalUser = userRepository.findUserByEmail(email);
        if(optionalUser.isPresent()){
            throw new AlreadyPresentException("Email already registered!");
        }

        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setMobile(mobile);
        user.setUserType(userType);
        user.setPassword(bCryptPasswordEncoder.encode(password));

        User saved = userRepository.save(user);
    }

    public ResponseEntity<UserResponseDTO> login(String email, String password) throws BadCredentialException, NotFoundException {
        Optional<User> optionalUser = userRepository.findUserByEmail(email);
        if(optionalUser.isEmpty()){
            throw new NotFoundException("not found !");
        }

        User user = optionalUser.get();

        if(!bCryptPasswordEncoder.matches(password, user.getPassword())){
            throw new BadCredentialException("Password and Username doesn't match!");
        }

        Map<String, Object> customClaims = new HashMap<>();
        customClaims.put("userType", user.getUserType());
        customClaims.put("email", user.getEmail());
        customClaims.put("name", user.getName());
        customClaims.put("expiryAT", new Date(LocalDate.now().plusDays(3).toEpochDay()));

        String token = Jwts
                .builder()
                .signWith(secretKey)
                .claims(customClaims)
                .issuedAt(new Date()).expiration(new Date(LocalDate.now().plusDays(3).toEpochDay()))
                .compact();

        Session session = new Session();
        session.setSessionStatus(SessionStatus.ACTIVE);
        session.setUser(user);
        session.setToken(token);

        sessionRepository.save(session);
        UserResponseDTO responseDTO = Mappers.convertToResponseDTO(user);

        MultiValueMap<String, String > headers = new MultiValueMapAdapter<>(new HashMap<>());

        headers.add(HttpHeaders.SET_COOKIE, "auth-token: " + token);

        return new ResponseEntity<>(responseDTO, headers, HttpStatus.OK);
    }

    public void logout(String token, Long userId){
        Optional<Session> sessionOptional = sessionRepository.findSessionByTokenAndUser_Id(token, userId);

        if(sessionOptional.isEmpty()){
            return;
        }
        Session session = sessionOptional.get();

        session.setSessionStatus(SessionStatus.ENDED);

        sessionRepository.save(session);
    }
}
