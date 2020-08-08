package simple.guestbook.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import simple.guestbook.domain.Book;
import simple.guestbook.domain.BookSearch;
import simple.guestbook.domain.BookSearchType;
import simple.guestbook.service.BookService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class IndexController {

    private final BookService bookService;

    @RequestMapping("/")
    public String index(@ModelAttribute("bookSearch") BookSearch bookSearch, Model model) {
        List<Book> findBooks = bookService.findBooks(bookSearch);
        model.addAttribute("bookForm", new BookForm());
        model.addAttribute("books", findBooks);

        return "/index";
    }
}
