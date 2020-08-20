package dao.abstracts;

import org.springframework.security.core.userdetails.UserDetailsService;
import enums.RegisterResult;
import jdo.Account;

/**
 * Dao for work with account structure
 * */
public interface AccountDao extends UserDetailsService {

    Account findByUsername(String username);

    RegisterResult register(Account account);

    void delete(String username);

}
