package aiss.youtubeMiner.service;

import aiss.youtubeMiner.model.channel.Channel;
import aiss.youtubeMiner.model.videoSnippet.VideoSnippet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class ChannelService {

    @Autowired
    RestTemplate restTemplate;

    public Channel listChannelByTitle(String title, String token) {
        String uri = "https://www.googleapis.com/youtube/v3/channels?key="+token+"&part=snippet&forHandle="+title;
        Channel channel = restTemplate.getForObject(uri, Channel.class);
        return channel;
    }

    public List<Channel> listChannelById(String id, String token) {
        String uri = "https://www.googleapis.com/youtube/v3/channels?key="+token+"&part=snippet&id="+id;
        Channel[] channels = restTemplate.getForObject(uri, Channel[].class);
        return Arrays.stream(channels).toList();
    }

    public void getChannelAllInfo(String name, String maxVideos, String maxComments, String token){
        Channel canal = listChannelByTitle(name,token);
        List<VideoSnippet> videosDelCanal =
        for(int i;i<videosDelCanal.size();i++){

        }
        canal.setVideos(videosDelCanal);
        String uriVideoMiner = "http://localhost:8080/videominer/channels/"+canal.getId()+"?maxVideos="+maxVideos+"&maxComments="+maxComments;
        restTemplate.postForObject(uriVideoMiner, canal, Channel.class);
        canal.getVideos();
    }

}
