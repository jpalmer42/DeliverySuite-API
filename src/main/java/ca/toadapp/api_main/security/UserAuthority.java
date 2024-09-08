package ca.toadapp.api_main.security;

import org.springframework.security.core.GrantedAuthority;

public class UserAuthority implements GrantedAuthority {
	public enum UserType {
		firebaseUser, anonymousUser;
	}

	private static final long	serialVersionUID		= -4538372662600576886L;
	public static UserAuthority	firebaseUserAuthority	= new UserAuthority( UserType.firebaseUser );
	public static UserAuthority	anonymousUserAuthority	= new UserAuthority( UserType.anonymousUser );

	private UserType			userType;

	UserAuthority( UserType userType ) {
		this.userType = userType;
	}

	public UserType getUserType() {
		return userType;
	}

	@Override
	public String getAuthority() {
		return userType.name();
	}

}
