package simple.guestbook.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;
import simple.guestbook.domain.Book;
import simple.guestbook.domain.BookSearch;

import javax.persistence.EntityManager;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class BookRepository {

    // JPA EntityMananger
    private final EntityManager em;


    /**
     * 방명록 저장
     */
    public Long save(Book book) {
        em.persist(book);
        return book.getId();
    }

    /**
     * bookId 기반 탐색
     * Service나 Tag관련해서 필요할 듯
     */
    public Book findOne(Long bookId) {
        return em.find(Book.class, bookId);
    }

    /**
     * 전체 탐색, 작성자 탐색, 내용 탐색
     */
    public List<Book> findAll(BookSearch bookSearch) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Book> cq = cb.createQuery(Book.class);
        Root<Book> b = cq.from(Book.class);

        /*
        탐색 조건 : 삭제되지 않은(is not null deletedate) AND ( writerNick OR content )
         */
        List<Predicate> criteria = new ArrayList<>();

        if (!StringUtils.isEmpty(bookSearch.getSearchType()) && StringUtils.hasText(bookSearch.getSearchText())) {
            // WHERE절에 LIKE
            Predicate conditionNick = cb.like(b.get("writerNick"), "%" + bookSearch.getSearchText() + "%");
            Predicate coditionContent = cb.like(b.get("content"), "%" + bookSearch.getSearchText() + "%");

            /*
                ALL("전체"),
                WRITER("작성자"),
                CONTENT("내용");
             */
            switch (bookSearch.getSearchType()) {
                case ALL:
                    criteria.add(conditionNick);
                    criteria.add(coditionContent);
                    break;
                case WRITER:
                    criteria.add(conditionNick);
                    break;
                case CONTENT:
                    criteria.add(coditionContent);
                    break;
            }
        }

        // 삭제되지 않은 방명록만 조회(using deleteDate)
        Predicate isNotDelete = cb.isNull(b.get("deleteDate"));

        // 검색 조건 조합 :
        if (criteria.size() == 0) {
            cq.where(isNotDelete);
        } else {
            cq.where(cb.and(isNotDelete, cb.or(criteria.toArray(new Predicate[criteria.size()]))));
        }

        // 정렬 : 최신순으로 내림차순
        List<Order> orderList = new ArrayList<>();

        orderList.add(cb.desc(b.get("modifyDate")));
        orderList.add(cb.desc(b.get("writeDate")));

        cq.orderBy(orderList);

        return em.createQuery(cq).setMaxResults(100).getResultList();
    }
}
