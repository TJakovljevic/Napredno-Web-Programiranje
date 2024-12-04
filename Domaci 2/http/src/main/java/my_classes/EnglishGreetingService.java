package my_classes;

import annotations.Qualifier;
import annotations.Service;

@Service
@Qualifier("englishGreetingService")
public class EnglishGreetingService implements GreetingsService{

    @Override
    public void greet() {
        System.out.println("Hello");
    }

    public EnglishGreetingService() {
    }
}
