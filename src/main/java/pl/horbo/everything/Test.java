package pl.horbo.everything;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("session")
public class Test {

    @Getter
    @Setter
//    @Value
    private float brutto;

    @Getter
    @Setter
    private float zaliczkaNaPit;

    @Getter
    @Setter
    private float kosztUzyskaniaDochodu;

}
