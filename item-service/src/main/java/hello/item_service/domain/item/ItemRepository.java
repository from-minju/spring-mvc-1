package hello.item_service.domain.item;

import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository //이 안에 @Component있음 -> 스프링사용 / 컴포넌트스캔의 대상이 됨.
public class ItemRepository {

    private static final Map<Long, Item> store = new HashMap<>(); //static
    private static long sequence = 0L; //static

    public Item save(Item item){
        item.setId(++sequence);
        store.put(item.getId(), item);
        return item;
    }

     
}
