package ca.toadapp.api_main.config;

public enum AppEnvironment {
	prod("prod"),
	test("test"),
	dev("dev");
	
	final String code; 
	private AppEnvironment(String code) {
		this.code = code;
	}
}
