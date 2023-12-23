package diplom.auth.web.jwt;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import diplom.auth.business.services.DefaultProfileService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

import static org.springframework.util.StringUtils.hasText;

@Service
public class TokenAuthenticationService {
    private static final String SECRET = "Secret";//Ключ Шифрования
    private static final long EXPIRATION_TIME = 864_000_000;//Время пригодности токена (10days)
    private static final String TOKEN_PREFIX = "Bearer";//Префикс
    private static final String HEADER_STRING = "Authorization";//Заголовок

    private static Logger LOG = LoggerFactory.getLogger(TokenAuthenticationService.class);

    private static DefaultProfileService profileService;
    public TokenAuthenticationService(DefaultProfileService profileService){
        this.profileService = profileService;
    }

    static void addAuthentication(HttpServletResponse response, String login){
        response.addHeader(HEADER_STRING, generateToken(login));
    }

    static Authentication getAuthentication(HttpServletRequest request){
        String token  = getToken(request);
        if (!hasText(token))
        {return null;}
        //Проверка была ли уже запущенна сессия по этому токену
        String username = getUsername(token);

        UserDetails user = profileService.loadUserByUsername(username);
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user,
                null, user.getAuthorities());
        return authentication;
    }

    public static String generateToken(String username){

        return TOKEN_PREFIX+" "+Jwts.builder()
                .setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis()+ EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();
    }

    private static String getToken(HttpServletRequest request){
        if (request.getHeader(HEADER_STRING)!=null)
        {return request.getHeader(HEADER_STRING).replace(TOKEN_PREFIX+" ", "");}
        else
        {return null;}
    }

    public static String getUsername(String token){
        String newToken = token.replace(TOKEN_PREFIX + " ", "");
        try{
            return newToken!=null ? Jwts.parser()
                    .setSigningKey(SECRET)
                    .parseClaimsJws(newToken)
                    .getBody()
                    .getSubject() : null;
        }
        catch (JwtException e){
            LOG.info("Ошибка обработки токена {}", token);
            return null;
        }
    }
}
