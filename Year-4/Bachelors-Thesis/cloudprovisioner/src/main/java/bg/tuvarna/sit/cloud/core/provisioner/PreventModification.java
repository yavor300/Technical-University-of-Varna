package bg.tuvarna.sit.cloud.core.provisioner;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.annotation.ElementType;

// TODO [Enhancement] Per field (e.g for PersistentMetadata)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface PreventModification {
}
