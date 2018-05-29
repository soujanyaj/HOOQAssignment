package com.tests;

import com.configurations.ConfigFactory;
import com.configurations.IConfiguration;

public class BaseTest {
	
	protected static IConfiguration config =  null;

	public static void testInitialize(String type){
		config = ConfigFactory.getConfig(type);
	}
}
 