package business.controllers;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import data.daos.TokenDao;
import data.daos.UserDao;
import data.entities.Encrypt;
import data.entities.Token;
import data.entities.User;

@Controller
@Transactional
public class TokenController {

    private TokenDao tokenDao;

    private UserDao userDao;

    @Autowired
    public void setTokenDao(TokenDao tokenDao) {
        this.tokenDao = tokenDao;
    }

    @Autowired
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public String login(String username) {
        User user = userDao.findByUsernameOrEmail(username);
        assert user != null;
        Token token = new Token(user, Token.HOURS_TO_EXPIRE);
        tokenDao.save(token);
        return token.getValue();
    }
    
    public boolean IsValid(String basicAuth){
    	String[] auth = new Encrypt().encryptInString(basicAuth).split(":");
    	String tokenValue = auth[1];
    	Token token = tokenDao.findByValue(tokenValue);
    	return !token.isExpired();
  	}
}
