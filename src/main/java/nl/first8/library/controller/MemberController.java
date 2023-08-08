package nl.first8.library.controller;

import nl.first8.library.domain.Member;
import nl.first8.library.domain.Member;
import nl.first8.library.domain.Member;
import nl.first8.library.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/v1")
public class MemberController {
    @Autowired
    private MemberRepository memberRepository;

    @GetMapping("/members")
    public List<Member> getAll() {
        return this.memberRepository.findAll();
    }

    @GetMapping("/members/{id}")
    public ResponseEntity<Member> getById(@PathVariable(value = "id") Long id)  {
        Optional<Member> member = memberRepository.findById(id);

        if (member.isPresent()){
            return ResponseEntity.ok(member.get());
        }
        else {
            //TODO: better response
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/members")
    public Member add(@RequestBody Member member) {

        Member savedMember = memberRepository.save(member);

        return savedMember;
    }

    @PutMapping("/members/{id}")
    public ResponseEntity<Member> update(@PathVariable(value = "id") Long id, @RequestBody Member body) {
        Optional<Member> memberOptional = memberRepository.findById(id);
        Member memberDB;

        if (memberOptional.isPresent()){
            memberDB = memberOptional.get();

            if (Objects.nonNull(body.getFirstName()))           memberDB.setFirstName(body.getFirstName());
            if (Objects.nonNull(body.getLastName()))            memberDB.setLastName(body.getLastName());
            if (Objects.nonNull(body.getStreet()))              memberDB.setStreet(body.getStreet());
            if (Objects.nonNull(body.getHouseNumber()))         memberDB.setHouseNumber(body.getHouseNumber());
            if (Objects.nonNull(body.getHouseNumberSuffix()))   memberDB.setHouseNumberSuffix(body.getHouseNumberSuffix());
            if (Objects.nonNull(body.getPostalCode()))          memberDB.setPostalCode(body.getPostalCode());
            if (Objects.nonNull(body.getCity()))                memberDB.setCity(body.getCity());
            if (Objects.nonNull(body.getCountry()))             memberDB.setCountry(body.getCountry());

        }
        else {
            return ResponseEntity.notFound().build();
        }

        Member updatedMember = memberRepository.save(memberDB);
        return ResponseEntity.ok(updatedMember);
    }

    @PutMapping("/members/{id}/disable")
    public ResponseEntity<Member> disable(@PathVariable(value = "id") Long id) {
        Member member = memberRepository.getById(id);
        if(member.isDisabled()){
            //TODO: better response
            return ResponseEntity.notFound().build();
        }
        else {
            member.setDisabled(true);
        }

        Member updatedMember = memberRepository.save(member);
        return ResponseEntity.ok(updatedMember);
    }

    @PutMapping("/members/{id}/enable")
    public ResponseEntity<Member> enable(@PathVariable(value = "id") Long id) {
        Member member = memberRepository.getById(id);

        member.setDisabled(false);

        Member updatedMember = memberRepository.save(member);
        return ResponseEntity.ok(updatedMember);
    }
}
