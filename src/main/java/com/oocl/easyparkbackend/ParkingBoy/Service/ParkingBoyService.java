package com.oocl.easyparkbackend.ParkingBoy.Service;

import com.itmuch.lightsecurity.jwt.JwtOperator;
import com.itmuch.lightsecurity.jwt.User;
import com.itmuch.lightsecurity.jwt.UserOperator;
import com.oocl.easyparkbackend.ParkingBoy.Entity.ParkingBoy;
import com.oocl.easyparkbackend.ParkingBoy.Exception.NotFindParkingBoyException;
import com.oocl.easyparkbackend.ParkingBoy.Exception.ParkingBoyIdErrorException;
import com.oocl.easyparkbackend.ParkingBoy.Exception.UserNameOrPasswordErrorException;
import com.oocl.easyparkbackend.ParkingBoy.Repository.ParkingBoyRepository;
import com.oocl.easyparkbackend.ParkingLot.Entity.ParkingLot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class ParkingBoyService {

    @Autowired
    private ParkingBoyRepository repository;
    @Autowired
    private JwtOperator jwtOperator;
    @Autowired
    private UserOperator userOperator;

    public String login(ParkingBoy parkingBoy) {
        Optional<ParkingBoy> optionalParkingBoy = Optional.empty();
        if (parkingBoy.getEmail() != null && parkingBoy.getPassword() != null) {
            optionalParkingBoy = repository.getByEmailAndPassword(parkingBoy.getEmail(), parkingBoy.getPassword());
        }
        if (parkingBoy.getUsername() != null && parkingBoy.getPassword() != null) {
            optionalParkingBoy = repository.getByUsernameAndPassword(parkingBoy.getUsername(), parkingBoy.getPassword());
        }
        if (parkingBoy.getPhoneNumber() != null && parkingBoy.getPassword() != null) {
            optionalParkingBoy = repository.getByPhoneNumberAndPassword(parkingBoy.getPhoneNumber(), parkingBoy.getPassword());
        }
        if (optionalParkingBoy.isPresent()) {
            User user = User.builder()
                    .id(Integer.valueOf(optionalParkingBoy.get().getId()))
                    .username(optionalParkingBoy.get().getUsername())
                    .roles(Arrays.asList("parkingBoy"))
                    .build();
            return jwtOperator.generateToken(user);
        }
        throw new UserNameOrPasswordErrorException();
    }

    public ParkingBoy findParkingBoy() {
        User user = userOperator.getUser();
        ParkingBoy parkingBoy = repository.findById(user.getId()).orElse(null);
        if(parkingBoy == null){
            throw  new NotFindParkingBoyException();
        }
        return parkingBoy;
    }

}
