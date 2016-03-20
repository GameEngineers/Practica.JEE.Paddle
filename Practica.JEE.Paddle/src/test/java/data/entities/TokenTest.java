package data.entities;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Calendar;

import javax.validation.constraints.AssertFalse;

import org.junit.Test;

import data.entities.Token;
import data.entities.User;

public class TokenTest {

    @Test
    public void testTokenUser() {
        User user = new User("u", "u@gmail.com", "p", Calendar.getInstance());
        Token token = new Token(user, Token.HOURS_TO_EXPIRE);
        assertTrue(token.getValue().length() > 20);
    }
    
    @Test
    public void testTokenNoExpired(){
    	User user = new User("u", "u@gmail.com", "p", Calendar.getInstance());
        Token token = new Token(user, Token.HOURS_TO_EXPIRE);
        assertFalse(token.isExpired());
    }
    
    @Test
    public void testTokenExpired(){
    	User user = new User("u", "u@gmail.com", "p", Calendar.getInstance());
        Token token = new Token(user, -1);
        assertTrue(token.isExpired());
    }

}
