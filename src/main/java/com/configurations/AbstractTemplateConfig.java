package com.configurations;

import com.core.pages.AbstractTemplatePage;
import com.core.pages.AuthenticationPage;
import com.core.pages.CreateAnAccountPage;
import com.core.pages.Data;
import com.core.pages.HomePage;
import com.core.pages.MyAccountPage;

public abstract class AbstractTemplateConfig {
	protected String url = null;
	protected AbstractTemplatePage abstractTemplatePage = null;
	protected HomePage homePage = null;
	protected AuthenticationPage authenticationPage = null;
	protected CreateAnAccountPage createAnAccountPage = null;
	protected MyAccountPage myAccountPage = null;
	protected Data data = null;
	
	public AbstractTemplatePage AbstractTemplatePage() {
		return abstractTemplatePage;
	}

	public HomePage HomePage() {
		return homePage;
	}

	public AuthenticationPage AuthenticationPage() {
		return authenticationPage;
	}

	public CreateAnAccountPage CreateAnAccountPage() {
		return createAnAccountPage;
	}

}
