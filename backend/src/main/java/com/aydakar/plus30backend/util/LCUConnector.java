package com.aydakar.plus30backend.util;

import com.aydakar.plus30backend.entity.ApiError;
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
import java.io.IOException;
import java.util.Map;

import static com.aydakar.plus30backend.util.CommandLine.getCredentials;

@Component
public class LCUConnector {
    private final String appPort;
    private final String authToken;
    WebClient client;
    ObjectMapper objectMapper;
    Map<String, String> credentials = getCredentials();

    public LCUConnector(ObjectMapper objectMapper) {
        this.appPort = credentials.get("appPort");
        this.authToken = credentials.get("authToken");
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
    }


    public <T> T get(String endPoint, Class<T> responseType) {
        return execute("get", endPoint, null, responseType);
    }

    public <T> T post(String endPoint, Class<T> responseType) {
        return execute("post", endPoint, null, responseType);
    }

    public <T, R> T post(String endPoint, R data, Class<T> responseType) {
        return execute("post", endPoint, data, responseType);
    }

    public <T> T put(String endPoint, Class<T> responseType) {
        return execute("put", endPoint, null, responseType);

    }

    public <T, R> T put(String endPoint, R data, Class<T> responseType) {
        return execute("put", endPoint, data, responseType);
    }

    public <T> T delete(String endPoint, Class<T> responseType) {
        return execute("delete", endPoint, null, responseType);
    }

    private <T, R> T execute(String method, String endPoint, R data, Class<T> responseType) {
        WebClient.ResponseSpec responseSpec;
        try {
            responseSpec = switch (method) {
                case "get" -> client.get().uri(endPoint).retrieve();
                case "delete" -> client.delete().uri(endPoint).retrieve();
                case "post" -> (data == null) ?
                        client.post().uri(endPoint).contentType(MediaType.APPLICATION_JSON).retrieve() :
                        client.post().uri(endPoint).contentType(MediaType.APPLICATION_JSON).body(Mono.just(data), responseType).retrieve();
                case "put" -> (data == null) ?
                        client.put().uri(endPoint).contentType(MediaType.APPLICATION_JSON).retrieve() :
                        client.put().uri(endPoint).contentType(MediaType.APPLICATION_JSON).body(Mono.just(data), responseType).retrieve();
                default -> null;
            };
            if (responseSpec != null) {
                T response = responseSpec.bodyToMono(responseType)
                        .block();
                System.out.println(response);
                return response;
            }
        } catch (WebClientResponseException e) {
            String responseBody = e.getResponseBodyAsString();
            try {
                ApiError apiError = objectMapper.readValue(responseBody, ApiError.class);
                throw new CustomException(apiError.getHttpStatus(), apiError.getMessage());
            } catch (IOException parseException) {
                throw new CustomException(500, "Error parsing error response");
            }

        } catch (Exception e) {
            throw new CustomException(500, "Internal Server Error: " + e.getMessage());
        }
        throw new CustomException(500, "Unknown error occurred");
    }

    public String printInfo() {
        String credentials = this.appPort + this.authToken;
        return credentials;
    }
}