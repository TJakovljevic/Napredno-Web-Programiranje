package my_classes;

import annotations.*;

@Controller
public class MyController {

    @Autowired(verbose = false)
    @Qualifier("englishGreetingService")
    private GreetingsService englishGreetingService;
    @Autowired(verbose = true)
    private RecursiveServiceNew recursiveServiceNew;

    @Autowired(verbose = true)
    private MyComponent component;

    @Autowired(verbose = true)
    private MyComponent component1;

    //da se ispise component, i rekurzija, field za setovanje pa gore sve sa ispisima, misli kao rekurznivno da se ispise sve

    

    //Provera za autowired a da nije bean
//    @Autowired(verbose = true)
//    private String path;

    @Autowired(verbose = false)
    @Qualifier("serbianGreetingService")
    //Isto provera autowired bez qualifier
    //Za proveru iste vrednosti qualifiera
//    @Qualifier("englishGreetingService")
    private GreetingsService serbianGreetingService;

    public MyController() {
    }

    @Get
    @Path("/hello")
    public void sayHello() {
        this.englishGreetingService.greet();
    }

    @Get
    @Path("/cao")
    public void sayCao() {
        this.serbianGreetingService.greet();
    }

    //Za proveru unikatnosti rute
//    @Get
//    @Path("/cao")
//    public void saybye() {
//        this.serbianGreetingService.greet();
//    }


    @Get
    @Path("/bye")
    public void sayBye() {
        System.out.println("Bye, World!");
    }

    @Post
    @Path("/hello-test")
    public void postHello() {
        System.out.println("Hello, World! (POST)");
    }
}
