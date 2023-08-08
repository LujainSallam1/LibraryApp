package nl.first8.library.service;

import nl.first8.library.domain.Lid;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class LidServiceTest {

    @Autowired
    private LidService service;

    @Test
    public void gettingAllUsers() {
        List<Lid> list = service.getAllUsers();

        assertEquals(list.size(), 0);

        //Adding new user
        Lid lid = new Lid();
        lid.setVoornaam("Rowan");
        lid.setAchternaam("Kieboom");
        lid.setWoonplaats("Den hoorn");
        lid.setAdress("achterdijkshoorn");

        lid = service.addUser(lid);
        list = service.getAllUsers();
        assertEquals(1, list.size());
        assertEquals(lid.getId(), service.getUser(lid.getId()).getId());
        assertEquals(lid.getVoornaam(), service.getUser(lid.getId()).getVoornaam());
        assertEquals(lid.getAchternaam(), service.getUser(lid.getId()).getAchternaam());
        assertEquals(lid.getAdress(), service.getUser(lid.getId()).getAdress());
    }

    @Test
    public void getNonExistingLid() {
        assertThrows(ResponseStatusException.class, () -> {
            service.getUser(0L);
        }, "Lid not found!");
    }

    @Test
    public void addInvalidLid() {
        assertThrows(ResponseStatusException.class, () -> {
            service.addUser(null);
        });

    }

}