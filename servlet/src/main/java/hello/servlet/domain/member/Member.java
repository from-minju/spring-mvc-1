package hello.servlet.domain.member;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter //Lombok이 제공하는 기능
public class Member {

    private Long id;
    private String username;
    private int age;

    public Member(){

    }

    public Member(String username, int age){
        this.username = username;
        this.age = age;
    }
}
