package com.configurations;
import com.core.pages.HomePage;

public class Core extends AbstractTemplateConfig implements IConfiguration{

	public Core(String url){
        this.url = url;
        this.homePage = new HomePage(this.url);
	}

	public void signUp() {
		this.homePage.signUp();
		
	}
	
	public void login(String email, String password) {
		
		this.homePage.login(email, password);
	}

}
