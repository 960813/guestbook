package simple.guestbook.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import simple.guestbook.domain.Book;
import simple.guestbook.service.BookService;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @PostMapping("/book/new")
    public String writeNewBook(@Valid BookForm bookForm, BindingResult result) {
        if (result.hasErrors()) {
            return "/index";
        }

        String newBookContent = bookForm.getContent();
        String newBookNick = bookForm.getWriterNick();
        String newBookPassword = bookForm.getWriterPassword();

        Book book = Book.create(newBookContent.replace("\r\n", "<br/>"), newBookNick, newBookPassword);
        bookService.writeBook(book);

        return "redirect:/";
    }

    @GetMapping("/book/{bookId}/modify")
    public String modifyExistsBookForm(@PathVariable("bookId") Long bookId, Model model) {
        Book findBook = bookService.findBook(bookId);
        model.addAttribute("bookUpdateForm", new BookUpdateForm(bookId, findBook.getContent(), findBook.getWriterNick(), ""));
        return "/modifyForm";
    }

    @PostMapping("/book/modify")
    public String modifyExistsBook(BookUpdateForm bookUpdateForm) {
        Long bookId = bookUpdateForm.getBookId();
        if (bookService.modifyBook(bookId, bookUpdateForm.getContent(), bookUpdateForm.getWriterPassword())) {
            return "redirect:/";
        }
        return "redirect:/book/" + bookId + "/modify";
    }

    @GetMapping("/book/{bookId}/delete")
    public String deleteExistsBookForm(@PathVariable("bookId") Long bookId, Model model) {
        Book findBook = bookService.findBook(bookId);
        model.addAttribute("bookUpdateForm", new BookUpdateForm(bookId, findBook.getContent(), findBook.getWriterNick(), ""));
        return "/deleteForm";
    }

    @PostMapping("/book/delete")
    public String deleteExistsBook(BookUpdateForm bookUpdateForm) {
        Long bookId = bookUpdateForm.getBookId();
        if (bookService.deleteBook(bookId, bookUpdateForm.getWriterPassword())) {
            return "redirect:/";
        }
        return "redirect:/book/" + bookId + "/delete";
    }

}
