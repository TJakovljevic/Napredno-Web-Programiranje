package annotations;

import framework.request.enums.Scope;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Bean(scope = Scope.PROTOTYPE)
public @interface Component {
}
//da se pokaze da nije signleton, da je injectovan