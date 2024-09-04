package hello.servlet.domain.member;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
* 동시성 문제가 고려되어 있지 않음, 실무에서는 ConcurrentHashMap, AtomicLong 사용 고려
*
* static : 각 객체들에서 공통적으로 하나의 값이 유지되어야 할 경우 사용. 메모리에 딱 한번만 할당
* final : 오직 한 번만 할당할 수 있는 엔티티를 정의할 때 사용. 한번 값 할당하면 수정 불가능
*
* cmd + shift + T : 테스트 클래스 생성
* */
public class MemberRepository {

    private static Map<Long, Member> store = new HashMap<>(); // static 사용
    private static long sequence = 0L; //static 사용

    private static final MemberRepository instance = new MemberRepository(); //싱글톤으로 관리되어야 하기 때문에 static final 쓴 듯

    public static MemberRepository getInstance() {
        return instance;
    }

    private MemberRepository(){

    }

    public Member save(Member member){
        member.setId(++sequence);
        store.put(member.getId(), member);
        return member;
    }

    public Member findById(Long id){
        return store.get(id);
    }

    public List<Member> findAll() {
        return new ArrayList<>(store.values()); //store원본값을 가지고 수정할 수도 있기 때문에 아예 새로운 객체로 반환함.
    }

    public void clearStore(){
        store.clear();
    }

}
