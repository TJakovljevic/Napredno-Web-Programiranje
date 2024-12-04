package my_classes;

import annotations.*;

@Component
public class MyComponent {

    public MyComponent(){System.out.println("My Component initialised");}


    @Autowired(verbose = false)
    @Qualifier("englishGreetingService")
    private GreetingsService englishGreetingService;




}
