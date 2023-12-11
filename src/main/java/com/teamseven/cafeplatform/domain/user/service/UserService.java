package com.teamseven.cafeplatform.domain.user.service;

import com.teamseven.cafeplatform.domain.cafe.service.CafeService;
import com.teamseven.cafeplatform.domain.propensity.dto.UserPropensityDTO;
import com.teamseven.cafeplatform.domain.propensity.service.PropensityService;
import com.teamseven.cafeplatform.domain.user.dto.UserJoinDTO;
import com.teamseven.cafeplatform.domain.user.dto.UserLoginDTO;
import com.teamseven.cafeplatform.domain.user.entity.Follow;
import com.teamseven.cafeplatform.domain.user.entity.User;
import com.teamseven.cafeplatform.domain.user.repository.FollowRepository;
import com.teamseven.cafeplatform.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final FollowRepository followRepository;
    private final PropensityService propensityService;

    public User join(UserJoinDTO dto) {
        if(checkLoginIdDuplicate(dto.getLoginId())) return null;
        return userRepository.save(dto.toEntity());
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

    public User setPropensity(UserPropensityDTO dto) {
        Optional<User> optionalUser = userRepository.findById(dto.getUserId());
        if (optionalUser.isEmpty()) return null;
        User user = optionalUser.get();
        propensityService.setUserPropensity(dto, user);
        return user;
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



}
