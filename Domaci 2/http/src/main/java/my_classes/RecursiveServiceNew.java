package my_classes;

import annotations.Autowired;
import annotations.Service;

@Service
public class RecursiveServiceNew {
    @Autowired(verbose = true)
    private RecursiveService recursiveService;

    public RecursiveServiceNew(){
        System.out.println("Recursive service new initialised");
    }
}
