package com.microservice.library.controller;

import com.microservice.library.dto.*;
import com.microservice.library.service.BookService;
import com.microservice.library.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/library")
public class CoreController {

    @Autowired
    private MemberService memberService;

    @Autowired
    private BookService bookService;

    @PostMapping("/newmember")
    @ResponseStatus(HttpStatus.CREATED)
    public NewMemberResponse newMember(@RequestBody NewMemberRequest request) {
        return memberService.addNewMember(request);
    }

    @GetMapping("/allmembers")
    @ResponseStatus(HttpStatus.OK)
    public List<MemberQueryResponse> allMembers() {
        return memberService.getAllMembers();
    }

    @GetMapping("/memberbyid")
    @ResponseStatus(HttpStatus.OK)
    public MemberQueryResponse getMemberById(@PathVariable(name = "id") int id) {
        return memberService.getMemberById(id);
    }

    @PostMapping("/newbook")
    @ResponseStatus(HttpStatus.CREATED)
    public NewBookResponse newBook(@RequestBody NewBookRequest request) {
        return bookService.addNewBook(request);
    }

    @GetMapping("/allbooks")
    @ResponseStatus(HttpStatus.OK)
    public List<BookQueryResponse> allBooks() {
        return bookService.getAllBooks();
    }

    @GetMapping("/bookbyid")
    @ResponseStatus(HttpStatus.OK)
    public BookQueryResponse getBookById(@PathVariable(name = "id") int id) {
        return bookService.getBookById(id);
    }
}
