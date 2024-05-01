package aiss.youtubeMiner.service;

import aiss.videominer.model.Caption;
import aiss.videominer.model.Comment;
import aiss.videominer.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.UnknownHttpStatusCodeException;

import java.util.Arrays;
import java.util.List;


public class CommentsService {

    @Autowired
    RestTemplate restTemplate;
    public List<Comment> listComments(String commentId, String token){
        String uri = "https://www.googleapis.com/youtube/v3/comments?part=snippet&key="+token+"&id="+commentId;
        Comment[] comments = restTemplate.getForObject(uri, Comment[].class);
        return Arrays.stream(comments).toList();
    }

    public List<Comment> listCommentsParent(String parentId, String token){
        String uri = "https://www.googleapis.com/youtube/v3/comments?part=snippet&key="+token+"&parentId="+parentId;
        Comment[] comments = restTemplate.getForObject(uri, Comment[].class);
        return Arrays.stream(comments).toList();
    }


}

