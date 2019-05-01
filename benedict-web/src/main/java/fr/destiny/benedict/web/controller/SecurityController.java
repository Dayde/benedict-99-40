package fr.destiny.benedict.web.controller;

import fr.destiny.benedict.web.model.TokenResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import static fr.destiny.benedict.web.config.BenedictConfig.BUNGIE_ROOT_URL;

@Controller
public class SecurityController {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${BUNGIE_CLIENT_ID}")
    private String CLIENT_ID;

    @Value("${BUNGIE_CLIENT_SECRET}")
    private String CLIENT_SECRET;

    @RequestMapping("/login")
    public String login() {
        return String.format(
                "redirect:%s/en/oauth/authorize?client_id=%s&response_type=code&reauth=true",
                BUNGIE_ROOT_URL,
                CLIENT_ID
        );
    }

    @RequestMapping(value = "/token")
    @ResponseBody
    public TokenResponse token(@RequestParam String code) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setBasicAuth(CLIENT_ID, CLIENT_SECRET);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code");
        body.add("code", code);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);

        return restTemplate.postForEntity(
                BUNGIE_ROOT_URL + "/platform/app/oauth/token/",
                request,
                TokenResponse.class
        ).getBody();
    }
}
