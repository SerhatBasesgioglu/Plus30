package com.aydakar.plus30backend.util;

import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

import javax.net.ssl.SSLException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class LCUConnector{
    private String appPort;
    private String authToken;
    WebClient client;


    public LCUConnector(){
        commandLineFetcher();
    }

    public void connect() {
        SslContext sslContext = null;
        try {
            sslContext = SslContextBuilder
                    .forClient()
                    .trustManager(InsecureTrustManagerFactory.INSTANCE)
                    .build();
        } catch (SSLException e) {
            throw new RuntimeException(e);

        }
        SslContext finalSslContext = sslContext;
        HttpClient httpClient = HttpClient.create().secure(t -> t.sslContext(finalSslContext));

        client = WebClient.builder()
                .baseUrl("https://127.0.0.1:" + this.appPort)
                .defaultHeader("Authorization", this.authToken)
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .build();
    }

    public void disconnect(){
        //May not be necessary, not sure.
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
    private String encodeString(String input) {
        byte[] encodedBytes = Base64.getEncoder().encode(input.getBytes(StandardCharsets.UTF_8));
        return new String(encodedBytes, StandardCharsets.UTF_8);
    }

    //get,post,put,delete requests for the LCU api, uses non-blocking logic, for synchronous response
    // .block() can be added
    public Mono<String> get(String endpoint){
        return client.get()
                .uri(endpoint)
                .retrieve()
                .bodyToMono(String.class);
    }

    public Mono<String> post(String endpoint, String postData){
        return client.post()
                .uri(endpoint)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(postData), String.class)
                .retrieve()
                .bodyToMono(String.class);
    }

    public Mono<String> put(String endpoint, String putData){
        return client.put()
                .uri(endpoint)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(putData), String.class)
                .retrieve()
                .bodyToMono(String.class);
    }

    public Mono<String> delete(String endpoint){
        return client.delete()
                .uri(endpoint)
                .retrieve()
                .bodyToMono(String.class);
    }

    //Shows appPort and authToken
    public void printInfo(){
        System.out.println(this.appPort + this.authToken);
    }

    //Can be used to test whether the connection is working
    public void test(){
        String result = this.get("/lol-summoner/v1/current-summoner").block();
        System.out.println(result);
    }

}
