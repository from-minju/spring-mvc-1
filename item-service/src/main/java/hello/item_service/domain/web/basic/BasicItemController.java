package hello.item_service.domain.web.basic;

import hello.item_service.domain.item.Item;
import hello.item_service.domain.item.ItemRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/basic/items")
@RequiredArgsConstructor //Lombok 기능임. final 붙은 변수들을 가지고 자동으로 생성자 만들어 준다.
public class BasicItemController {

    private final ItemRepository itemRepository; //ItemRepository의존관계 주입을 위해 final키워드를 빼면 안됨!!!

/*  - 이 코드를 @RequiredArgsContructor가 대체함.
    - ItemRepository클래스에 붙은 @Repository에 @Component가 들어있어서 ItemRepository는 자동으로 컴포넌트스캔의 대상이됨. -> 스프링에 등록됨.
    - @Autowired로 생성자의 변수 itemRepository에 ItemRepository가(스프링빈에 등록되어있던) 주입됨.

    @Autowired //생략가능. 스프링에 생성자가 딱 하나만 있는 경우 생략 가능함.
    public BasicItemController(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }
 */

    @GetMapping
    public String items(Model model) {
        List<Item>items = itemRepository.findAll();
        model.addAttribute("items", items);
        return "basic/items";
    }

    /**
     * 테스트용 데이터 추가
     */
    @PostConstruct
    public void init() {
        itemRepository.save(new Item("testA", 10000, 10));
        itemRepository.save(new Item("testB", 20000, 20));

    }

}
