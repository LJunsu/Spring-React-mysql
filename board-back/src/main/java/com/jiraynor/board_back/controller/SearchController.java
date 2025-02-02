package com.jiraynor.board_back.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jiraynor.board_back.dto.response.search.GetPopularListResponseDto;
import com.jiraynor.board_back.dto.response.search.GetRelationListResponseDto;
import com.jiraynor.board_back.service.SearchService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping(value = "/api/v1/search")
@RequiredArgsConstructor
public class SearchController {
    private final SearchService SearchService;

    @GetMapping("/popular-list")
    public ResponseEntity<? super GetPopularListResponseDto> getPopularList() {
        ResponseEntity<? super GetPopularListResponseDto> response = SearchService.getPopularList();
        return response;
    }
    
    @GetMapping("/{searchWord}/relation-list")
    public ResponseEntity<? super GetRelationListResponseDto> getRelationList(
        @PathVariable("searchWord") String searchWord
    ) {
        ResponseEntity<? super GetRelationListResponseDto> response = SearchService.getRelationList(searchWord);
        return response;
    }
}
