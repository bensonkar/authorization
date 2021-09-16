package com.stanbic.authorizationservice.config;

import com.stanbic.authorizationservice.repositories.AuthenticationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

/**
 * @author bkariuki
 */
@Component
@Transactional(noRollbackFor = {BadCredentialsException.class})
public class AppAuthentication extends DaoAuthenticationProvider {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private AuthenticationRepository authenticationRepository;
    String error;

    @Override
    protected void doAfterPropertiesSet() {
        if (super.getUserDetailsService() != null) {
        }
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        Authentication auth = super.authenticate(authentication);
        com.stanbic.authorizationservice.entities.Authentication dbAuth = authenticationRepository.findByUsername(authentication.getName());
        try {
            logger.info("Login was successful");
            return auth;
        } catch (BadCredentialsException e) {
            if (Objects.nonNull(dbAuth)) {
                error = "Wrong or invalid credentials";
            } else {
                error = "wrong username or password";
            }
            logger.error(error);
            throw new BadCredentialsException(error);
        } catch (LockedException e) {
            error = "Account locked!!";
            logger.error(error);
            throw new LockedException(error);
        } catch (DisabledException e) {
            error = "User has been disabled";
            logger.error(error);
            throw new DisabledException(error);
        } catch (CredentialsExpiredException e) {
            error = "User credentials has expired";
            logger.error(error);
            throw new CredentialsExpiredException(error);
        }
    }

}
