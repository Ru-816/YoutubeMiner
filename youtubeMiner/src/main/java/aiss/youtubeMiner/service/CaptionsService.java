package aiss.youtubeMiner.service;


import aiss.youtubeMiner.model.caption.Caption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.UnknownHttpStatusCodeException;

import java.util.Arrays;
import java.util.List;

@Service
public class CaptionsService {
    @Autowired
    RestTemplate restTemplate;

    public List<Caption> listCaptionsByVideoId(String videoId, String token){
        String uri = "https://www.googleapis.com/youtube/v3/captions?part=snippet&videoId="+videoId+"&key="+token;
        Caption[] captions = restTemplate.getForObject(uri, Caption[].class);
        return Arrays.stream(captions).toList();
    }
}
