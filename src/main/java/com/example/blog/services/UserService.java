package com.example.blog.services;

import com.example.blog.configurations.JwtService;
import com.example.blog.entities.User;
import com.example.blog.entities.datatypes.Role;
import com.example.blog.models.UserModel;
import com.example.blog.models.auth.AuthenticationRequest;
import com.example.blog.models.auth.AuthenticationResponse;
import com.example.blog.models.auth.RegisterRequest;
import com.example.blog.models.auth.RegisterResponse;
import com.example.blog.models.users.ProfileRequest;
import com.example.blog.models.users.ProfileResponse;
import com.example.blog.repositories.UserRepository;
import com.example.blog.shared.exceptions.BlogException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

@Service
public class UserService {
    private  final UserRepository userRepository;
    private  final JwtService jwtService;
    private  final AuthenticationManager authenticationManager;
    private  final PasswordEncoder passwordEncoder;
    private  final  FileService fileService;

    private static final Logger logger= LoggerFactory.getLogger(UserService.class);

    @Autowired
    public  UserService(UserRepository userRepository, JwtService jwtService, AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder, FileService fileService){
        this.userRepository=userRepository;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.fileService = fileService;
    }
    public List<UserModel> GetUsers(){

        List<User> users= userRepository.findAll();
        List<UserModel> result = users.stream()
                .map(u -> new UserModel(u))
                .toList();
        return result;
    }

    public  UserModel GetProfile(String id) throws BlogException {
        logger.info("Getting user {} profile",id);

        var user= userRepository.findById(id).orElseThrow(()-> new BlogException("User not found"));
        return new UserModel(user);
    }

   public ProfileResponse UpdateProfile(ProfileRequest profileRequest) throws BlogException, IOException {
       if(StringIsNullOrEmpty(profileRequest.getFirstName()))
           throw new BlogException("First name is empty");
       if(StringIsNullOrEmpty(profileRequest.getFirstName()))
           throw new BlogException("Last name is empty");
       if(profileRequest.getBirthDate()==null)
           throw new BlogException("Birthday is null");

       logger.info("Getting user profile from context");
       var auth= SecurityContextHolder.getContext().getAuthentication();
       if(!auth.isAuthenticated()){
           var details= auth.getDetails();
           logger.error("User {} is not authenticated", details);
           throw new BlogException("user is not authenticated");
       }
       var user = userRepository.findByEmail(auth.getName())
               .orElseThrow(()-> new BlogException("User not found")); // name should contain the enail
       //var user= (User)auth.getPrincipal();//var user= userRepository.findById(((User)auth.getPrincipal()))

       if(profileRequest.getBirthDate()!=null)
           user.birthDate = profileRequest.getBirthDate();
       user.firstName= profileRequest.getFirstName();
       user.lastName= profileRequest.getLastName();

       //user.setSecondaryLanguage(profileRequest.getSecondaryLanguage());
       if(profileRequest.getImage()!=null){
           var image= fileService.Upload(profileRequest.getImage());
           user.setImage(image);
       }
       userRepository.save(user);

       logger.info("User profile updated");
       return new ProfileResponse(user);
     }

    public  UserModel GetUser() throws BlogException {
        var user=GetUserContext();
//        var u= userRepository.findById(user.Id)
//                .orElseThrow(()-> new ArclightException("User not found"));
        if(user== null)
            throw  new BlogException("User not found");

        var result= new UserModel(user);
        return result;
    }

    public  User GetUserContext() throws BlogException {
        logger.info("Getting user profile");
        var auth= SecurityContextHolder.getContext().getAuthentication();
        if(!auth.isAuthenticated()){ // toggle this
            var details= auth.getDetails();
            logger.error("User {} is not authenticated", details);
            throw  new BlogException("user is not authenticated");
        }

        return (User)auth.getPrincipal();
    }

    public  User GetUserInstance() throws BlogException {
        var user= GetUserContext();
        return userRepository.findById(user.id)
                .orElseThrow(()-> new BlogException("User not found"));
    }

    public RegisterResponse register(RegisterRequest registerRequest) throws BlogException {

        logger.info("User is registering with email {} and name {}", registerRequest.getEmail(), registerRequest.getFirstName());
        // Check if auto mapper exist in Java
        if(StringIsNullOrEmpty(registerRequest.getEmail()))
            throw  new BlogException("Email is empty");
        if(!emailIsValid(registerRequest.getEmail()))
            throw  new BlogException("Email is not valid");
        if(StringIsNullOrEmpty(registerRequest.getPassword()))
            throw new BlogException("Password is empty");

        if(StringIsNullOrEmpty(registerRequest.getFirstName()))
            throw new BlogException("Name is empty");
        if(StringIsNullOrEmpty(registerRequest.getLastName()))
            throw new BlogException("Surname is empty");

        var existinguser= userRepository.findByEmail(registerRequest.getEmail());

        if(!existinguser.isEmpty())
        {
            logger.error("User {} already exits", existinguser.get().email );
            throw new BlogException("User exists already");
        }
        var hashedPassword= //hashWith256(registerRequest.getPassword());
        passwordEncoder.encode(registerRequest.getPassword());
        var user= new User.UserBuilder(registerRequest.getFirstName(),registerRequest.getLastName())
                .setEmail(registerRequest.getEmail())
                .setPassword(hashedPassword)
                .setRole(Role.User)
                .build();

        userRepository.save(user);

        var response= new RegisterResponse(user.id,user.email,user.firstName, user.lastName);
        return  response;
    }

    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest) throws BlogException {

        var auth= SecurityContextHolder.getContext().getAuthentication();
        var details =auth.getDetails().toString();
        logger.info("User {} is logging in with email {}", details,authenticationRequest.getEmail());

        if(StringIsNullOrEmpty(authenticationRequest.getEmail()))
          throw  new BlogException("Email is empty");
        if(StringIsNullOrEmpty(authenticationRequest.getPassword()))
            throw new BlogException("Password is empty");

        var user= userRepository.findByEmail(authenticationRequest.getEmail())
                .orElseThrow(()-> new BlogException("User not found"));

        authenticate(authenticationRequest.getEmail(), authenticationRequest.getPassword());

        var extraClaims = new HashMap<String, Object>();
        var jwtToken= jwtService.GenerateToken(extraClaims, user);
        return new AuthenticationResponse.Builder()
                //.builder()
                .setToken(jwtToken)
                .build();
    }

    private  void authenticate(String email, String password) throws BlogException {

        try{
            var result =authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    email,
                    password
            ));
            logger.info("User authenticated with the right credentials");
        }
        catch (Exception e){
            logger.error(e.getMessage());
            throw  new BlogException("Wrong email or password");
        }

    }

    // Helper method
    private static boolean StringIsNullOrEmpty(String str){
        return (str == null && str.trim().isEmpty());
    }

    public static String hashWith256(final String base) {
        try{
            final MessageDigest digest = MessageDigest.getInstance("SHA-256");
            final byte[] hash = digest.digest(base.getBytes("UTF-8"));
            final StringBuilder hexString = new StringBuilder();
            for (int i = 0; i < hash.length; i++) {
                final String hex = Integer.toHexString(0xff & hash[i]);
                if(hex.length() == 1)
                    hexString.append('0');
                hexString.append(hex);
            }

            return hexString.toString();
            // return  String encoded = Base64.getEncoder().encodeToString(hashedByetArray);
        } catch(Exception ex){
            throw new RuntimeException(ex);
        }
    }

    private  static boolean emailIsValid(String s){

        var EMAIL = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
        return EMAIL.matcher(s).matches();
    }
}
