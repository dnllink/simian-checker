package br.com.bonaldo.simianchecker.domains;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DnaStats {
    private boolean id;
    private Long total;

    public boolean isHuman() {
        return !id;
    }

    public boolean isSimian() {
        return id;
    }
}