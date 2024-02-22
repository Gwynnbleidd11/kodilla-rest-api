package com.crud.tasks.trello.mapper;

import com.crud.tasks.domain.*;
import com.crud.tasks.mapper.TrelloMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class TrelloMapperTestSuite {

    @Autowired
    private TrelloMapper trelloMapper;

    @Test
    void testMapToBoard() {
        //Given
        List<TrelloListDto> trelloListDto = List.of(new TrelloListDto("1", "test_list", false));
        List<TrelloBoardDto> trelloBoardDto = List.of(new TrelloBoardDto("1", "test_board", trelloListDto));

        //When
        List<TrelloBoard> mappedBoard = trelloMapper.mapToBoards(trelloBoardDto);

        //Then
        assertEquals(1, mappedBoard.size());
        assertEquals("1", mappedBoard.get(0).getId());
        assertEquals("test_list", mappedBoard.get(0).getLists().get(0).getName());
        assertFalse(mappedBoard.get(0).getLists().get(0).isClosed());
    }

    @Test
    void testMapToBoardDto() {
        //Given
        List<TrelloList> trelloList = List.of(new TrelloList("1", "test_list", false));
        List<TrelloBoard> trelloBoard = List.of(new TrelloBoard("1", "test_board", trelloList));

        //When
        List<TrelloBoardDto> mappedBoard = trelloMapper.mapToBoardsDto(trelloBoard);

        //Then
        assertEquals(1, mappedBoard.size());
        assertEquals("1", mappedBoard.get(0).getId());
        assertEquals("test_list", mappedBoard.get(0).getLists().get(0).getName());
        assertFalse(mappedBoard.get(0).getLists().get(0).isClosed());
    }

    @Test
    void testMapToCard() {
        //Given
        TrelloCardDto trelloCardDto = new TrelloCardDto("test_name", "test_description",
                "1", "1");

        //When
        TrelloCard mappedCard = trelloMapper.mapToCard(trelloCardDto);

        //Then
        assertEquals("test_name", mappedCard.getName());
        assertEquals("test_description", mappedCard.getDescription());
        assertEquals("1", mappedCard.getPos());
        assertEquals("1", mappedCard.getListId());
    }

    @Test
    void testMapToCardDto() {
        //Given
        TrelloCard trelloCard = new TrelloCard("test_name", "test_description",
                "1", "1");

        //When
        TrelloCardDto mappedCard = trelloMapper.mapToCardDto(trelloCard);

        //Then
        assertEquals("test_name", mappedCard.getName());
        assertEquals("test_description", mappedCard.getDescription());
        assertEquals("1", mappedCard.getPos());
        assertEquals("1", mappedCard.getListId());
    }
}
