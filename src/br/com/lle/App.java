package br.com.lle;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import com.fasterxml.jackson.databind.ObjectMapper;

public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Sending POST to GCM" );

        String apiKey = "AIzaSyAJkI3UGZ2u6hlj3uAIpPFhnJ4X29q5bgw";
        Content content = createContent();

        POST2GCM.post(apiKey, content);
    }

    public static Content createContent(){

        Content c = new Content();

        c.addRegId("APA91bFlzrQS8tDD7C1ao5YqfbZEsLNyrfymaWhW-a6vm7f3Vde4ufxXp_ods6WGw9WLa6F71pzsqd29GcDTiAMZwV_gXiKvOFSu4fEhxRcMWe3SnMy0WS1pbc17vnMR3yLxkXCf9vxFHzERBdcWRo0yB8Oski6cYopbhBFTpXFKk5A1YRRvlTc");
        String msg = "PETR4 - 14,60";
        c.createData(msg, msg);

        return c;
    }
}
