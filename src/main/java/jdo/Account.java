package jdo;

import com.google.common.collect.ImmutableMap;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import enums.Authority;
import enums.Gender;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * User data.
 * */
@Data
public class Account implements UserDetails {

	public static DateTimeFormatter getFormatter() {
		return formatter;
	}

	public static void setFormatter(DateTimeFormatter formatter) {
		Account.formatter = formatter;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public LocalDate getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(LocalDate birthDate) {
		this.birthDate = birthDate;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setAuthorities(Set<Authority> authorities) {
		this.authorities = authorities;
	}

	private static DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE;

    private String username;
    private String password;
    private String email;
    private static Gender gender;
    private LocalDate birthDate;

    private Set<Authority> authorities = new HashSet<>();

    @Override
    public Set<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public void addAuthority(Authority authority){
        authorities.add(authority);
    }

    public boolean hasAuthority(String authority){
        return authorities.contains(Authority.of(authority));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
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

    public Map<String, String> buildRedisMap(){
        return ImmutableMap.<String, String>builder()
                .put("username", username)
                .put("password", password)
                .put("email", email)
                .put("gender", gender.db)
                .put("birthDate", birthDate.format(formatter))
                .build();
    }

    public String[] buildRedisAuthoritiesArray(){
        return authorities.stream().map(authority -> authority.id).toArray(size -> new String[authorities.size()]);
    }

    public static Account fromRedisMapAndAuthorityMap(Map<String, String> map, Set<String> authorities){
        if ( (map == null || map.isEmpty()) && (authorities == null || authorities.isEmpty()) ) {
            return null;
        }
        Account account = new Account();
        if (map != null) {
            account.setUsername(map.get("username"));
            account.setPassword(map.get("password"));
            account.setEmail(map.get("email"));
            account.setGender(gender.of(map.get("gender")));
            account.setBirthDate(LocalDate.parse(map.get("birthDate"), formatter));
        }
        if (authorities != null){
            account.setAuthorities(authorities.stream().map(Authority::of).collect(Collectors.toSet()));
        }

        return account;
    }

}

