package pl.edu.mimuw.trade.io;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface SkipSerialization {
}
