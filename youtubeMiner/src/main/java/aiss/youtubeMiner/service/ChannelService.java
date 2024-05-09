package aiss.youtubeMiner.service;

import aiss.youtubeMiner.model.videominer.User;
import aiss.youtubeMiner.model.videominer.Video;
import aiss.youtubeMiner.model.youtube.caption.Caption;
import aiss.youtubeMiner.model.youtube.channel.Channel;
import aiss.youtubeMiner.model.youtube.comment.Comment;
import aiss.youtubeMiner.model.youtube.videoSnippet.VideoSnippet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
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

    public Channel getChannelAllInfo(String name, String maxVideos, String maxComments, String token){
        Channel canal = listChannelByTitle(name,token);
        List<VideoSnippet> videosDelCanal = videoService.listVideoByChannelId(canal.getId(), token, maxVideos);
        for(VideoSnippet video: videosDelCanal){
            List<Comment> comentarios = commentsService.listCommentsByVideoId(video.getId().getVideoId(), token, maxComments);
            video.setComments(comentarios);
            List<Caption> subtitulos = captionsService.listCaptionsByVideoId(video.getId().getVideoId(), token);
            video.setCaptions(subtitulos);
        }
        canal.setVideos(videosDelCanal);
        return canal;
    }

    public void postChannel(String name, String maxVideos, String maxComments, String token) {
        Channel canal = getChannelAllInfo(name, maxVideos, maxComments, token);
        String uriVideoMiner = "http://localhost:8080/videominer/channels/"+canal.getId()+"?maxVideos="+maxVideos+"&maxComments="+maxComments;
        aiss.youtubeMiner.model.videominer.Channel canalVideoMiner = new aiss.youtubeMiner.model.videominer.Channel();

        canalVideoMiner.setId(canal.getId());
        canalVideoMiner.setName(canal.getSnippet().getTitle());
        canalVideoMiner.setDescription(canal.getSnippet().getDescription());
        canalVideoMiner.setCreatedTime(canal.getSnippet().getPublishedAt());
        canalVideoMiner.setVideos(transformarVideos(canal.getVideos()));

        restTemplate.postForObject(uriVideoMiner, canalVideoMiner, aiss.youtubeMiner.model.videominer.Channel.class);
    }

    private List<Video> transformarVideos(List<VideoSnippet> videosYT){
        List<Video> listaVideosVideoMiner = new ArrayList<>();
        for(VideoSnippet videoYT:videosYT){
            Video videoVideoMiner = new Video();

            videoVideoMiner.setId(videoYT.getId().getVideoId());
            videoVideoMiner.setCaptions(transformarCaptions(videoYT.getCaptions()));
            videoVideoMiner.setComments(transformarComments(videoYT.getComments()));
            videoVideoMiner.setDescription(videoYT.getSnippet().getDescription());
            videoVideoMiner.setReleaseTime(videoYT.getSnippet().getPublishedAt());
            videoVideoMiner.setName(videoYT.getSnippet().getTitle());

            listaVideosVideoMiner.add(videoVideoMiner);
        }
        return listaVideosVideoMiner;
    }
    private List<aiss.youtubeMiner.model.videominer.Caption> transformarCaptions(List<Caption> captionsYT){
        List<aiss.youtubeMiner.model.videominer.Caption> listaCaptionsVideoMiner = new ArrayList<>();
        for(Caption captionYT:captionsYT){
            aiss.youtubeMiner.model.videominer.Caption captionVideoMiner = new aiss.youtubeMiner.model.videominer.Caption();

            captionVideoMiner.setId(captionYT.getId());
            captionVideoMiner.setLanguage(captionYT.getSnippet().getLanguage());
            captionVideoMiner.setName(captionYT.getSnippet().getName());

            listaCaptionsVideoMiner.add(captionVideoMiner);
        }
        return listaCaptionsVideoMiner;
    }

    private List<aiss.youtubeMiner.model.videominer.Comment> transformarComments(List<Comment> commentsYT){
        List<aiss.youtubeMiner.model.videominer.Comment> listaCommentsVideoMiner = new ArrayList<>();
        for(Comment commentYT:commentsYT){
            aiss.youtubeMiner.model.videominer.Comment commentVideoMiner = new aiss.youtubeMiner.model.videominer.Comment();

            commentVideoMiner.setId(commentYT.getCommentSnippet().getTopLevelComment().getId());
            commentVideoMiner.setCreatedOn(commentYT.getCommentSnippet().getTopLevelComment().getSnippet().getPublishedAt());
            commentVideoMiner.setText(commentYT.getCommentSnippet().getTopLevelComment().getSnippet().getTextOriginal());
            commentVideoMiner.setAuthor(crearUser(commentYT));

            listaCommentsVideoMiner.add(commentVideoMiner);
        }
        return listaCommentsVideoMiner;
    }

    private User crearUser(Comment comentario){
        User usuario = new User();

        usuario.setId(Long.valueOf(comentario.getCommentSnippet().getTopLevelComment().getSnippet().getAuthorChannelId().getValue()));
        usuario.setName(comentario.getCommentSnippet().getTopLevelComment().getSnippet().getAuthorDisplayName());
        usuario.setUser_link(comentario.getCommentSnippet().getTopLevelComment().getSnippet().getAuthorChannelUrl());
        usuario.setPicture_link(comentario.getCommentSnippet().getTopLevelComment().getSnippet().getAuthorProfileImageUrl());

        return usuario;
    }
}
