import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import nl.first8.library.controller.MemberController;
import nl.first8.library.domain.entity.Member;
import nl.first8.library.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class MemberControllerTest {

    @InjectMocks
    private MemberController membersController;

    @Mock
    private MemberRepository memberRepository;
    List<Member> mockMemberList = new ArrayList<>();
    @BeforeEach
    public void setUp() {

        Member Member2=new Member(); Member Member1=new Member();
        Member1.setId(6L);
        Member1.setName("Lujain");
        Member1.setAdress("zijlkade");
        Member1.setCity("Nieuwland");
        Member2.setId(6L);
        Member2.setName("Lujain");
        Member2.setAdress("zijlkade");
        Member2.setCity("Nieuwland");
        mockMemberList.add(Member1);
        mockMemberList.add(Member2);
        when(memberRepository.findAll()).thenReturn(mockMemberList);
    }

    @Test
    public void testGetAllMembers() {
        List<Member> result = membersController.getAll();
        assertEquals(mockMemberList, result);

        verify(memberRepository).findAll();
    }}
//    @Test
//        public void testGetById_ExistingId() {
//            Long existingId = 1L;
//            Members Member1=new Members();
//            Member1.setId(existingId);
//            Member1.setName("Lujain");
//            Member1.setAdress("zijlkade");
//            Member1.setCity("Nieuwland");
//
//            MembersRepository membersRepository = mock(MembersRepository.class);
//            when(membersRepository.findById(existingId)).thenReturn(Optional.of(Member1));
//
//
//            MembersControlller membersController = new MembersControlller();
//
//            ResponseEntity<Members> response = membersController.getById(existingId);
//
//
//            assertEquals(ResponseEntity.ok(Member1), response);
//        }}
////
////        @Test
////        public void testGetById_NonExistingId() {
////            Long nonExistingId = 999L;
////            MembersRepository membersRepository = mock(MembersRepository.class);
////            when(membersRepository.findById(nonExistingId)).thenReturn(Optional.empty());
////
////
////            MembersControlller membersController = new MembersControlller();
////
////
////            ResponseEntity<Members> response = membersController.getById(nonExistingId);
////
////            assertEquals(ResponseEntity.notFound().build(), response);
////        }
////    }
////
//
//
