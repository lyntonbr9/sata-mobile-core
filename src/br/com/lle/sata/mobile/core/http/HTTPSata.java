package br.com.lle.sata.mobile.core.http;

import static br.com.lle.sata.util.LogUtil.log;
import static br.com.lle.sata.util.StringUtil.concat;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Scanner;

public class HTTPSata {
	
	/**
	 * The POST method.
	 * 
	 * @param targetURL
	 *            : The URL to POST to.
	 * @param contentHash
	 *            : The hashtable of the parameters to be posted.
	 * 
	 * @return The String returned as a result of POSTing.
	 */
	public static String POST(String targetURL, Hashtable<String, String> contentHash) {
		try {
			setProxyProperties();
			
			URL url;
			HttpURLConnection conn;
			// The data streams used to read from and write to the URL connection.
			DataOutputStream out;
			DataInputStream in;

			// String returned as the result of the POST.
			String resultado = "";

			// Create the URL object and make a connection to it.
			url = new URL(targetURL);
			conn = (HttpURLConnection) url.openConnection();

			// Set connection parameters. We need to perform input and output,
			// so set both as true.
			conn.setDoInput(true);
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("charset", "UTF-8");

			// Disable use of caches.
			conn.setUseCaches(false);

			// Set the content type we are POSTing. We impersonate it as
			// encoded form data
			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

			// get the output stream to POST to.
			out = new DataOutputStream(conn.getOutputStream());
			String content = "";

			// Create a single String value to be POSTED from the parameters passed
			// to us. This is done by making "name"="value" pairs for all the keys
			// in the Hashtable passed to us.
			Enumeration<String> e = contentHash.keys();
			boolean first = true;
			while (e.hasMoreElements()) {
				// For each key and value pair in the hashtable
				Object key = e.nextElement();
				Object value = contentHash.get(key);
				
				// If this is not the first key-value pair in the hashtable,
				// concantenate an "&" sign to the constructed String
				if (!first) {
					content = concat(content, "&");
				}
					
				// append to a single string. Encode the value portion
				content = concat(content, key, "=", URLEncoder.encode((String) value, "UTF-8"));

				first = false;
			}
			
			// Write out the bytes of the content string to the stream.
			out.writeBytes(content);
			out.flush();
			out.close();

			// Read input from the input stream.
			in = new DataInputStream(conn.getInputStream());
			
			// Read the input stream
			Scanner sc = new Scanner(in);
			while(sc.hasNextLine()) {
				resultado = concat(resultado, sc.nextLine(), "\n");
			}

			in.close();
			sc.close();
			
			// faz a conversão de UTF-8 para ISO-8859-1 por causa da acentuacao
			String respostaUTF8 = new String(resultado.getBytes(), "UTF-8");
			resultado = new String(respostaUTF8.getBytes(), "ISO-8859-1");
			// return the string that was read.
			return resultado;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "";
	}
	
	/**
	 * The GET method.
	 * 
	 * @param targetURL
	 *            : The URL to GET.
	 * @param contentHash
	 *            : The hashtable of the parameters to be send.
	 * 
	 * @return The String returned as a result of GETing.
	 */
	public static String GET(String targetURL, Hashtable<String, String> contentHash) {
		// String returned as the result of the GET.
		String content = "";
		try {
			setProxyProperties();
			
			URL url;
			HttpURLConnection conn;
			// The data streams used to read from and write to the URL connection.
			DataInputStream in;

			// Create the URL object and make a connection to it.
			url = new URL(targetURL);
			conn = (HttpURLConnection) url.openConnection();

			// Set connection parameters. We need to perform input.
			conn.setDoInput(true);
			conn.setRequestMethod("GET");
			conn.setRequestProperty("charset", "UTF-8");

			// Disable use of caches.
			conn.setUseCaches(false);

			// Set the content type we are POSTing. We impersonate it as
			// encoded form data
//			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			
			// Read input from the input stream.
			in = new DataInputStream(conn.getInputStream());
			
			// Read the input stream
			Scanner sc = new Scanner(in);
			while(sc.hasNextLine()) {
				content = concat(content, sc.nextLine(), "\n");
			}
			// close the streams
			in.close();
			sc.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return content;
	}
	
	private static void setProxyProperties() {
//		if (SATAUtil.isAmbienteDesenvolvimento()) {
//			System.setProperty("http.proxyHost", "proxyad.br-petrobras.com.br");
//			System.setProperty("http.proxyPort", "9090");
//		}
	}
	
	public static void main(String[] args) {
		String ativo = "PETRH20";
		String urlCotacao = concat("http://br.advfn.com/bolsa-de-valores/bovespa/", ativo, "/cotacao");
		String html = HTTPSata.GET(urlCotacao, null);
		log(html);
	}

}
