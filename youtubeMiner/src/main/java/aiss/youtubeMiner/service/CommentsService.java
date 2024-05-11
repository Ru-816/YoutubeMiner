package aiss.youtubeMiner.service;


import aiss.youtubeMiner.model.youtube.comment.Comment;
import aiss.youtubeMiner.model.youtube.comment.CommentSearch;
import aiss.youtubeMiner.model.youtube.videoSnippet.VideoSnippetSearch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
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

    public List<Comment> listCommentsByVideoId(String videoId, String token, String maxComments){
        String uri = "https://www.googleapis.com/youtube/v3/commentThreads?maxResults="+maxComments+"&key="+token+"&part=snippet&videoId="+videoId;
        ResponseEntity<CommentSearch> response = restTemplate.getForEntity(uri, CommentSearch.class);
        return Objects.requireNonNull(response.getBody()).getItems();
    }


}

