package di;

import java.util.HashMap;
import java.util.Map;

public class DependencyContainer {
    private final Map<Class<?>, Object> beans = new HashMap<>();
    private final Map<Class<?>, Map<String, Object>> interfaceToImplementation = new HashMap<>();


    public <T> void registerBean(Class<T> clazz, T instance) {
        beans.put(clazz, instance);
        System.out.println("Beans: " + beans);
    }


    public void registerInterface(Class<?> interfaceClass, Class<?> implementationClass, String qualifier) throws Exception {

        Object instance = implementationClass.getDeclaredConstructor().newInstance();


        interfaceToImplementation
                .computeIfAbsent(interfaceClass, k -> new HashMap<>())
                .put(qualifier, instance);
    }


    public <T> T getBean(Class<T> clazz, String qualifier) throws Exception {

        if (beans.containsKey(clazz)) {
            return clazz.cast(beans.get(clazz));
        }
//        System.out.println("Interface: " + interfaceToImplementation);

        Map<String, Object> implementations = interfaceToImplementation.get(clazz);


        if (implementations != null && implementations.containsKey(qualifier)) {
            return clazz.cast(implementations.get(qualifier));
        }


        throw new Exception("No registered implementation found for " + clazz.getName() + " with qualifier " + qualifier);
    }
}
