package hello.item_service.domain.web.basic;

import hello.item_service.domain.item.Item;
import hello.item_service.domain.item.ItemRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

    @GetMapping("/{itemId}")
    public String item(@PathVariable Long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "basic/item";
    }

    @GetMapping("/add")
    public String addForm() {
        return "basic/addForm";
    }


//    @PostMapping("/add")
    public String addItemV1 (@RequestParam String itemName,
                       @RequestParam int price,
                       @RequestParam Integer quantity,
                       Model model) {

        Item item = new Item();
        item.setItemName(itemName);
        item.setPrice(price);
        item.setQuantity(quantity);

        itemRepository.save(item);

        //저장된 `item` 을 모델에 담아서 뷰에 전달
        model.addAttribute("item", item);

        return "basic/item";
    }

//    @PostMapping("/add")
    public String addItemV2 (@ModelAttribute("item") Item item, Model model) {
        // 컨트롤러는 사용자 요청을 처리한 후, 뷰에 필요한 데이터를 Model객체에 담아 뷰로 전달한다.

        itemRepository.save(item);
//        model.addAttribute("item", item); //자동 추가, 생략 가능

        return "basic/item";
    }

    /**
     * @ModelAttribute name 생략 가능
     * model.addAttribute(item); 자동 추가, 생략 가능
     * 생략시 model에 저장되는 name은 클래스명 첫글자만 소문자로 등록 Item -> item */
//    @PostMapping("/add")
    public String addItemV3(@ModelAttribute Item item) {
        itemRepository.save(item);
        return "basic/item";
    }

    /**
     * @ModelAttribute 자체 생략 가능
     * model.addAttribute(item) 자동 추가 */
//    @PostMapping("/add")
    public String addItemV4(Item item) {
        itemRepository.save(item);
        return "basic/item";
    }

    /**
     *  PRG - Post/Redirect/Get
     */
//    @PostMapping("/add")
    public String addItemV5(Item item) {
        itemRepository.save(item);
        return "redirect:/basic/items/" + item.getId();
    }

    /**
     * RedirectAttributes
     */
    @PostMapping("/add")
    public String addItemV6(Item item, RedirectAttributes redirectAttributes) {
        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/basic/items/{itemId}";
    }


    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable Long itemId, Model model) { //@PathVariable : 경로에 포함된 값을 변수로 받아오는 데 사용하는 어노테이션
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "basic/editForm";
    }


    @PostMapping("/{itemId}/edit")
    public String edit(@PathVariable Long itemId, @ModelAttribute Item item) {
        itemRepository.update(itemId, item);
        return "redirect:/basic/items/{itemId}";
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
