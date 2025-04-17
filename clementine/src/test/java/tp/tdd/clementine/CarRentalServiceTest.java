package tp.tdd.clementine;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CarRentalServiceTest {

    @Mock
    private CarRepository carRepository;

    @InjectMocks
    private CarRentalService carRentalService;

    private Car availableCar;    
    private Car unavailableCar;

    @Spy
    private Car spiedCar = new Car("ABC123", "Toyota Corolla", true);

    @BeforeEach
    void setUp() {
        availableCar = new Car("ABC123", "Toyota Corolla", true);
        unavailableCar = new Car("XYZ789", "Honda Civic", false);
    }

    @Test
    void returnAllCars() {
        List<Car> expectedCars = Arrays.asList(availableCar, unavailableCar);
        when(carRepository.getAllCars()).thenReturn(expectedCars);
        
        List<Car> actualCars = carRentalService.getAllCars();

        assertEquals(expectedCars, actualCars);
        verify(carRepository, times(1)).getAllCars();
    }

    @Test
    void shouldReturnTrueAndUpdateCar() {

        when(carRepository.findByRegistrationNumber("ABC123")).thenReturn(Optional.of(spiedCar));
        
        boolean result = carRentalService.rentCar("ABC123");

        assertTrue(result);
        assertFalse(spiedCar.isAvailable());
        verify(carRepository, times(1)).updateCar(spiedCar);
    }

    @Test
    void unavailableCar() {

        when(carRepository.findByRegistrationNumber("XYZ789")).thenReturn(Optional.of(unavailableCar));

        boolean result = carRentalService.rentCar("XYZ789");

        assertFalse(result);
        verify(carRepository, never()).updateCar(any());
    }

}