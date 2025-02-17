package com.libraryquerypie.onlinelibrarysystem.borrow;

import com.libraryquerypie.onlinelibrarysystem.borrow.dto.BookBorrowRequest;
import com.libraryquerypie.onlinelibrarysystem.entity.Borrow;
import com.libraryquerypie.onlinelibrarysystem.jwt.JwtUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest
@Transactional(readOnly = true)
@ActiveProfiles("test")
public class BorrowControllerTest {
    @Mock
    private BorrowService borrowService;

    @Mock
    private JwtUtils jwtUtils;

    @InjectMocks
    private BorrowController borrowController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("도서 대출을 시도한다.")
    void testRegisterBorrowSuccess() {
        String token = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJlbWFpbElkIjoiZWVlQGdtYWlsLmNvbSIsImlhdCI6MTczOTYzMzUxMywiZXhwIjoxNzM5NzE5OTEzfQ.PVkQ-qZk43EElq-3dJ99Ur-zOMy5ujeTkAiYulIBnag";
        String jwt = token.substring(7);
        String emailId = "test@example.com";
        BookBorrowRequest request = new BookBorrowRequest();
        Long borrowId = 1L;

        when(jwtUtils.extractEmailId(jwt)).thenReturn(emailId);
        when(borrowService.registerBorrow(anyString(), any(BookBorrowRequest.class))).thenReturn(1L);

        ResponseEntity<String> response = borrowController.registerBorrow(token, request);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("도서 대출 성공", response.getBody());
    }

}
