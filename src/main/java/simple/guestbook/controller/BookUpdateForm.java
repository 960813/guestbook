package simple.guestbook.controller;


import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BookUpdateForm extends BookForm {
    private Long bookId;

    public BookUpdateForm(Long bookId, String content, String writerNick, String writerPassword) {
        super(content, writerNick, writerPassword);
        this.bookId = bookId;
    }
}
