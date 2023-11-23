package com.aydakar.plus30backend.util;

import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
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

public class LCUConnector{
    private String appPort;
    private String authToken;
    WebClient client;


    public LCUConnector(){
        commandLineFetcher();
    }

    public void connect() throws SSLException {
        SslContext sslContext = SslContextBuilder
                .forClient()
                .trustManager(InsecureTrustManagerFactory.INSTANCE)
                .build();
        HttpClient httpClient = HttpClient.create().secure(t -> t.sslContext(sslContext));

        client = WebClient.builder()
                .baseUrl("https://127.0.0.1:" + this.appPort)
                .defaultHeader("Authorization", this.authToken)
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .build();
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

    public void get(String endpoint){
        Mono<Map> result = client.get()
                .uri(endpoint)
                .retrieve()
                .bodyToMono(Map.class);
        result.subscribe(System.out::println);
    }

    public void post(String endpoint, Map<String,Object> postData){
        Mono<String> result = client.post()
                .uri(endpoint)
                .body(Mono.just(postData), Map.class)
                .retrieve()
                .bodyToMono(String.class);
        result.subscribe(System.out::println);
    }

    public void put(String endpoint, Map<String,Object> putData){
        Mono<String> result = client.put()
                .uri(endpoint)
                .body(Mono.just(putData), Map.class)
                .retrieve()
                .bodyToMono(String.class);
        result.subscribe(System.out::println);
    }

    public void delete(String endpoint){
        Mono<Void> result = client.delete()
                .uri(endpoint)
                .retrieve()
                .bodyToMono(Void.class);
        result.subscribe(System.out::println);
    }

    //Shows appPort and authToken
    public void printInfo(){
        System.out.println(this.appPort + this.authToken);
    }

    public void test(){
        Mono<Map> result = client.get().uri("/lol-summoner/v1/current-summoner").retrieve().bodyToMono(Map.class);
        System.out.println(result);
        result.subscribe(System.out::println);
    }

}
