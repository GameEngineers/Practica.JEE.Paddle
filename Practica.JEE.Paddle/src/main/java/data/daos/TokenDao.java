package data.daos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import data.entities.Token;
import data.entities.User;

public interface TokenDao extends JpaRepository<Token, Integer> {

    Token findByUser(User user);
    
    Token findByValue(String value);
    
    @Transactional
    @Modifying
    @Query("delete from Token t where t.expires < CURRENT_TIMESTAMP")
    public void deleteAllExpiredTokens();
    
    @Transactional
    @Modifying
    @Query("delete from Token t where t.user = ?1 and t.expires < CURRENT_TIMESTAMP")
    public void deleteAllExpiredTokensByUser(User user);
}
