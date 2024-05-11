package aiss.youtubeMiner.service;


import aiss.youtubeMiner.model.youtube.caption.Caption;
import aiss.youtubeMiner.model.youtube.caption.CaptionSearch;
import aiss.youtubeMiner.model.youtube.videoSnippet.VideoSnippetSearch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
public class CaptionsService {
    @Autowired
    RestTemplate restTemplate;

    public List<Caption> listCaptionsByVideoId(String videoId, String token){
        String uri = "https://www.googleapis.com/youtube/v3/captions?part=snippet&videoId="+videoId+"&key="+token;
        ResponseEntity<CaptionSearch> response = restTemplate.getForEntity(uri, CaptionSearch.class);
        return Objects.requireNonNull(response.getBody()).getItems();
    }
}
