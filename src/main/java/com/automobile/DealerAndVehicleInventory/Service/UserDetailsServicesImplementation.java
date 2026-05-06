package com.automobile.DealerAndVehicleInventory.Service;

import com.automobile.DealerAndVehicleInventory.Entity.User;
import com.automobile.DealerAndVehicleInventory.Enum.Role;
import com.automobile.DealerAndVehicleInventory.Repository.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class UserDetailsServicesImplementation implements UserDetailsService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public UserDetailsServicesImplementation(UserRepository userRepository,PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder=passwordEncoder;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
            User user =userRepository.findByUsername(username);
        System.out.println(user);
            if (user==null){
                throw new UsernameNotFoundException("User Not Found");
            }
            return new org.springframework.security.core.userdetails.User(
                    user.getUsername(),
                    user.getPassword(),
                    List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole().name()))
            );
        }

    public Boolean registerUser(User user){
        if (user.getRole()==Role.DEALER_ADMIN || user.getRole()==Role.GLOBAL_ADMIN){
            if (userRepository.existsByTenantIdAndRole(user.getTenantId(), Role.DEALER_ADMIN) || userRepository.existsByTenantIdAndRole(user.getTenantId(), Role.GLOBAL_ADMIN)) {
                return false;
            }
        }
            if (userRepository.existsByTenantId(user.getTenantId())) {
                user.setPassword(passwordEncoder.encode(user.getPassword()));
                userRepository.save(user);
                return true;
            }
        return false;
    }
}
