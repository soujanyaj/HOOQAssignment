package com.tests;

import java.io.File;

import com.configurations.ConfigFactory;
import com.configurations.IConfiguration;

public class BaseTest {

	protected static IConfiguration config = null;

	public static void testInitialize(String type) {
		
		for (String folder : new String[] { "./TestResultFiles"}) {
			try {
				File theDir = new File(folder);
				if (!theDir.exists())
					theDir.mkdir();
				else {
					for (File file : theDir.listFiles()){
						if (file.isDirectory()) {
						    for (File c : file.listFiles())
						      c.delete();
						  }						
						if (!file.isDirectory())
							file.delete();
					}
				}
			} catch (SecurityException se) {
			}
		}
		
		config = ConfigFactory.getConfig(type);
	}
}
