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
		if (profiles != null && profiles.length > 0) {
			if (profiles[0].equals(AppEnvironment.prod.code)) {
				appEnvironment = AppEnvironment.prod;
			}
			if (profiles[0].equals(AppEnvironment.test.code)) {
				appEnvironment = AppEnvironment.test;
			}
			if (profiles[0].equals(AppEnvironment.dev.code)) {
				appEnvironment = AppEnvironment.dev;
			}
		}
		return appEnvironment;
	}

}