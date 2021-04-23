package org.zerock.club.service;

import java.util.List;

import org.zerock.club.dto.NoteDTO;
import org.zerock.club.entity.ClubMember;
import org.zerock.club.entity.Note;

public interface NoteService {
    
    List<NoteDTO> getAllWithWriter(String writerEmail);
    
    NoteDTO get(Long num);

    Long register(NoteDTO noteDTO);

    void modify(NoteDTO noteDTO);

    void remove(Long num);


    default Note dtoToEntity(NoteDTO noteDTO) {
        Note note = Note.builder()
                .num(noteDTO.getNum())
                .title(noteDTO.getTitle())
                .content(noteDTO.getContent())
                .writer(ClubMember.builder().email(noteDTO.getWriterEmail()).build())
                .build();

        return note;
    }

    default NoteDTO entityToDTO (Note note) {
        NoteDTO noteDTO = NoteDTO.builder()
                .num(note.getNum())
                .title(note.getTitle())
                .content(note.getContent())
                .writerEmail(note.getWriter().getEmail())
                .regDate(note.getRegDate())
                .modDate(note.getModDate())
                .build();
        
        return noteDTO;
    }
}
