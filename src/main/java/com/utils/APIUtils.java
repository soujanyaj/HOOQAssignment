package com.utils;

import java.io.BufferedReader;
import java.io.CharArrayWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.testng.Assert;

/**
 * The Class APIUtils.
 */
public class APIUtils {

	/** The xml response. */
	public static char[] xmlResponse = null;

	/** The writer. */
	private static CharArrayWriter writer;

	/**
	 * Gets the POST response text.
	 *
	 * @param url
	 *            the url
	 * @param urlParameters
	 *            the url parameters
	 * @param sessionId
	 *            the session id
	 * @param reqMethod
	 *            the req method
	 * @return the POST response text
	 */
	public static char[] getPOSTResponseText(URL url, String urlParameters, String sessionId, String reqMethod) {
		HttpURLConnection conn = null;
		StringBuffer response = new StringBuffer();

		try {
			/*
			 * // Create a trust manager that does not validate certificate chains
			 * TrustManager[] trustAllCerts = new TrustManager[] {new X509TrustManager() {
			 * public java.security.cert.X509Certificate[] getAcceptedIssuers() { return
			 * null; } public void checkClientTrusted(X509Certificate[] certs, String
			 * authType) { } public void checkServerTrusted(X509Certificate[] certs, String
			 * authType) { } } };
			 * 
			 * // Install the all-trusting trust manager SSLContext sc =
			 * SSLContext.getInstance("SSL"); sc.init(null, trustAllCerts, new
			 * java.security.SecureRandom());
			 * HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
			 * 
			 * // Create all-trusting host name verifier HostnameVerifier allHostsValid =
			 * new HostnameVerifier() { public boolean verify(String hostname, SSLSession
			 * session) { return true; } };
			 * 
			 * // Install the all-trusting host verifier
			 * HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
			 */

			conn = (HttpURLConnection) url.openConnection();
			conn.setDoInput(true);
			conn.setDoOutput(true);
			conn.setUseCaches(false);

			conn.setRequestMethod(reqMethod);

			if (!urlParameters.equals("")) {
				DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
				wr.writeBytes(urlParameters);
				wr.flush();
				wr.close();
			}

			response = getResponses(conn);
		} catch (IOException e1) {
			Assert.fail(Thread.currentThread().getStackTrace()[1].getMethodName() + " FAILED, Line No: "
					+ e1.getStackTrace()[0].getLineNumber() + " And Exception is: " + e1);
		} finally {
			conn.disconnect();
		}

		return response.toString().toCharArray();
	}

	/**
	 * Gets the responses.
	 *
	 * @param conn
	 *            the conn
	 * @return the responses
	 */
	public static StringBuffer getResponses(HttpURLConnection conn) {
		writer = new CharArrayWriter();
		StringBuffer response = new StringBuffer();
		try {

			int responseCode = conn.getResponseCode();
			System.out.println("responseCode: " + responseCode);
			if (responseCode != 200) {
				if (responseCode == 204)
					response.append("no response");
				if (responseCode == 401)
					response.append("401 response");
				else {
					writer.write(conn.getResponseCode());
					BufferedReader in = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
					String inputLine;

					while ((inputLine = in.readLine()) != null) {
						response.append(inputLine);
					}

					in.close();
				}

			}

			else {
				BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
				String inputLine;

				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);
				}
				in.close();
			}
		} catch (IOException e1) {
			Assert.fail(Thread.currentThread().getStackTrace()[1].getMethodName() + " FAILED, Line No: "
					+ e1.getStackTrace()[0].getLineNumber() + " And Exception is: " + e1);
		} finally {
			conn.disconnect();
		}
		return response;
	}

	/**
	 * Gets the response text.
	 *
	 * @param url
	 *            the url
	 * @param reqMethod
	 *            the req method
	 * @return the response text
	 */
	public static char[] getResponseText(URL url, String reqMethod) {
		HttpURLConnection conn = null;
		StringBuffer response = new StringBuffer();

		try {
			conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod(reqMethod);
			conn.setDoInput(true);
			conn.setDoOutput(false);
			conn.setUseCaches(false);
			conn.connect();

			response = getResponses(conn);

		} catch (IOException e1) {
			Assert.fail(Thread.currentThread().getStackTrace()[1].getMethodName() + " FAILED, Line No: "
					+ e1.getStackTrace()[0].getLineNumber() + " And Exception is: " + e1);
		} finally {
			conn.disconnect();
		}

		return response.toString().toCharArray();
	}

}
