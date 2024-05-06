package aiss.youtubeMiner.service;

import aiss.youtubeMiner.model.caption.Caption;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
public class CaptionsServiceTest {

    @Autowired
    CaptionsService service;

    @Test
    @DisplayName("List captions")
    void listCaptions(){
        List<Caption> captions = service.listCaptions("UOUBW8bkjQ4","AIzaSyAxEkj8TANdB9Qb-R8syu8A-RDuh1wIQLo");
        assertFalse(captions.isEmpty(), "The list of captions is empty");
    }

}
