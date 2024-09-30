package hello.springmvc.basic;

import lombok.Data;

@Data //롬복Data : Getter, Setter, ToString...를 자동으로 적용해줌.
public class HelloData {
    private String username;
    private int age;
}
