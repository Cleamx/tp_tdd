package tp.tdd.clementine;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CarRentalService {

    @Autowired
    private CarRepository carRepository;

    public List<Car> getAllCars() {
        return carRepository.getAllCars();
    }

    public boolean rentCar(String registrationNumber) {
        Optional<Car> car = carRepository.findByRegistrationNumber(registrationNumber);
        if (car.isPresent() && car.get().isAvailable()) {
            car.get().setAvailable(false);
            carRepository.updateCar(car.get());
            return true;
        }
        return false;
    }

    public void returnCar(String registrationNumber) {
        Optional<Car> car = carRepository.findByRegistrationNumber(registrationNumber);
        car.ifPresent(c -> {
            c.setAvailable(true);
            carRepository.updateCar(c);
        });
    }

    public void addCar(String registrationNumber, String model) {
        if (carRepository.findByRegistrationNumber(registrationNumber).isPresent()) {
            throw new IllegalArgumentException("Car with this registration number already exists.");
        }
        carRepository.addCar(new Car(registrationNumber, model, true));
    }
    
    public List<Car> searchCarsByModel(String model) {
        return carRepository.getAllCars().stream()
                .filter(car -> car.getModel().equalsIgnoreCase(model))
                .toList();
    }

}
