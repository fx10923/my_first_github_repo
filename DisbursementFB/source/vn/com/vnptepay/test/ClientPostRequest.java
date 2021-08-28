package vn.com.vnptepay.test;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ConnectException;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import java.net.URL;
import java.net.UnknownHostException;
import java.security.cert.X509Certificate;

public class ClientPostRequest {
	public static String postRequest(String url, String inputdata) {
		BufferedReader in = null;
		HttpsURLConnection con = null;
		try {
			// Create a trust manager that does not validate certificate chains
			TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
				public java.security.cert.X509Certificate[] getAcceptedIssuers() {
					return null;
				}

				public void checkClientTrusted(X509Certificate[] certs, String authType) {
				}

				public void checkServerTrusted(X509Certificate[] certs, String authType) {
				}
			} };

			// Install the all-trusting trust manager
			SSLContext sc = SSLContext.getInstance("SSL");
			sc.init(null, trustAllCerts, new java.security.SecureRandom());
			HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

			// Create all-trusting host name verifier
			HostnameVerifier allHostsValid = new HostnameVerifier() {
				public boolean verify(String hostname, SSLSession session) {
					return true;
				}
			};

			// Install the all-trusting host verifier
			HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
			URL obj = new URL(url);
			con = (HttpsURLConnection) obj.openConnection();
			con.setRequestMethod("POST");
			con.setDoOutput(true);
			con.setConnectTimeout(120000);

			con.setReadTimeout(120000);
			System.err.println("RequestMethod: " + con.getRequestMethod() + ",connecttimeout: "
					+ con.getConnectTimeout() + ",readtimeout: " + con.getReadTimeout());
			con.setRequestProperty("Content-Type", "application/json");

			OutputStream os = con.getOutputStream();
			os.write(inputdata.getBytes("utf8"));
			os.flush();

			in = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
			String inputLine;
			StringBuffer response = new StringBuffer();
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			return response.toString();
		} catch (ConnectException e) {
			e.printStackTrace();
			return "-1";
		} catch (UnknownHostException e) {
			e.printStackTrace();
			return "-1";
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return "-1";
		} catch (Exception e) {
			e.printStackTrace();
			String errorMsg = e.getMessage().toLowerCase();
			if ((errorMsg.contains("sslexception")) || (errorMsg.contains("(404)not found"))
					|| (errorMsg.contains("connection timed out") || (errorMsg.contains("connect timed out")))) {
				return "-1";
			}
			return null;
		} finally {
			try {
				if (con != null) {
					con.disconnect();
				}
				in.close();
			} catch (Exception e2) {
			}
		}
	}
}
