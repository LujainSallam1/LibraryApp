import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import nl.first8.library.controller.LedenControlller;
import nl.first8.library.domain.Leden;
import nl.first8.library.repository.LedenRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class LedenControllerTest {

    @InjectMocks
    private LedenControlller ledenController;

    @Mock
    private LedenRepository ledenRepository;
    List<Leden> mockLedenList = new ArrayList<>();
    @BeforeEach
    public void setUp() {

        Leden Lid2=new Leden(); Leden Lid1=new Leden();
        Lid1.setId(6L);
        Lid1.setNaam("Lujain");
        Lid1.setAdres("zijlkade");
        Lid1.setWoonplaats("Nieuwland");
        Lid2.setId(6L);
        Lid2.setNaam("Lujain");
        Lid2.setAdres("zijlkade");
        Lid2.setWoonplaats("Nieuwland");
        mockLedenList.add(Lid1);
        mockLedenList.add(Lid2);
        when(ledenRepository.findAll()).thenReturn(mockLedenList);
    }

    @Test
    public void testGetAllLeden() {
        List<Leden> result = ledenController.getAll();
        assertEquals(mockLedenList, result);

        verify(ledenRepository).findAll();
    }}
//    @Test
//        public void testGetById_ExistingId() {
//            Long existingId = 1L;
//            Leden Lid1=new Leden();
//            Lid1.setId(existingId);
//            Lid1.setNaam("Lujain");
//            Lid1.setAdres("zijlkade");
//            Lid1.setWoonplaats("Nieuwland");
//
//            LedenRepository ledenRepository = mock(LedenRepository.class);
//            when(ledenRepository.findById(existingId)).thenReturn(Optional.of(Lid1));
//
//
//            LedenControlller ledenController = new LedenControlller();
//
//            ResponseEntity<Leden> response = ledenController.getById(existingId);
//
//
//            assertEquals(ResponseEntity.ok(Lid1), response);
//        }}
////
////        @Test
////        public void testGetById_NonExistingId() {
////            Long nonExistingId = 999L;
////            LedenRepository ledenRepository = mock(LedenRepository.class);
////            when(ledenRepository.findById(nonExistingId)).thenReturn(Optional.empty());
////
////
////            LedenControlller ledenController = new LedenControlller();
////
////
////            ResponseEntity<Leden> response = ledenController.getById(nonExistingId);
////
////            assertEquals(ResponseEntity.notFound().build(), response);
////        }
////    }
////
//
//
