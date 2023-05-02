package com.microservice.library.service;

import com.microservice.library.dto.MemberQueryResponse;
import com.microservice.library.dto.NewMemberRequest;
import com.microservice.library.dto.NewMemberResponse;
import com.microservice.library.model.Member;
import com.microservice.library.repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class MemberService {

    @Autowired
    private MemberRepository memberRepository;

    public NewMemberResponse addNewMember(NewMemberRequest request){
        log.info("creating new member");
        NewMemberResponse response = new NewMemberResponse();

        Member member = Member.builder()
                .level(request.getLevel())
                .registrationId(request.getRegistrationId())
                .build();

        memberRepository.save(member);
        response.setMessage("new library membership created successfully");
        log.info("new member created successfully");

        return response;
    }

    public List<MemberQueryResponse> getAllMembers() {
        List<Member> members = memberRepository.findAll();

        return members.stream().map(this::mapToMemberResponse).collect(Collectors.toList());
    }

    public MemberQueryResponse getMemberById(int id) {

        return mapToMemberResponse(memberRepository.findById(id).get());
    }

    private MemberQueryResponse mapToMemberResponse(Member member) {
        return MemberQueryResponse.builder()
                .id(member.getId())
                .registrationId(member.getRegistrationId())
                .level(member.getLevel())
                .build();
    }
}
