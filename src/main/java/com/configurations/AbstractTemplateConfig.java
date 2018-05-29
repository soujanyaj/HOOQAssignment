package com.configurations;

import com.core.pages.HomePage;

public abstract class AbstractTemplateConfig {
	protected String url = null;
    protected HomePage homePage = null;
    
    public HomePage HomePage()
    {
      return homePage; 
    }
    

}
