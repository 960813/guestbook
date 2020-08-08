package simple.guestbook.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
//@RequiredArgsConstructor(staticName = )
@AllArgsConstructor
public class BookSearch {

    private BookSearchType searchType;
    private String searchText;
}
