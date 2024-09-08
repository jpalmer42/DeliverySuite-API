package ca.toadapp.api_main.security;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class AnonymousUserDetails implements UserDetails {
	private static final long	serialVersionUID	= 5185256426170366959L;

	private String				name;
	private String				email;
	private String				uid;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of( UserAuthority.anonymousUserAuthority );
	}

	@Override
	public String getPassword() {
		return null;
	}

	@Override
	public String getUsername() {
		return name;
	}

	public String getEmail() {
		return email;
	}

	public String getUid() {
		return uid;
	}

	@Override
	public boolean isAccountNonExpired() {
		return false;
	}

	@Override
	public boolean isAccountNonLocked() {
		return false;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return false;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
