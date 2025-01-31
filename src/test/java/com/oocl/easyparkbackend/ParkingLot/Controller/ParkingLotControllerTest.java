package com.oocl.easyparkbackend.ParkingLot.Controller;

import com.oocl.easyparkbackend.ParkingBoy.Service.ParkingBoyService;
import com.oocl.easyparkbackend.ParkingLot.Entity.ParkingLot;
import com.oocl.easyparkbackend.ParkingLot.Service.ParkingLotService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ParkingLotController.class)
public class ParkingLotControllerTest {
    @Autowired
    private MockMvc mvc;
    @MockBean
    private ParkingLotService parkingLotService;

    @Test
    void should_return_parkingLots_when_invoke_getParkingLotsByParkingBoy() throws Exception {
        List<ParkingLot> parkingLotList = new ArrayList<>();
        ParkingLot parkingLot1 = new ParkingLot();
        parkingLot1.setId("1");
        parkingLot1.setName("停车场1");
        parkingLot1.setAvailable(20);
        parkingLot1.setCapacity(10);
        parkingLotList.add(parkingLot1);

        when(parkingLotService.getParkingLotByParkingBoy()).thenReturn(parkingLotList);
        ResultActions resultActions = mvc.perform(get("/parkingLots"));

        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].name").value("停车场1"));
    }
}
