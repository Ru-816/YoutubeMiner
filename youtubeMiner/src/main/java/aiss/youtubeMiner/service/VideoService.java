package aiss.youtubeMiner.service;

import aiss.youtubeMiner.model.youtube.videoSnippet.VideoSnippet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

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
        VideoSnippet[] videos = restTemplate.getForObject(uri, VideoSnippet[].class);
        return Arrays.stream(videos).toList();
    }


}
