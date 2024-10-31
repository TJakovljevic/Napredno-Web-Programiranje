package my_classes;

import annotations.Controller;
import annotations.Get;
import annotations.Path;

@Controller
public class TempController {

    @Get
    @Path("/ayo")
    public void sayHello() {
        System.out.println("Hello, World!");
    }

    public TempController() {
    }
}
