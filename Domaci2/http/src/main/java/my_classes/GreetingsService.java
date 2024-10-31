package my_classes;

import annotations.Bean;
import annotations.Qualifier;
import annotations.Service;

@Service
public interface GreetingsService {
    void greet();
}
