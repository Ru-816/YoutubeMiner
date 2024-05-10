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

    @GetMapping("/channels/{name}")
    public Channel findById(@PathVariable String name, String maxVideos, String maxComments, String token) {
        Channel channel = channelService.getChannelAllInfo(name, maxVideos, maxComments, token);
        return channel;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/channels/{name}")
    public void create(@PathVariable String name, String maxVideos, String maxComments, String token) {
        channelService.postChannel(name, maxVideos, maxComments, token);
    }

}