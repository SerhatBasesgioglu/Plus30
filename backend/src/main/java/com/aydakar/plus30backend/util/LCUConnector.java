package com.aydakar.plus30backend.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

import javax.net.ssl.SSLException;

import static com.aydakar.plus30backend.util.CommandLineFetcher.commandLineFetch;

@Component
public class LCUConnector {
    private final String appPort;
    private final String authToken;
    WebClient client;
    ObjectMapper objectMapper;

    public LCUConnector(ObjectMapper objectMapper) {
        this.appPort = commandLineFetch().get("appPort");
        this.authToken = commandLineFetch().get("authToken");
        this.objectMapper = objectMapper;
    }

    public void connect() {
        SslContext sslContext;
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

    private void disconnect() {
        //May not be necessary, after the implementation change into public.
    }

    /*
    get,post,put,delete requests for the LCU api, normally it uses non-blocking logic, but
    I am converting it to blocking with .block(), later on can be changed to non-block for
    better performance.
     */

    public JsonNode get(String endPoint){
        return execute("get", endPoint, null);
    }

    public JsonNode post(String endPoint){
        return execute("post", endPoint, null);

    }
    public JsonNode post(String endPoint, JsonNode data){
        return execute("post", endPoint, data);
    }

    public JsonNode put(String endPoint){
        return execute("put", endPoint, null);

    }
    public JsonNode put(String endPoint, JsonNode data){
        return execute("put", endPoint, data);
    }

    public JsonNode delete(String endPoint){
        return execute("delete", endPoint, null);
    }

    private JsonNode execute(String method, String endPoint, JsonNode data){
        WebClient.ResponseSpec responseSpec = null;
        try {
            responseSpec = switch (method) {
                case "get" -> client.get().uri(endPoint).retrieve();
                case "delete" -> client.delete().uri(endPoint).retrieve();
                case "post" -> (data == null) ?
                        client.post().uri(endPoint).contentType(MediaType.APPLICATION_JSON).retrieve() :
                        client.post().uri(endPoint).contentType(MediaType.APPLICATION_JSON).body(Mono.just(data), JsonNode.class).retrieve();
                case "put" -> (data == null) ?
                        client.put().uri(endPoint).contentType(MediaType.APPLICATION_JSON).retrieve() :
                        client.put().uri(endPoint).contentType(MediaType.APPLICATION_JSON).body(Mono.just(data), JsonNode.class).retrieve();
                default -> null;
            };
            if (responseSpec != null) {
                JsonNode response = responseSpec.bodyToMono(JsonNode.class)
                        .onErrorResume(WebClientResponseException.class, e ->
                                Mono.just(e.getResponseBodyAs(JsonNode.class)))
                        .block();
                System.out.println(response);
                return response;
            }
        }catch(Exception e) {
            return objectMapper.valueToTree(e);
        }
        return objectMapper.valueToTree("There was an error during the initialization of connector");
    }

    public void printInfo() {
        System.out.println(this.appPort + this.authToken);
    }
}