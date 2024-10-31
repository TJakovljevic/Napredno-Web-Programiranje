package di;

import java.util.HashMap;
import java.util.Map;

public class DependencyContainer {
    private final Map<Class<?>, Object> beans = new HashMap<>();
    private final Map<Class<?>, Map<String, Object>> interfaceToImplementation = new HashMap<>();

    // Method to directly register a concrete implementation instance
    public <T> void registerBean(Class<T> clazz, T instance) {
        beans.put(clazz, instance);
        System.out.println("Beans: " + beans);
    }

    // Method to register an interface implementation with a qualifier
    public void registerInterface(Class<?> interfaceClass, Class<?> implementationClass, String qualifier) throws Exception {
        // If no instance exists, create and cache it based on the qualifier
        Object instance = implementationClass.getDeclaredConstructor().newInstance();

        // Register the instance under its qualifier
        interfaceToImplementation
                .computeIfAbsent(interfaceClass, k -> new HashMap<>())
                .put(qualifier, instance);
    }

    // Method to retrieve an instance by class or interface with a qualifier
    public <T> T getBean(Class<T> clazz, String qualifier) throws Exception {
        // Check if there is a directly registered instance
        if (beans.containsKey(clazz)) {
            return clazz.cast(beans.get(clazz));
        }
//        System.out.println("Interface: " + interfaceToImplementation);
        // Retrieve the implementation map for the interface
        Map<String, Object> implementations = interfaceToImplementation.get(clazz);


        if (implementations != null && implementations.containsKey(qualifier)) {
            return clazz.cast(implementations.get(qualifier));
        }

        // If no matching instance is found, throw an exception
        throw new Exception("No registered implementation found for " + clazz.getName() + " with qualifier " + qualifier);
    }
}
