package com.teamseven.cafeplatform.domain.user.service;

import com.teamseven.cafeplatform.domain.user.dto.UserJoinDTO;
import com.teamseven.cafeplatform.domain.user.dto.UserLoginDTO;
import com.teamseven.cafeplatform.domain.user.entity.User;
import com.teamseven.cafeplatform.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository userRepository;

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

    public User getUserById(Long id){
        if(id!=null) {
            return userRepository.findById(id).orElse(null);
        }
        return null;
    }
}
