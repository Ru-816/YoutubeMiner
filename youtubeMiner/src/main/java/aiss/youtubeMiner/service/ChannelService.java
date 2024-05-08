package aiss.youtubeMiner.service;

import aiss.youtubeMiner.model.caption.Caption;
import aiss.youtubeMiner.model.channel.Channel;
import aiss.youtubeMiner.model.comment.Comment;
import aiss.youtubeMiner.model.comment.CommentSnippet;
import aiss.youtubeMiner.model.videoSnippet.VideoSnippet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import aiss.youtubeMiner.service.VideoService;
import java.util.Arrays;
import java.util.List;

@Service
public class ChannelService {

    @Autowired
    RestTemplate restTemplate;
    @Autowired
    VideoService videoService;
    @Autowired
    CommentsService commentsService;
    @Autowired
    CaptionsService captionsService;

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
        List<VideoSnippet> videosDelCanal = videoService.listVideoByChannelId(canal.getId(), token, maxVideos);
        for(VideoSnippet video: videosDelCanal){
            List<Comment> comentarios = commentsService.listCommentsByVideoId(video.getId().getVideoId(), token, maxComments);
            video.setComments(comentarios);
            List<Caption> subtitulos = captionsService.listCaptionsByVideoId(video.getId().getVideoId(), token);
            video.setCaptions(subtitulos);
        }
        canal.setVideos(videosDelCanal);
        String uriVideoMiner = "http://localhost:8080/videominer/channels/"+canal.getId()+"?maxVideos="+maxVideos+"&maxComments="+maxComments;
        restTemplate.postForObject(uriVideoMiner, canal, Channel.class);
        canal.getVideos();
    }

}
