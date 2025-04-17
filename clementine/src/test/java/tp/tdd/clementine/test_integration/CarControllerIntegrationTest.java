package tp.tdd.clementine.test_integration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import tp.tdd.clementine.Car;
import tp.tdd.clementine.CarRepository;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class CarControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CarRepository carRepository;

    @BeforeEach
    void setUp() {
        carRepository.getAllCars().clear();
    }

    @Test
    void testGetAllCars() throws Exception {
        carRepository.addCar(new Car("123ABC", "Toyota", true));

        mockMvc.perform(get("/cars"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].registrationNumber").value("123ABC"))
                .andExpect(jsonPath("$[0].model").value("Toyota"))
                .andExpect(jsonPath("$[0].available").value(true));
    }

    @Test
    void testRentCar() throws Exception {
        carRepository.addCar(new Car("123ABC", "Toyota", true));

        mockMvc.perform(post("/cars/rent/123ABC"))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));

        mockMvc.perform(get("/cars"))
                .andExpect(jsonPath("$[0].available").value(false));
    }

    @Test
    void testReturnCar() throws Exception {
        carRepository.addCar(new Car("123ABC", "Toyota", false));

        mockMvc.perform(post("/cars/return/123ABC"))
                .andExpect(status().isOk());

        mockMvc.perform(get("/cars"))
                .andExpect(jsonPath("$[0].available").value(true));
    }

    @Test
    void testAddCar() throws Exception {
        mockMvc.perform(post("/cars/add")
                .param("registrationNumber", "789GHI")
                .param("model", "Ford"))
                .andExpect(status().isOk());
    
        assertTrue(carRepository.getAllCars().stream()
                .anyMatch(car -> car.getRegistrationNumber().equals("789GHI") && car.getModel().equals("Ford")));
    }

}
