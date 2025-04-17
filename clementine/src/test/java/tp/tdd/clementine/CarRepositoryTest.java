package tp.tdd.clementine;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class CarRepositoryTest {

    private Car car;
    private CarRepository carRepository;
    
    @BeforeEach
    void setUp() {
        carRepository = new CarRepository();
        car = new Car("123ABC", "Toyota", true);
    }

    @Test
    void testAddCar() {
        carRepository.addCar(car);
        assertEquals(1, carRepository.getAllCars().size());
    }

    @Test
    void testFindByRegistrationNumber() {
        carRepository.addCar(car);
        Optional<Car> foundCar = carRepository.findByRegistrationNumber("123ABC");
        assertTrue(foundCar.isPresent());
        assertEquals("Toyota", foundCar.get().getModel());
    }

    @Test
    void testUpdateCar() {
        carRepository.addCar(car);
        car.setAvailable(false);
        carRepository.updateCar(car);
        Optional<Car> updatedCar = carRepository.findByRegistrationNumber("123ABC");
        assertTrue(updatedCar.isPresent());
        assertFalse(updatedCar.get().isAvailable());
    }
}
