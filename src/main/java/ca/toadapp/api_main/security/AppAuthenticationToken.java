package ca.toadapp.api_main.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.server.resource.authentication.BearerTokenAuthenticationToken;

import com.google.firebase.auth.FirebaseToken;

import ca.toadapp.api_main.security.service.ServiceFirebase;
import lombok.extern.java.Log;

@Log
public class AppAuthenticationToken extends BearerTokenAuthenticationToken {
	private static final long serialVersionUID = -3095054057370058734L;

	private FirebaseToken firebaseToken;

	private UserDetails userDetails;

	private static String anonymousSecret = null;

	public AppAuthenticationToken(String firebaseTokenEncoded, ServiceFirebase firebaseService) {
		super(firebaseTokenEncoded);
		try {

			if (anonymousSecret == null) {
				anonymousSecret = firebaseService.getRemoteConfigString("anonymousSecret", null);
				if (anonymousSecret == null)
					throw new Exception("anonymousSecret not set in RemoteConfig");
			}

			if (firebaseTokenEncoded.equals(anonymousSecret)) {
				super.setAuthenticated(true);
				userDetails = new AnonymousUserDetails("anonymous", "anonymous", "anonymous");
			} else {
				firebaseToken = firebaseService.verifyToken(firebaseTokenEncoded);
				super.setAuthenticated(true);
				userDetails = new AppUserDetails(firebaseToken.getName(), firebaseToken.getEmail(),
						firebaseToken.getUid());
			}
		} catch (Exception ex) {
			log.severe(ex.getMessage());
		}
	}

	@Override
	public Object getCredentials() {
		return firebaseToken;
	}

	@Override
	public Object getPrincipal() {
		return userDetails;
	}

	@Override
	public String getName() {
		return firebaseToken != null ? firebaseToken.getName() : userDetails.getUsername();
	}

	public String getEmail() {
		return firebaseToken != null ? firebaseToken.getEmail() : "";
	}
}
