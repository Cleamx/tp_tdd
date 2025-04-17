package tp.tdd.clementine;
import java.util.List;

public class CarController {

    private CarRentalService carRentalService;

    public List<Car> getAllCars() {
        return carRentalService.getAllCars();
    }

    public boolean rentCar(String registrationNumber) {
        return carRentalService.rentCar(registrationNumber);
    }

    public void returnCar(String registrationNumber) {
        carRentalService.returnCar(registrationNumber);
    }
}
