package com.teamseven.cafeplatform.domain.user.service;

import com.teamseven.cafeplatform.config.BaseEntity;
import com.teamseven.cafeplatform.domain.cafe.entity.Cafe;
import com.teamseven.cafeplatform.domain.cafe.entity.Review;
import com.teamseven.cafeplatform.domain.cafe.repository.CafeRepository;
import com.teamseven.cafeplatform.domain.cafe.service.CafeService;
import com.teamseven.cafeplatform.domain.order.entity.Order;
import com.teamseven.cafeplatform.domain.order.service.OrderService;
import com.teamseven.cafeplatform.domain.propensity.dto.UserPropensityDTO;
import com.teamseven.cafeplatform.domain.propensity.entity.UserPropensity;
import com.teamseven.cafeplatform.domain.propensity.service.PropensityService;
import com.teamseven.cafeplatform.domain.stamp.service.StampService;
import com.teamseven.cafeplatform.domain.user.dto.UserJoinDTO;
import com.teamseven.cafeplatform.domain.user.dto.UserLoginDTO;
import com.teamseven.cafeplatform.domain.user.entity.CafeMember;
import com.teamseven.cafeplatform.domain.user.entity.Follow;
import com.teamseven.cafeplatform.domain.user.entity.User;
import com.teamseven.cafeplatform.domain.user.repository.CafeMemberRepository;
import com.teamseven.cafeplatform.domain.user.repository.FollowRepository;
import com.teamseven.cafeplatform.domain.user.repository.UserRepository;
import com.teamseven.cafeplatform.file.common.Classify;
import com.teamseven.cafeplatform.file.service.FileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final CafeMemberRepository cafeMemberRepository;
    private final UserRepository userRepository;
    private final FollowRepository followRepository;
    private final PropensityService propensityService;
    private final CafeRepository cafeRepository;
    private final FileService fileService;
    private final StampService stampService;

    public User join(UserJoinDTO dto) {
        if(checkLoginIdDuplicate(dto.getLoginId())) return null;
        User user = userRepository.save(dto.toEntity());
        if (dto.getPhoto() != null) {
            try {
                user.setPhoto(fileService.storePhoto(dto.getPhoto(), Classify.USER).orElseThrow());
            } catch (IOException e) {
                log.error("유저등록시 사진저장 실패: "+ e.getMessage());
            }
        }
        return userRepository.save(user);
    }

    public boolean checkLoginIdDuplicate(String loginId) {
        return userRepository.existsByLoginId(loginId);
    }

    public User login(UserLoginDTO dto) {
        Optional<User> optionalUser = userRepository.findByLoginId(dto.getLoginId());
        if (optionalUser.isEmpty()) return null;
        User user = optionalUser.get();
        if (!user.getPassword().equals(dto.getPassword())) return null;
        return user;
    }

    public UserPropensity setPropensity(UserPropensityDTO dto) {
        Optional<User> optionalUser = userRepository.findById(dto.getUserId());
        if (optionalUser.isEmpty()) return null;
        User user = optionalUser.get();
        return propensityService.setUserPropensity(dto, user);
    }

    public User getUserById(Long id){
        if(id!=null) {
            return userRepository.findById(id).orElse(null);
        }
        return null;
    }

    public User getUserByLoginId(String loginId){
        if(loginId!=null) {
            return userRepository.findByLoginId(loginId).orElse(null);
        }
        return null;
    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    @Transactional
    public Follow follow(Long followerId, Long followeeId){
        Optional<User> follower = userRepository.findById(followerId); //아몰라 걍 리턴널 때릴래
        Optional<User> followee = userRepository.findById(followeeId);
        if(follower.isPresent() && followee.isPresent()){
            return followRepository.save(Follow.builder()
                    .follower(follower.get())
                    .followee(followee.get())
                    .build());
        }
        return null;
    }

    public void unFollow(Long followId){
        followRepository.deleteById(followId);
    }

    public CafeMember joinMember(Long userId, Long cafeId){
        User user = getUserById(userId);
        if(user == null) return null;
        Optional<Cafe> optionalCafe = cafeRepository.findById(cafeId);
        if (optionalCafe.isEmpty()) return null;
        Cafe cafe = optionalCafe.get();
        CafeMember cafeMember = cafeMemberRepository.save(CafeMember.builder().user(user).cafe(cafe).build());
        stampService.makeStampBoard(userId, cafeId);
        return cafeMember;
    }

    public CafeMember joinMember(User user, Cafe cafe){
        CafeMember cafeMember = cafeMemberRepository.save(CafeMember.builder().user(user).cafe(cafe).build());
        stampService.makeStampBoard(user.getId(), cafe.getId());
        return cafeMember;
    }


    //ReviewService 에 있는거 사용(그건 쿼리문으로 불러옴)
//    public List<Review> getFolloweeRecentReview(Long userId, int number) {
//        return userRepository.findById(userId)
//                .map(value -> value.getFollowees().stream()
//                .map(Follow::getFollowee)
//                .flatMap(user -> user.getOrders().stream())
//                .map(Order::getReview)
//                .sorted(Comparator.comparing(BaseEntity::getCreateTime).reversed())
//                .limit(number)
//                .collect(Collectors.toList())).orElse(null);
//
//    }
}
