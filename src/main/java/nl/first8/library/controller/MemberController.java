package nl.first8.library.controller;

import nl.first8.library.domain.Member;
import nl.first8.library.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
public class MemberController {
    @Autowired
    private MemberRepository memberRepository;

    @GetMapping("/members")
    public List<Member> getAll() {
        return memberRepository.findAll();
    }

    @GetMapping("/members/{memberid}")
    public ResponseEntity<Member> getById(@PathVariable(value = "memberid") Long memberid) {
        Member member = memberRepository.findById(memberid).get();
        return ResponseEntity.ok(member);
    }

    @PostMapping("/members")
    public Member add(@RequestBody Member member) {
        Member savedMember = memberRepository.save(member);
        return savedMember;
    }

    @PutMapping("/members/{memberid}")
    public ResponseEntity<Member> update(@PathVariable(value = "memberid") Long memberid, @RequestBody Member member) {
        member.setId(memberid);
        Member updatedMember = memberRepository.save(member);
        return ResponseEntity.ok(updatedMember);
    }

    @PutMapping("/members/{memberid}/active")
    public ResponseEntity<Member> activateMember(@PathVariable(value = "memberid") Long memberid) {
        Optional<Member> optionalMember = memberRepository.findById(memberid);
        if (optionalMember.isPresent()) {
            Member member = optionalMember.get();
            if (member.isActive()) {
                return ResponseEntity.notFound().build(); // POSSIBLE BETTER ERROR HANDLING, SINCE IT IS ACTUALLY FOUND, JUST NOT AVAILABLE
            }
            member.setActive(true);
            Member updatedMember = memberRepository.save(member);
            return ResponseEntity.ok(updatedMember);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/members/{memberid}/inactive")
    public ResponseEntity<Member> deactivateMember (@PathVariable(value = "memberid") Long memberid){
        Optional<Member> optionalMember = memberRepository.findById(memberid);
        if (optionalMember.isPresent()) {
            Member member = optionalMember.get();
            if (member.isActive()) {
                member.setActive(false);
                Member updatedMember = memberRepository.save(member);
                return ResponseEntity.ok(updatedMember);
            }
            else{
                return ResponseEntity.notFound().build(); // POSSIBLE BETTER ERROR HANDLING, SINCE IT IS ACTUALLY FOUND, JUST NOT BORROWED YET
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

