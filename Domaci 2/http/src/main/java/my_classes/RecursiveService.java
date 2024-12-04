package my_classes;

import annotations.Autowired;
import annotations.Service;

@Service
public class RecursiveService {

    public RecursiveService(){
        System.out.println("Recursive service initialised");
    }
}
