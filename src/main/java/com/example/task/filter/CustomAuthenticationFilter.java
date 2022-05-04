package com.example.task.filter;


import com.auth0.jwt.algorithms.Algorithm;
import com.example.task.model.binding.LoginBindingModel;
import com.example.task.util.TokenUtilities;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;


import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Component
public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final ObjectMapper objectMapper;
    private final TokenUtilities tokenUtilities;

    public CustomAuthenticationFilter(AuthenticationManager authenticationManager, TokenUtilities tokenUtilities) {
        this.authenticationManager = authenticationManager;
        this.tokenUtilities = tokenUtilities;

        this.objectMapper = new ObjectMapper();
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        LoginBindingModel loginInfo = null;
        try {
            loginInfo = objectMapper.readValue(request.getInputStream(), LoginBindingModel.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String email = loginInfo.getEmail();
        String password = loginInfo.getPassword();
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email, password);

        return authenticationManager.authenticate(authenticationToken);
    }



    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        User principal = (User) authResult.getPrincipal();
        Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());

        String accessToken = tokenUtilities.createToken(principal.getUsername() ,request.getRequestURL().toString() ,principal.getAuthorities(),10L);
        String refreshToken = tokenUtilities.createToken(principal.getUsername() ,request.getRequestURL().toString() ,principal.getAuthorities(),30L);

        // set tokens on header below
        // response.setHeader("access_token", accessToken);
        // response.setHeader("refresh_token", refreshToken);

        // return tokens as Json Body on response
        Map<String , String> tokens = new HashMap<>();
        tokens.put("access_token", accessToken);
        tokens.put("refresh_token", refreshToken);
        response.setContentType(APPLICATION_JSON_VALUE);
        objectMapper.writeValue(response.getOutputStream(),tokens);



    }
}
