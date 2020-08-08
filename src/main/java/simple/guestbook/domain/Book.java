package simple.guestbook.domain;

import com.sun.istack.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
//@RequiredArgsConstructor(s)
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id")
    private Long id;

    @Column(length = 255)
    @NotNull
    private String content;

    @Column(length = 32)
    @NotNull
    private String writerNick;

    @Column(length = 32)
    @NotNull
    private String writerPassword;

    @NotNull
    private LocalDateTime writeDate;

    private LocalDateTime modifyDate;

    private LocalDateTime deleteDate;

    // 태그 관련 매핑


    /*
    생성, 수정, 삭제 관련 BL
     */

    /**
     * 방명록 생성
     */
    public static Book create(String content, String writerNick, String writerPassword) {
        Book book = new Book();
        book.content = content;
        book.writerNick = writerNick;
        book.writerPassword = writerPassword;
        book.writeDate = LocalDateTime.now();

        return book;
    }

    /**
     * 방명록 수정
     */
    public void modify(String content) {
        this.content = content;
        this.modifyDate = LocalDateTime.now();
    }

    /**
     * 방명록 삭제
     */
    public void delete() {
        this.deleteDate = LocalDateTime.now();
    }
}
