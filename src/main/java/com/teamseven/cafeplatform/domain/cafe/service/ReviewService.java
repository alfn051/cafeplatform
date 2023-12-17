package com.teamseven.cafeplatform.domain.cafe.service;

import com.teamseven.cafeplatform.domain.cafe.dto.ReviewDTO;
import com.teamseven.cafeplatform.domain.cafe.dto.ReviewDisplayDTO;
import com.teamseven.cafeplatform.domain.cafe.dto.ReviewEvaluateDTO;
import com.teamseven.cafeplatform.domain.cafe.entity.Cafe;
import com.teamseven.cafeplatform.domain.cafe.entity.Review;
import com.teamseven.cafeplatform.domain.cafe.entity.ReviewEvaluate;
import com.teamseven.cafeplatform.domain.cafe.repository.CafeRepository;
import com.teamseven.cafeplatform.domain.cafe.repository.ReviewEvaluateRepository;
import com.teamseven.cafeplatform.domain.cafe.repository.ReviewRepository;
import com.teamseven.cafeplatform.domain.order.entity.Order;
import com.teamseven.cafeplatform.domain.order.service.OrderService;
import com.teamseven.cafeplatform.domain.user.entity.User;
import com.teamseven.cafeplatform.domain.user.repository.UserRepository;
import com.teamseven.cafeplatform.file.service.FileService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReviewService {
    private final OrderService orderService;
    private final ReviewRepository reviewRepository;
    private final ReviewEvaluateRepository reviewEvaluateRepository;
    private final FileService fileService;
    private final CafeRepository cafeRepository;
    private final UserRepository userRepository;

    public Review createReview(ReviewDTO dto) {
        Order order = orderService.getOrderById(dto.getOrderId());
        if(order == null) return null;
        Review review = reviewRepository.save(dto.toEntity(order));
        if (dto.getPhotoList() != null) {
            try {
                fileService.storeReviewPhotos(dto.getPhotoList(), review);
            }catch (IOException e){
                log.error("리뷰 등록 사진 파일 저장 실패"+e.getMessage());
            }
        }
        return review;
    }

    public List<ReviewDisplayDTO> getAllReviewByCafe(long cafeId) {
        Optional<Cafe> optionalCafe = cafeRepository.findById(cafeId);
        if(optionalCafe.isEmpty()) return null;
        Cafe cafe = optionalCafe.get();
        return cafe.getOrders().stream().map(Order::getReview).toList().stream().map(review -> ReviewDisplayDTO.builder().review(review).reviewPhoto(review.getFirstReviewPhoto()).build()).toList();
    }

    public List<ReviewDisplayDTO> getAllReviewByUser(long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isEmpty()) return null;
        User user = optionalUser.get();
        return user.getOrders().stream().map(Order::getReview).toList().stream().map(review -> ReviewDisplayDTO.builder().review(review).reviewPhoto(review.getFirstReviewPhoto()).build()).toList();
    }

    public List<ReviewDisplayDTO> getFolloweeRecentReview(long userId, int number) {
        Pageable pageable =  PageRequest.of(0, number, Sort.by(Sort.Direction.DESC, "createTime"));
        return reviewRepository.findFolloweeRecentReview(userId, pageable).stream().map(review -> ReviewDisplayDTO.builder().review(review).reviewPhoto(review.getFirstReviewPhoto()).build()).toList();
    }

    @Transactional
    public ReviewEvaluate evaluateReview(ReviewEvaluateDTO dto){
        Optional<User> optionalUser = userRepository.findById(dto.getUserId());
        if(optionalUser.isEmpty()) return null;
        User user = optionalUser.get();
        Optional<Review> optionalReview = reviewRepository.findById(dto.getReviewId());
        if (optionalReview.isEmpty()) return null;
        Review review = optionalReview.get();
        review.setEvaluate(review.getEvaluate()+dto.getEvaluate().getNumber());
        return reviewEvaluateRepository.save(ReviewEvaluate.builder().user(user).review(review).evaluate(dto.getEvaluate()).build());
    }
}
