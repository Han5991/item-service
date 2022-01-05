package hello.itemservice.web.basic;

import hello.itemservice.domian.item.Item;
import hello.itemservice.domian.item.ItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.PostConstruct;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/basic/items")
@RequiredArgsConstructor
public class BasicItemController {
    private final ItemRepository itemRepository;

    @GetMapping
    public String items(Model model) {
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);
        return "basic/items";
    }

    /**
     * 테스트용 데이터 추가
     */
    @PostConstruct
    public void init() {
        itemRepository.save(new Item("itemA", 10000, 10));
        itemRepository.save(new Item("itemB", 20000, 20));
    }

    @GetMapping("/{itemId}")
    public String item(@PathVariable long itemId, Model model){
        Item byId = itemRepository.findById(itemId);
        model.addAttribute("item", byId);
        return "basic/item";
    }

    @GetMapping("/add")
    public String addForm(){
        return "basic/addForm";
    }

    //@PostMapping("/add")
    public String save(@RequestParam String itemName,
                       @RequestParam int price,
                       @RequestParam Integer quantity,
                       Model model){
        Item item = new Item(itemName, price, quantity);
        itemRepository.save(item);
        model.addAttribute("item", item);
        return "basic/item";
    }

//    @PostMapping("/add")
    public String save(@ModelAttribute("item") Item item){
        itemRepository.save(item);
        //model.addAttribute("item", item); //자동 추가
        return "basic/item";
    }

    //@PostMapping("/add")
    public String save2(@ModelAttribute Item item){
        Item newItem = itemRepository.save(item);
        Long itemId = newItem.getId();
        //model.addAttribute("item", item); //자동 추가 지우면 클래스명을 앞에만 소문자로 바꿔서 등록
        return "redirect:/basic/items/"+itemId;
    }

    //@PostMapping("/add")
    public String save3(Item item){
        itemRepository.save(item);
        return "redirect:/basic/items/"+item.getId();
    }

    @PostMapping("/add")
    public String save4(Item item, RedirectAttributes redirectAttributes){
        Item save = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", save.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/basic/items/{itemId}";
    }

    @GetMapping("{itemId}/edit")
    public String editForm(@PathVariable Long itemId, Model model){
        Item id = itemRepository.findById(itemId);
        model.addAttribute("item", id);
        return "basic/editForm";
    }

    @PostMapping ("{itemId}/edit")
    public String edit(@PathVariable Long itemId, @ModelAttribute("item") Item item){
        itemRepository.update(itemId, item);
        return "redirect:/basic/items/{itemId}";
    }

    @GetMapping ("{itemId}/delete")
    public String edit(@PathVariable Long itemId, Model model){
        itemRepository.delete(itemId);
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);
        return "basic/items";
    }
}
