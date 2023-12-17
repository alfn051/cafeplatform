package com.teamseven.cafeplatform.domain.order.service;

import com.teamseven.cafeplatform.domain.cafe.entity.Cafe;
import com.teamseven.cafeplatform.domain.cafe.entity.Menu;
import com.teamseven.cafeplatform.domain.cafe.repository.CafeRepository;
import com.teamseven.cafeplatform.domain.cafe.service.CafeService;
import com.teamseven.cafeplatform.domain.cafe.service.MenuService;
import com.teamseven.cafeplatform.domain.order.common.AddCartDTO;
import com.teamseven.cafeplatform.domain.order.common.OrderState;
import com.teamseven.cafeplatform.domain.order.entity.Order;
import com.teamseven.cafeplatform.domain.order.entity.OrderMenu;
import com.teamseven.cafeplatform.domain.order.repository.OrderMenuRepository;
import com.teamseven.cafeplatform.domain.order.repository.OrderRepository;
import com.teamseven.cafeplatform.domain.stamp.entity.StampGift;
import com.teamseven.cafeplatform.domain.stamp.service.StampService;
import com.teamseven.cafeplatform.domain.user.entity.User;
import com.teamseven.cafeplatform.domain.user.repository.UserRepository;
import com.teamseven.cafeplatform.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderMenuRepository orderMenuRepository;
    private final MenuService menuService;
    private final StampService stampService;
    private final UserRepository userRepository;
    private final CafeRepository cafeRepository;
    private final CafeService cafeService;

    @Transactional
    public Order createPreparingOrder(User user, Cafe cafe) {
        return orderRepository.save(Order.builder()
                .orderState(OrderState.PREPARING)
                .user(user)
                .cafe(cafe)
                .totalPrice(0L)
                .stampDiscount(0L)
                .pointDiscount(0L)
                .amount(0L)
                .build());
    }

    @Transactional
    public OrderMenu addCart(AddCartDTO dto) {
        Optional<Order> optionalOrder = orderRepository.findById(dto.getOrderId());
        if (optionalOrder.isEmpty()) return null;
        Order order = optionalOrder.get();
        Menu menu = menuService.getMenuById(dto.getMenuId());
        if (menu == null) return null;
        OrderMenu orderMenu = orderMenuRepository.findByOrderAndMenu(order, menu)
                .orElse(OrderMenu.builder().order(order).menu(menu).quantity(0).build());
        orderMenu.setQuantity(orderMenu.getQuantity() + dto.getQuantity());
        order.setTotalPrice(order.getTotalPrice() + (orderMenu.getMenu().getPrice() * orderMenu.getQuantity()));
        order.setAmount(order.getTotalPrice() - order.getPointDiscount() - order.getStampDiscount());
        return orderMenuRepository.save(orderMenu);
    }


    @Transactional
    public OrderMenu useGift(long orderId, long stampGiftId) {
        Optional<Order> optionalOrder = orderRepository.findById(orderId);
        if (optionalOrder.isEmpty()) return null;
        Order order = optionalOrder.get();
        StampGift stampGift = stampService.getStampGiftById(stampGiftId);
        if (stampGift == null) return null;
        if (!Objects.equals(order.getCafe().getId(), stampGift.getStampBoard().getStampSetting().getCafe().getId()))
            return null;
        order.setTotalPrice(order.getTotalPrice() + stampGift.getMenu().getPrice());
        order.setStampDiscount(stampGift.getMenu().getPrice());
        order.setAmount(order.getTotalPrice() - order.getStampDiscount() - order.getPointDiscount());
        return orderMenuRepository.save(OrderMenu.builder()
                .order(order)
                .menu(stampGift.getMenu())
                .stampGift(stampGift)
                .quantity(1)
                .build());
    }

//    @Transactional
//    public Order checkOrder(long orderId){
//        Optional<Order> optionalOrder = orderRepository.findById(orderId);
//        if(optionalOrder.isEmpty()) return null;
//        Order order = optionalOrder.get();
//        order.getOrderMenus().forEach(orderMenu -> {
//            order.setTotalPrice(order.getTotalPrice()+(orderMenu.getMenu().getPrice() * orderMenu.getQuantity()));
//            if(orderMenu.getStampGift() != null)
//        });
//    }

    @Transactional
    public Order usePoint(long orderId, long point) {
        Optional<Order> optionalOrder = orderRepository.findById(orderId);
        if (optionalOrder.isEmpty()) return null;
        Order order = optionalOrder.get();
        if (order.getPointDiscount() + point > order.getUser().getPoint()) return null; //유저가 가진 포인트보다 많으면 적용안됨
        order.setPointDiscount(order.getPointDiscount() + point);
        order.setAmount(order.getTotalPrice() - order.getPointDiscount() - order.getStampDiscount());
        return orderRepository.save(order);
    }

    @Transactional
    public Order confirmOrder(long orderId) {
        Optional<Order> optionalOrder = orderRepository.findById(orderId);
        if (optionalOrder.isEmpty()) return null;
        Order order = optionalOrder.get();
        order.setTotalPrice(0L);
        order.setAmount(0L);
        order.getOrderMenus().forEach(orderMenu -> {
            order.setTotalPrice(order.getTotalPrice()+orderMenu.getMenu().getPrice() * orderMenu.getQuantity());
            if(orderMenu.getStampGift() != null){
                stampService.useStampGift(orderMenu.getStampGift());
                order.setStampDiscount(order.getStampDiscount()+orderMenu.getMenu().getPrice());
            }
        });
        //포인트 차감하는 거 만들거

        order.setAmount(order.getTotalPrice() - order.getStampDiscount() - order.getPointDiscount());
        order.setOrderedTime(LocalDateTime.now());
        order.setOrderState(OrderState.ORDERED);
        cafeService.recordMonthlyRecord(order.getCafe().getId(), order.getTotalPrice(), order.getStampDiscount(), order.getPointDiscount(), order.getAmount()); //월별매출기록
        return orderRepository.save(order);
    }

    public List<Order> getAllOrderByUser(long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if(optionalUser.isEmpty()) return null;
        User user = optionalUser.get();
        return orderRepository.findAllByUser(user);
    }

    public List<Order> getAllOrderByUserAndCafe(long userId, long cafeId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if(optionalUser.isEmpty()) return null;
        User user = optionalUser.get();
        Optional<Cafe> optionalCafe = cafeRepository.findById(cafeId);
        if (optionalCafe.isEmpty()) return null;
        Cafe cafe = optionalCafe.get();
        return orderRepository.findAllByUserAndCafe(user, cafe);
    }

    public Order getOrderById(long orderId) {
        return orderRepository.findById(orderId).orElse(null);
    }
}
