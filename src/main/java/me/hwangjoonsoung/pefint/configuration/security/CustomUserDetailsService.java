package me.hwangjoonsoung.pefint.configuration.security;

import lombok.RequiredArgsConstructor;
import me.hwangjoonsoung.pefint.domain.User;
import me.hwangjoonsoung.pefint.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

//    일반모드
//    @Override
//    public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {
//        System.out.println(userEmail);
//        User user = userRepository.findUserByEmail(userEmail);
//        return org.springframework.security.core.userdetails.User.withUsername(user.getName()).password(user.getPassword()).roles("USER").build();
//    }

//    custom mode
    @Override
    public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {
        User user = userRepository.findUserByEmail(userEmail);
        UserDetails userDetails = org.springframework.security.core.userdetails.User.withUsername(user.getName()).password(user.getPassword()).roles(user.getRole()).build();
        return userDetails;
    }
}
