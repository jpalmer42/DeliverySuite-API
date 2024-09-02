package ca.toadapp.api_main.security.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.ExportedUserRecord;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigException;
import com.google.firebase.remoteconfig.ParameterValue;
import com.google.firebase.remoteconfig.Parameter;

import ca.toadapp.api_main.config.AppConfig;
import ca.toadapp.api_main.config.AppEnvironment;
import lombok.extern.java.Log;

@Log
@Service
public class ServiceFirebase {

	private FirebaseAuth _firebaseAuthInstance;
	private FirebaseRemoteConfig _remoteConfig;

	@PostConstruct
	private void init() throws FileNotFoundException, IOException {
		var res = ServiceFirebase.class.getResource("/serviceAccountKey.json");
		if (res == null) {
			System.out.println(res.getPath());
			throw new FileNotFoundException("serviceAccountKey.json not found");
		}

		try (InputStream serviceAccount = res.openStream()) {
			var options = FirebaseOptions.builder().setCredentials(GoogleCredentials.fromStream(serviceAccount))
					.build();
			FirebaseApp.initializeApp(options);
		}

		_firebaseAuthInstance = FirebaseAuth.getInstance();

		_remoteConfig = FirebaseRemoteConfig.getInstance();

		log.info("Firebase Service Initilized");
	}

	public Map<String, Parameter> getRemoteConfig() throws FirebaseRemoteConfigException {
		return _remoteConfig.getTemplate().getParameters();
	}

	public String getRemoteConfigString(String key, String defaultValue) throws FirebaseRemoteConfigException {
		final Parameter parameter = getRemoteConfig().get(key);
		if (parameter == null)
			return defaultValue;
		ParameterValue parameterValue = parameter.getDefaultValue();
		if (parameterValue == null)
			return defaultValue;

		return ((ParameterValue.Explicit) parameterValue).getValue();
	}

	public FirebaseToken verifyToken(String idToken) throws FirebaseAuthException {
		var decodedToken = _firebaseAuthInstance.verifyIdToken(idToken);
		return decodedToken;
	}

	@Autowired
	private AppConfig appConfig;

	final long A_DAY_MS = 24 * 60 * 60 * 1000;

	@Scheduled(timeUnit = TimeUnit.MINUTES, fixedRate = 1440, initialDelay = 1) // Every Day
	private void deleteOldAccounts() {

		log.info(String.format("Deleting Dead User Accounts"));

		if (appConfig.getEnvironment() != AppEnvironment.dev) {

			final long maxDisuseTime = (new Date().getTime()) - 30 * A_DAY_MS;
			try {
				var page = _firebaseAuthInstance.listUsers(null);
				for (ExportedUserRecord user : page.iterateAll()) {
					long lastActive = user.getUserMetadata().getLastSignInTimestamp();
					if (lastActive < maxDisuseTime) {
						var uid = user.getUid();
						log.info(String.format("Deleting User %s", uid));
						// _firebaseAuthInstance.deleteUser(uid);
					}
				}
			} catch (FirebaseAuthException ex) {

			}
		}
	}

}
