package samucabank.apibank.domain.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import samucabank.apibank.domain.enums.user.UserRole;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class UserDetailsImpl implements UserDetails {

    private final User user;

    public UserDetailsImpl(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (user.getRole() == UserRole.ADMIN)
            return List.of(
                    new SimpleGrantedAuthority("ROLE_ADMIN"),
                    new SimpleGrantedAuthority("ROLE_USER")
            );

        else return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getPassword() {
        return Optional.of(user)
            .map(User::getPassword)
            .orElseThrow(() -> new IllegalArgumentException("Password cannot be null"));
    }

    @Override
    public String getUsername() {
        return Optional.of(user)
            .map(User::getEmail)
            .map(Email::getEmail)
            .orElseThrow(() -> new IllegalArgumentException("Email cannot be null"));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
