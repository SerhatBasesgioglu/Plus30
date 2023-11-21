package com.aydakar.plus30backend.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.aydakar.plus30backend.util.SSLManager.disableSSLVerification;

public class LCUConnector{
    private String appPort;
    private String authToken;


    public LCUConnector(){

    }

    public void connect(){
        commandLineFetcher();
    }

    public void disconnect(){

    }

    //Gets appPort and authToken from commandline
    private void commandLineFetcher(){
        String command = "wmic PROCESS WHERE name='LeagueClientUx.exe' GET commandline";
        StringBuilder output = new StringBuilder();
        String authToken = "";
        String appPort = "";

        try{
            ProcessBuilder processBuilder = new ProcessBuilder(command.split("\\s+"));
            Process process = processBuilder.start();

            //Gets raw byte stream, turns into characters, reads with buffer
            InputStream inputStream = process.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line;

            while((line = bufferedReader.readLine()) != null){
                output.append(line).append("\n");
            }

            bufferedReader.close();
            inputStreamReader.close();
            inputStream.close();
        }
        catch(IOException e){
            e.printStackTrace();
        }

        String appPortPattern = "--app-port=([0-9]*)";
        String authTokenPattern = "--remoting-auth-token=([\\w-]*)";

        Pattern appPortPatternCompiled = Pattern.compile(appPortPattern);
        Pattern authTokenPatternCompiled = Pattern.compile(authTokenPattern);

        Matcher appPortMatcher = appPortPatternCompiled.matcher(output);
        Matcher authTokenMatcher = authTokenPatternCompiled.matcher(output);

        if (appPortMatcher.find()) {
            appPort = appPortMatcher.group(1);
        }

        if (authTokenMatcher.find()) {
            authToken = authTokenMatcher.group(1);
        }

        authToken = "Basic " + encodeString("riot:" + authToken);

        this.authToken = authToken;
        this.appPort = appPort;

    }

    //Base64 encoding for authToken
    private static String encodeString(String input) {
        byte[] encodedBytes = Base64.getEncoder().encode(input.getBytes(StandardCharsets.UTF_8));
        return new String(encodedBytes, StandardCharsets.UTF_8);
    }

    public void req(String httpType, String endpoint) throws IOException {
        disableSSLVerification();

        String url = "https://127.0.0.1:" + this.appPort + endpoint;
        URL reqUrl = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) reqUrl.openConnection();
        connection.setRequestMethod(httpType);
        connection.setRequestProperty("Authorization", this.authToken);

        int responseCode = connection.getResponseCode();

        if(responseCode == HttpURLConnection.HTTP_OK){
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            StringBuilder response = new StringBuilder();

            while((line = reader.readLine()) != null){
                response.append(line);
            }
            reader.close();

            System.out.println("Response Content:");
            System.out.println(response.toString());
        } else {
            System.out.println("Error: " + responseCode);
        }


    }

    //Shows appPort and authToken
    public void printInfo(){
        System.out.println(this.appPort + this.authToken);
    }


}
