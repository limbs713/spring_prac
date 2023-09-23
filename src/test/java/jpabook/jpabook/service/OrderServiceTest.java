package jpabook.jpabook.service;

import jakarta.persistence.EntityManager;
import jpabook.jpabook.domain.Address;
import jpabook.jpabook.domain.Member;
import jpabook.jpabook.domain.Order;
import jpabook.jpabook.domain.OrderStatus;
import jpabook.jpabook.domain.item.Book;
import jpabook.jpabook.domain.item.Item;
import jpabook.jpabook.repository.Orderrepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
class OrderServiceTest {

    @Autowired
    EntityManager em;
    @Autowired
    OrderService orderService;
    @Autowired
    Orderrepository orderrepository;
    @Test
    public void 상품주문(String author, int price, int stockQuantity) throws Exception{
        Member member = createMember();
        Item item = createBook("시골 JPA", 10000, 10);
        int ordercount = 2;

        Long orderId = orderService.order(member.getId(), item.getId(), ordercount);
        Order getOrder = orderrepository.findone(orderId);

        Assertions.assertEquals( OrderStatus.ORDER, getOrder.getStatus(),"상품 주문시 상태는 ORDER");
        assertEquals(getOrder.getOrderItems().size(),1,"주문한 상품 종류가 정확해야 한다");
        assertEquals(10000*ordercount,getOrder.getTotalPrice(),"주문 가격은 수량 * 가격이다");
        assertEquals(8,item.getStockQuantity(),"주문 수량만큼 재고가 줄어야 한다.");

    }

    @Test
    public void 주문취소() throws Exception{

    }

    /**
     * 미완성
     * @throws Exception
     */
    @Test
    public void 상품주문_재고수량초과() throws Exception{
        Member member = new Member();
        Book book = new Book();
    }
    private Member createMember() {
        Member member = new Member();
        member.setName("회원1");
        member.setAddress(new Address("서울", "강가", "123-123"));
        em.persist(member);
        return member;
    }
    private Book createBook(String name, int price, int stockQuantity) {
        Book book = new Book();
        book.setName(name);
        book.setStockQuantity(stockQuantity);
        book.setPrice(price);
        em.persist(book);
        return book;
    }
}