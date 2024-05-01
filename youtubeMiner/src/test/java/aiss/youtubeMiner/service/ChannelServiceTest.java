package aiss.youtubeMiner.service;

import aiss.youtubeMiner.model.channel.Channel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class ChannelServiceTest {
    @Autowired
    ChannelService service;

    @Test
    @DisplayName("Get channel by title")
    void listChannelByTitle() {
        Channel canal = service.listChannelByTitle("@Leafyradio","AIzaSyAxEkj8TANdB9Qb-R8syu8A-RDuh1wIQLo");
        assertNotNull(canal, "The channel is null");
        System.out.println(canal);
    }
    @Test
    @DisplayName("Get channel by id")
    void listChannelById() {
        List<Channel> canales = service.listChannelById("UClVrJwcIy7saPcGc1nct80A","AIzaSyAxEkj8TANdB9Qb-R8syu8A-RDuh1wIQLo");
        assertFalse(canales.isEmpty(), "The list of channels is empty");
        System.out.println(canales);
    }
}
