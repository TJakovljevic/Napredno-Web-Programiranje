package di;

import annotations.*;
import framework.request.enums.Scope;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.*;

public class DIEngine {
    private DependencyContainer container;
    private Map<Class<?>, Object> singletonBeans = new HashMap<>();
    public Map<String, Method> routeMappings = new HashMap<>();



    public DIEngine(DependencyContainer container) {
        this.container = container;
    }


    private static final String BASE_PACKAGE = "my_classes";

    public Set<Class<?>> getAnnotatedClasses(Class<? extends Annotation> annotation) throws Exception {
        Set<Class<?>> annotatedClasses = new HashSet<>();

        // Zameni '.' sa '/' da bismo dobili odgovarajuću putanju za datoteke
        String path = BASE_PACKAGE.replace('.', '/');
        URL root = Thread.currentThread().getContextClassLoader().getResource(path);


        if (root == null) {
            throw new IllegalArgumentException("Package not found: " + BASE_PACKAGE);
        }
         File[] files = new File(root.toURI()).listFiles();
        // Pronađi sve klase u paketu
//        File[] files1 = new File(root.getFile()).listFiles();
//        System.out.println(root.getFile());
//        File file1 = new File(root.getFile());

        if (files != null) {
            for (File file : files) {
                if (file.isFile() && file.getName().endsWith(".class")) {
                    String className = BASE_PACKAGE + '.' + file.getName().replaceAll("\\.class$", "");
                    Class<?> clazz = Class.forName(className);

                    // Proveri da li je klasa anotirana datom anotacijom
                    if (clazz.isAnnotationPresent(annotation)) {
                        annotatedClasses.add(clazz);
                    }
                }
            }
        }
//        System.out.println("Anotirane: " + annotatedClasses);

        return annotatedClasses;
    }
    public void initialize() throws Exception {
        registerInterfacesAutomatically(); // Dodato automatsko mapiranje
        initializeControllers(); // Nastavite sa inicijalizacijom kontrolera
        initializeComponents();
    }
    public void initializeControllers() throws Exception {
        // Pronađi sve klase sa @Controller i inicijalizuj zavisnosti
        for (Class<?> controllerClass : getAnnotatedClasses(Controller.class)) {
            initializeBean(controllerClass);

        }
    }
    public void initializeComponents() throws Exception {
        // Pronađi sve klase sa @Controller i inicijalizuj zavisnosti
        for (Class<?> componentClass : getAnnotatedClasses(Component.class)) {
            initializeBean(componentClass);
        }
    }

    public Object initializeBean(Class<?> clazz) throws Exception {
        // Proverava da li je već inicijalizovan singleton bean
        if (singletonBeans.containsKey(clazz)) {
//            System.out.println("Singleton: " + singletonBeans);
            System.out.println("DIEngine: Vraćam postojeću instancu za " + clazz.getSimpleName());
            return singletonBeans.get(clazz);
        }

        if (!clazz.isAnnotationPresent(Bean.class) && !clazz.isAnnotationPresent(Service.class) && !clazz.isAnnotationPresent(Controller.class) && !clazz.isAnnotationPresent(Component.class)) {
            throw new Exception("Class " + clazz.getName() + " must be annotated with @Bean or @Service.");
        }

        // Kreira instancu i injectuje zavisnosti
        System.out.println("IoCContainer: Kreiram novu instancu za " + clazz.getSimpleName());
        Object instance = clazz.getDeclaredConstructor().newInstance();


        //Ovde se odradi onaj deo za singleton unutar Service ili scope=singleton
        if (isSingletonScope(clazz)) {
//            System.out.println("PROVERA KLASA: " + clazz);
            singletonBeans.put(clazz, instance);
        }
//        System.out.println("Nerealno je da ne radi singleton: " + singletonBeans);

        for (Field field : clazz.getDeclaredFields()) {
            if (field.isAnnotationPresent(Autowired.class)) {
                if (!isBeanField(field.getType())) {
                    throw new Exception("Field " + field.getName() + " in class " + clazz.getName() + " is @Autowired but does not correspond to a bean.");
                }
                System.out.println("IoCContainer: Injektujem zavisnost " + field.getType().getSimpleName() +
                        " u " + clazz.getSimpleName());
                injectField(instance, field);
            }
        }
        checkUniqueQualifier(clazz);


        // Inspect methods for @GET, @POST, and @Path annotations
        mapRoutes(clazz);

        // Here you can print or log the route mappings
      System.out.println("Route mappings for class " + clazz.getName() + ": " + routeMappings);


        return instance;
    }

    private boolean isBeanField(Class<?> fieldType) {
        // Check if the field type has any of the required bean annotations
        return fieldType.isAnnotationPresent(Bean.class) || fieldType.isAnnotationPresent(Service.class) || fieldType.isAnnotationPresent(Component.class);
    }


    private void mapRoutes(Class<?> clazz) throws Exception {
        if (!clazz.isAnnotationPresent(Controller.class)) {
            return; // Preskoči klase koje nisu anotirane sa @Controller
        }

        // Ako je klasa odgovarajuća, nastavi sa mapiranjem ruta
        for (Method method : clazz.getDeclaredMethods()) {
            String path = null;
            if (method.isAnnotationPresent(Path.class)) {
                Path pathAnnotation = method.getAnnotation(Path.class);
                path = pathAnnotation.value();
            }

            if (method.isAnnotationPresent(Get.class) && path != null) {
                String fullPath = "GET " + path;
                checkRouteUniqueness(fullPath);
                routeMappings.put(fullPath, method);
            } else if (method.isAnnotationPresent(Post.class) && path != null) {
                String fullPath = "POST " + path;
                checkRouteUniqueness(fullPath);
                routeMappings.put(fullPath, method);
            }
        }
    }
    private void checkRouteUniqueness(String fullPath) throws Exception {
        if (routeMappings.containsKey(fullPath)) {
            throw new Exception("Duplicate route mapping found for: " + fullPath);
        }
    }

    private void injectField(Object instance, Field field) throws Exception {
        field.setAccessible(true);
        Autowired autowired = field.getAnnotation(Autowired.class);
        Class<?> fieldType = field.getType();
        Object dependency = fieldType.isInterface() ? resolveQualifiedDependency(field, fieldType) : initializeBean(fieldType);


        if(field.isAnnotationPresent(Qualifier.class)){
            String qualifier = field.getAnnotation(Qualifier.class).value();
            dependency = container.getBean(fieldType, qualifier);
        }
        // Verbalni mod za praćenje inicijalizacije
        field.set(instance, dependency);

        // Verbose logging if enabled
        if (autowired.verbose()) {
            String logMessage = String.format(
                    "Initialized %s %s in %s on %s with %s",
                    fieldType.getSimpleName(),
                    field.getName(),
                    instance.getClass().getSimpleName(),
                    LocalDateTime.now(),
                    dependency.hashCode()
            );
            System.out.println(logMessage);
        }

        if (fieldType.isInterface()) {
            if (!field.isAnnotationPresent(Qualifier.class)) {
                throw new Exception("Field " + field.getName() + " must have a @Qualifier annotation when it's an interface.");
            }
            String qualifier = field.getAnnotation(Qualifier.class).value();
            dependency = container.getBean(fieldType, qualifier); // Dohvatanje implementacije iz container-a
            if (dependency == null) {
                throw new Exception("No implementation found in DependencyContainer for interface: " + fieldType.getName() + " with qualifier: " + qualifier);
            }
        } else {
            // Ako nije interfejs, inicijalizuje bean
            dependency = initializeBean(fieldType);
        }

        // Postavljanje zavisnosti
        field.set(instance, dependency);
    }

    private void registerInterfacesAutomatically() throws Exception {
        for (Class<?> clazz : getAnnotatedClasses(Service.class)) {
            for (Class<?> interfaceClass : clazz.getInterfaces()) {
                // Assuming you need to provide a qualifier and possibly a scope or other argument
                String qualifier = clazz.getAnnotation(Qualifier.class).value(); // Example of generating a qualifier
                container.registerInterface(interfaceClass, clazz, qualifier); // Adjust according to your actual method signature
            }
        }
    }

    private Object resolveQualifiedDependency(Field field, Class<?> fieldType) throws Exception {
        if (!field.isAnnotationPresent(Qualifier.class)) {
            throw new Exception("Field " + field.getName() + " must have a @Qualifier annotation.");
        }
        Qualifier qualifier = field.getAnnotation(Qualifier.class);
        return container.getBean(fieldType, qualifier.value());
    }
    private boolean isSingletonScope(Class<?> clazz) {
        if (clazz.isAnnotationPresent(Service.class)) return true;
        if (clazz.isAnnotationPresent(Component.class)) return false;
        Bean bean = findBeanAnnotation(clazz);
        return bean == null || bean.scope() == Scope.SINGLETON;
    }

    private Bean findBeanAnnotation(Class<?> clazz) {
        while (clazz != null) {
            Bean bean = clazz.getAnnotation(Bean.class);
            if (bean != null) {
                return bean;
            }
            clazz = clazz.getSuperclass();
        }
        return null;
    }
    public void handleRequest(String httpMethod, String path) {
        String fullPath = httpMethod + " " + path;
        Method method = routeMappings.get(fullPath);



        if (method != null) {
            try {
                Class<?> controllerClass = method.getDeclaringClass();
                // Preuzmite instancu kontrolera
                Object controllerInstance = singletonBeans.get(controllerClass);
                System.out.println("Controller: " + controllerInstance);
                method.invoke(controllerInstance);
            } catch (Exception e) {
                e.printStackTrace(); // Obrada greške
            }
        } else {

            System.out.println("Ruta nije pronađena: " + fullPath);

        }
    }

    private void checkUniqueQualifier(Class<?> clazz) throws Exception {
        Map<String, Class<?>> qualifiers = new HashMap<>();
        for (Field field : clazz.getDeclaredFields()) {
            if (field.isAnnotationPresent(Qualifier.class)) {
                String value = field.getAnnotation(Qualifier.class).value();
                if (qualifiers.containsKey(value)) {
                    throw new Exception("Multiple beans with qualifier " + value + " found in " + clazz.getName());
                }
                qualifiers.put(value, field.getType());
            }

        }
    }

    public Map<String, Method> getRouteMappings() {
        return routeMappings;
    }
}
