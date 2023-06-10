package org.iespring1402.Baloot.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import org.iespring1402.Baloot.models.AuthToken;
import org.iespring1402.Baloot.models.Baloot;
import org.iespring1402.Baloot.models.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.web.bind.annotation.ResponseBody;

@RestController
@RequestMapping("api/v1/oauth")
@CrossOrigin
public class OAuthController {
    @GetMapping(value = "")
    @ResponseBody
    public Object OAuthenticate(@RequestParam String code) throws Exception {
        Map<String, String> accessTokenMap = getAccessTokenMap(code);
        String access_token = accessTokenMap.get("access_token");
        User user = getUserDetails(access_token); // TODO: save user to database or update an existing one
        Map<String, Object> authResponse = new HashMap();

        if (user != null) {
            AuthToken authToken = new AuthToken(Baloot.SECRET_KEY, Baloot.ISSUER);
            authResponse.put("token", authToken.getToken());
            return ResponseEntity.status(HttpStatus.OK).body(authResponse);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Code");
        }
    }

    private User getUserDetails(String accessToken) {
        String apiUrl = "https://api.github.com/user";

        try {
            URL url = new URL(apiUrl);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");

            String authHeader = "token " + accessToken;
            con.setRequestProperty("Authorization", authHeader);

            int responseCode = con.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) { // success
                BufferedReader in = new BufferedReader(new InputStreamReader(
                        con.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                System.out.println(response.toString());
                ObjectMapper mapper = new ObjectMapper();
                Map<String, Object> responseObject = (Map<String, Object>) mapper.readValue(response.toString(),
                        new TypeReference<Map<String, Object>>() {
                        });

                User user = new User((String) responseObject.get("login"), null, (String) responseObject.get("email"),
                        null,
                        (String) responseObject.get("location"), 0);
                return user;

            } else {
                System.out.println("GET request to " + apiUrl + " failed with response code: " + responseCode);
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private Map<String, String> getAccessTokenMap(String code) {
        String clientId = "906f6f3bdced33565fff";
        String clientSecret = "f47665ff533c6f221e4d56a45fdd8a709f61b678";

        String url = "https://github.com/login/oauth/access_token";
        url += "?client_id=" + clientId;
        url += "&client_secret=" + clientSecret;
        url += "&code=" + code;

        try {
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            con.setRequestMethod("POST");
            con.setRequestProperty("Accept", "application/json");
            con.setDoOutput(true);

            byte[] postDataBytes = url.getBytes(StandardCharsets.UTF_8);

            try (OutputStream os = con.getOutputStream()) {
                os.write(postDataBytes);
                os.flush();
            }

            int responseCode = con.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(con.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;

                while ((line = in.readLine()) != null) {
                    response.append(line);
                }

                System.out.println(response.toString());
                ObjectMapper mapper = new ObjectMapper();
                Map<String, String> responseObject = (Map<String, String>) mapper.readValue(response.toString(),
                        new TypeReference<Map<String, String>>() {
                        });

                System.out.println(responseObject.get("access_token"));
                return responseObject;
            } else {
                System.out.println("Request failed with status code " + responseCode);
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
