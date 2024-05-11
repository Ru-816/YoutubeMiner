package aiss.youtubeMiner.service;

import aiss.youtubeMiner.model.youtube.videoSnippet.VideoSnippet;
import aiss.youtubeMiner.model.youtube.videoSnippet.VideoSnippetSearch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
public class VideoService {

    @Autowired
    RestTemplate restTemplate;

    public List<VideoSnippet> listVideoById(String id, String token) {
        String uri = "https://www.googleapis.com/youtube/v3/videos?key="+token+"&part=snippet&id="+id;
        VideoSnippet[] videos = restTemplate.getForObject(uri, VideoSnippet[].class);
        return Arrays.stream(videos).toList();
    }

    public List<VideoSnippet> listVideoByChannelId(String channelId, String token, String maxVideos) {
        String uri = "https://www.googleapis.com/youtube/v3/search?key="+token+"&part=snippet&channelId="+channelId+"&maxResults="+maxVideos+"&type=video";
        ResponseEntity<VideoSnippetSearch> response = restTemplate.getForEntity(uri, VideoSnippetSearch.class);
        return Objects.requireNonNull(response.getBody()).getItems();
        //VideoSnippetSearch response = restTemplate.getForObject(uri, VideoSnippetSearch.class);
        //return response.getItems();
    }


}
