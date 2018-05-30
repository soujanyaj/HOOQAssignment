package com.core.pages;

import java.io.FileReader;
import java.util.Arrays;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.testng.Assert;

import com.utils.Utils;

public class Data {
	public static Object[][] loginData = new Object[1][4];
	public static int j = 0;

	@SuppressWarnings("unchecked")
	public Object[][] getLoginData() {
		JSONParser parser = new JSONParser();
		j=0;
		try {
			JSONObject jsonObject = (JSONObject) parser.parse(new FileReader(Utils.getProperty("loginDataFile")));
			JSONArray loginDataSet = (JSONArray) jsonObject.get("loginDataSet");
			loginData = new Object[loginDataSet.size()][4];
			
			loginDataSet.forEach( (dataSet) -> {
				loginData[j][0] = "EMAIL_"+((JSONObject) dataSet).get("email").toString();
				loginData[j][1] = "PWD_"+((JSONObject) dataSet).get("password").toString();
				loginData[j][2] = "TEST-TYPE_"+((JSONObject) dataSet).get("testType").toString();
				loginData[j][3] = "ERROR-MSG_"+(((JSONObject) dataSet)).get("errorMsg").toString();
				j++;
			});
			

		} catch (Exception e) {
			Assert.fail(Thread.currentThread().getStackTrace()[1].getMethodName() + " FAILED, Line No: "
					+ e.getStackTrace()[0].getLineNumber() + " And Exception is", e);
		}

		System.out.println("loginData: " + Arrays.deepToString(loginData));
		return loginData;
	}
	
	public static void main(String a[]) {
		Data d= new Data();
		d.getLoginData();
	}

}
