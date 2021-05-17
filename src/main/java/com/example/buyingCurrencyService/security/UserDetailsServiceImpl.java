package com.example.buyingCurrencyService.security;

import com.example.buyingCurrencyService.dao.UserDaoImpl;
import com.example.buyingCurrencyService.handlers.exception.NoSuchUserException;
import com.example.buyingCurrencyService.model.Status;
import com.example.buyingCurrencyService.model.entity.User;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private @NonNull UserDaoImpl userDaoImpl;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        User user = userDaoImpl.getUser(userName)
                .orElseThrow(() -> new NoSuchUserException("User does not exists"));

        return new org.springframework.security.core.userdetails.User(
                user.getLogin(),
                user.getPassword(),
                user.getStatus().equals(Status.ACTIVE),
                user.getStatus().equals(Status.ACTIVE),
                user.getStatus().equals(Status.ACTIVE),
                user.getStatus().equals(Status.ACTIVE),
                user.getRole().getAuthorities()
        );
    }
}