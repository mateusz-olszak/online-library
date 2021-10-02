package com.library.controllers;

import com.library.domain.Copy;
import com.library.dto.CopyDto;
import com.library.exceptions.ElementNotFoundException;
import com.library.mappers.CopyMapper;
import com.library.service.CopyService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
public class CopyController {

    private CopyService copyService;
    private CopyMapper copyMapper;

    @GetMapping("/copies")
    List<CopyDto> getAllCopies(){
        List<Copy> copies = copyService.findAllCopies();
        return copyMapper.mapToListCopyDto(copies);
    }

    @GetMapping(value = "/copies", params = "title")
    List<CopyDto> getAllCopiesWithGivenTitle(@RequestParam("title") String title){
        List<Copy> copies = copyService.retrieveAvailableCopiesForGivenTitle(title);
        return copyMapper.mapToListCopyDto(copies);
    }

    @GetMapping("copy")
    CopyDto getCopy(@RequestParam("id") int id) throws ElementNotFoundException {
        Copy copy = copyService.findCopy(id);
        return copyMapper.maptoCopyDto(copy);
    }

    @PostMapping("/copies")
    void saveCopy(@RequestBody CopyDto copyDto) throws ElementNotFoundException {
        Copy copy = copyMapper.mapToCopy(copyDto);
        copyService.saveCopy(copy);
    }

    @DeleteMapping("/copies/delete/{id}")
    void deleteCopy(@PathVariable int id){
        copyService.deleteCopy(id);
    }

    @PatchMapping("/copy/status/{id}")
    void changeCopyStatus(@PathVariable int id, @RequestBody CopyDto copyDto) throws ElementNotFoundException{
        Copy copy = copyService.findCopy(id);
        copy.setStatus(copyDto.getStatus());
        copyService.saveCopy(copy);
    }

}