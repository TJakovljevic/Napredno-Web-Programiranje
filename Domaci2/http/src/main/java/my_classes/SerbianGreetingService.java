package my_classes;

import annotations.Qualifier;
import annotations.Service;

@Service
@Qualifier("serbianGreetingService")
public class SerbianGreetingService implements GreetingsService{
    @Override
    public void greet() {
        System.out.println("Cao");
    }

    public SerbianGreetingService() {
    }
}
