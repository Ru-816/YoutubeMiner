package aiss.youtubeMiner.service;

import aiss.youtubeMiner.model.youtube.channel.Channel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class CommentsServiceTest {
    @Autowired
    CommentsService service;

    /*@Test
    @DisplayName("Get channel by title")
    void listComments() {
        Channel canal = service.listChannelByTitle("@Leafyradio","AIzaSyAxEkj8TANdB9Qb-R8syu8A-RDuh1wIQLo");
        assertNotNull(canal, "The channel is null");
        System.out.println(canal);
    }
    @Test
    @DisplayName("Get channel by title")
    void listCommentsParent() {
        Channel canal = service.listChannelByTitle("@Leafyradio","AIzaSyAxEkj8TANdB9Qb-R8syu8A-RDuh1wIQLo");
        assertNotNull(canal, "The channel is null");
        System.out.println(canal);
    }*/
}
