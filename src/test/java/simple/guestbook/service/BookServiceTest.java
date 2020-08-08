package simple.guestbook.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import simple.guestbook.domain.Book;
import simple.guestbook.domain.BookSearch;
import simple.guestbook.domain.BookSearchType;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class BookServiceTest {
    @Autowired
    private BookService bookService;

    /**
     * 검증 방법: book 객체 생성 후 등록 -> 단건 조회 후 비교 + 전체 조회 후 비교
     */
    @Test
    public void 방명록_등록() throws Exception {
        // given
        Book book = Book.create("내용", "작성자", "암호");

        // when
        bookService.writeBook(book);

        // then
        // 단건 조회 PK가 1인지 검증
        Assertions.assertThat(bookService.findBook(book.getId())).isEqualTo(book);

        // 전체 조회 목록이 1개인지 검증
        BookSearch bookSearch = BookSearch.create(BookSearchType.ALL, "");
        Assertions.assertThat(bookService.findBooks(bookSearch).size()).isEqualTo(1);
    }

    /**
     * 검증 방법: 방명록 작성 -> 수정 -> 수정된 내용과 일치하는지 확인
     */
    @Test
    public void 방명록_수정() throws Exception{
        // given
        Book book = Book.create("내용", "작성자", "암호");
        bookService.writeBook(book);
        
        // when
        bookService.modifyBook(book.getId(), "가나다라마바사", "암호");
        
        // then
        Assertions.assertThat(bookService.findBook(book.getId()).getContent()).isEqualTo("가나다라마바사");
    }

    /**
     * 검증 방법: 방명록 작성 -> 삭제 -> deleteDate 세팅 여부 확인 -> 전체 목록 비교
     */
    @Test
    public void 방명록_삭제() throws Exception{
        // given
        Book book = Book.create("내용", "작성자", "암호");
        bookService.writeBook(book);
        
        // when
        bookService.deleteBook(book.getId(), "암호");

        // then
        // deleteDate 세팅 여부 확인
        Assertions.assertThat(bookService.findBook(book.getId()).getDeleteDate()).isNotNull();


        // 전체 목록 비교
        BookSearch bookSearch = BookSearch.create(BookSearchType.ALL, "");
        Assertions.assertThat(bookService.findBooks(bookSearch).size()).isEqualTo(0);
    }

    /**
     * 검증 방법: 방명록 작성 -> id기반 조회 -> Null 여부 판단
     */
    @Test
    public void 방명록_단건_조회() throws Exception{
        // given
        Book book = Book.create("내용", "작성자", "암호");
        bookService.writeBook(book);

        // when
        Book findBook = bookService.findBook(book.getId());

        // then
        Assertions.assertThat(findBook).isNotNull();
    }

    /**
     * 검증 방법: 방명록 작성(서로 다른 내용 방명록 2개) -> 검색 결과 개수 비교(ALL 3번, WRITER 2번, CONTENT 2번)
     */
    @Test
    public void 방명록_통합_조회() throws Exception{
        // given
        Book book1 = Book.create("내용", "작성자", "암호");
        bookService.writeBook(book1);
        Book book2 = Book.create("방명록 내용", "방명록 작성자", "암호");
        bookService.writeBook(book2);
        
        // when
        BookSearch bookSearch1 = BookSearch.create(BookSearchType.ALL, "");
        BookSearch bookSearch2 = BookSearch.create(BookSearchType.ALL, "내용");
        BookSearch bookSearch3 = BookSearch.create(BookSearchType.ALL, "작성자");

        BookSearch bookSearch4 = BookSearch.create(BookSearchType.WRITER, "작성자");
        BookSearch bookSearch5 = BookSearch.create(BookSearchType.WRITER, "방명록");

        BookSearch bookSearch6 = BookSearch.create(BookSearchType.CONTENT, "내용");
        BookSearch bookSearch7 = BookSearch.create(BookSearchType.CONTENT, "방명록");

        // then
        Assertions.assertThat(bookService.findBooks(bookSearch1).size()).isEqualTo(2);
        Assertions.assertThat(bookService.findBooks(bookSearch2).size()).isEqualTo(2);
        Assertions.assertThat(bookService.findBooks(bookSearch3).size()).isEqualTo(2);

        Assertions.assertThat(bookService.findBooks(bookSearch4).size()).isEqualTo(2);
        Assertions.assertThat(bookService.findBooks(bookSearch5).size()).isEqualTo(1);

        Assertions.assertThat(bookService.findBooks(bookSearch6).size()).isEqualTo(2);
        Assertions.assertThat(bookService.findBooks(bookSearch7).size()).isEqualTo(1);
    }
}