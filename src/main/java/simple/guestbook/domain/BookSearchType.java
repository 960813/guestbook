package simple.guestbook.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum BookSearchType {
    // 전체 탐색, 작성자 탐색, 내용 탐색
    ALL("전체"),
    WRITER("작성자"),
    CONTENT("내용");

    private final String displayName;

//    BookSearchType(String displayName) {
//        this.displayName = displayName;
//    }
}
