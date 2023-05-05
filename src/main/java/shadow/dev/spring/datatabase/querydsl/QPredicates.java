package shadow.dev.spring.datatabase.querydsl;

import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.Expressions;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class QPredicates {

    public static QPredicates builder() {
        return new QPredicates();
    }

    private final List<Predicate> predicates = new ArrayList<>();

    public <T> QPredicates add(T object, Function<T,Predicate> function){
        if(object!=null){
            predicates.add(function.apply(object));
        }
        return this;
    }

    public Predicate build() {
        return Optional.ofNullable(ExpressionUtils.allOf(predicates))
                .orElseGet(() -> Expressions.asBoolean(true).isTrue());
//        return ExpressionUtils.allOf(predicates);
    }

    public Predicate buildOr() {
return         Optional.ofNullable(ExpressionUtils.anyOf(predicates))
                .orElseGet(() -> Expressions.asBoolean(true).isTrue());

//        return ExpressionUtils.anyOf(predicates);
    }
}
