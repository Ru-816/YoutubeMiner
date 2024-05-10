package aiss.youtubeMiner.controller;

import aiss.youtubeMiner.model.youtube.channel.Channel;
import aiss.youtubeMiner.service.ChannelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/youtube")
public class ChannelController {

    @Autowired
    ChannelService channelService;

    @GetMapping("/channels/{id}")
    public Channel findById(@PathVariable String id, String maxVideos, String maxComments, String token) {
        Channel channel = channelService.getChannelAllInfo(id, maxVideos, maxComments, token);
        return channel;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/channels/{id}")
    public void create(@PathVariable String id, String maxVideos, String maxComments, String token) {
        channelService.postChannel(id, maxVideos, maxComments, token);
    }

}