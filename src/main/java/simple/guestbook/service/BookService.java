package simple.guestbook.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import simple.guestbook.domain.Book;
import simple.guestbook.domain.BookSearch;
import simple.guestbook.repository.BookRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;

    /**
     * 방명록 작성
     */
    @Transactional
    public Long writeBook(Book book) {
        return bookRepository.save(book);
    }

    /**
     * 방명록 수정
     * Book객체를 받는건 불가능(HTML에서 Form 태그로 값 전달)
     * Dto 생성 필요
     */
    @Transactional
    public boolean modifyBook(Long bookId, String content, String password) {
        Book book = bookRepository.findOne(bookId);
        if(verifyBookPassword(book, password)) {
            book.modify(content);
            return true;
        }
        return false;
    }

    /**
     * 방명록 삭제
     */
    @Transactional
    public boolean deleteBook(Long bookId, String password) {
        Book book = bookRepository.findOne(bookId);
        if(verifyBookPassword(book, password)) {
            book.delete();
            return true;
        }
        return false;
    }

    /**
     * 방명록 단건 조회
     */
    public Book findBook(Long bookId) {
        return bookRepository.findOne(bookId);
    }

    /**
     * 방명록 목록 조회(100건)
     */
    public List<Book> findBooks(BookSearch bookSearch) {
        return bookRepository.findAll(bookSearch);
    }


    // Boolean 래핑 타입에서 boolean 기본 타입으로 변경
    private boolean verifyBookPassword(Book book, String password) {
        return book.getWriterPassword().equals(password);
    }
}
