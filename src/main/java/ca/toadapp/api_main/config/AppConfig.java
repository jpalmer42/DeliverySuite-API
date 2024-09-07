package ca.toadapp.api_main.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

@Service
public class AppConfig {
	@Autowired
	private Environment environment;

	private AppEnvironment appEnvironment = null;

	public AppEnvironment getEnvironment() {
		if (appEnvironment != null)
			return appEnvironment;

		var profiles = environment.getActiveProfiles();
		for (String profile : profiles) {
			if (profile.contains(AppEnvironment.prod.name())) {
				appEnvironment = AppEnvironment.prod;
			}
			if (profile.contains(AppEnvironment.test.name())) {
				appEnvironment = AppEnvironment.test;
			}
			if (profile.contains(AppEnvironment.dev.name())) {
				appEnvironment = AppEnvironment.dev;
			}
			if (profile.contains(AppEnvironment.local.name())) {
				appEnvironment = AppEnvironment.dev;
			}
		}
		return appEnvironment;
	}

}