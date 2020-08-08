package simple.guestbook.controller;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

// 생성자를 써서 초기화 해야함.
// Bq. Thymeleaf에서 Controller로 넘겨줄때 생성자 혹은 Setter로 값을 지정
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class BookForm {

    @NotEmpty(message = "내용은 필수입니다.")
    private String content;

    @NotEmpty(message = "닉네임은 필수입니다.")
    private String writerNick;

    @NotEmpty(message = "암호는 필수입니다.")
    private String writerPassword;

//    public BookForm(String content, String writerNick, String writerPassword) {
//        this.content = content;
//        this.writerNick = writerNick;
//        this.writerPassword = writerPassword;
//    }
}
