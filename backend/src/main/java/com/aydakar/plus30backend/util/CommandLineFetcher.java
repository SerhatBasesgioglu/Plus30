package com.aydakar.plus30backend.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommandLineFetcher {
    public static Map<String,String> commandLineFetch(){
        String command = "wmic PROCESS WHERE name='LeagueClientUx.exe' GET commandline";
        StringBuilder output = new StringBuilder();
        Map<String, String> resultMap = new HashMap<>();
        String authToken = "";
        String appPort = "";

        try {
            ProcessBuilder processBuilder = new ProcessBuilder(command.split("\\s+"));
            Process process = processBuilder.start();

            //Gets raw byte stream, turns into characters, reads with buffer
            InputStream inputStream = process.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                output.append(line).append("\n");
            }

            bufferedReader.close();
            inputStreamReader.close();
            inputStream.close();
        } catch (IOException e) {
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

        resultMap.put("authToken", authToken);
        resultMap.put("appPort", appPort);

        return resultMap;
    }

    private static String encodeString(String input) {
        byte[] encodedBytes = Base64.getEncoder().encode(input.getBytes(StandardCharsets.UTF_8));
        return new String(encodedBytes, StandardCharsets.UTF_8);
    }
}
