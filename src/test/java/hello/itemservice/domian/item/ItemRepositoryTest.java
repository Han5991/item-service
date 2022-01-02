package hello.itemservice.domian.item;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ItemRepositoryTest {
    ItemRepository itemRepository = new ItemRepository();

    @AfterEach
    void AfterEach() {
        itemRepository.clearStore();
    }

    @Test
    void save() {
        //given
        Item item = new Item("itemA", 10000, 10);

        //where

        Item savedItem = itemRepository.save(item);

        //then
        Item findItem = itemRepository.findById(item.getId());
        assertThat(findItem).isEqualTo(savedItem);
    }

    @Test
    void findAll() {
        //given
        Item item1 = new Item("item1", 10000, 10);
        Item item2 = new Item("item2", 20000, 20);

        itemRepository.save(item1);
        itemRepository.save(item2);

        //where
        List<Item> itemList = itemRepository.findAll();

        //then
        assertThat(itemList.size()).isEqualTo(2);
        assertThat(itemList).contains(item1, item2);
    }

    @Test
    void update() {
        //given
        Item item = new Item("item", 10000, 10);

        Item save = itemRepository.save(item);
        Long itemId = save.getId();

        //where
        Item itemParam = new Item("item2", 20000, 30);
        itemRepository.update(itemId, itemParam);

        //then
        Item item1 = itemRepository.findById(itemId);
        assertThat(item1.getItemName()).isEqualTo(itemParam.getItemName());
        assertThat(item1.getPrice()).isEqualTo(itemParam.getPrice());
        assertThat(item1.getQuantity()).isEqualTo(itemParam.getQuantity());
    }
}