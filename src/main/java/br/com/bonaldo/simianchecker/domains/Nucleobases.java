package br.com.bonaldo.simianchecker.domains;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum Nucleobases {

    A("A"),
    C("C"),
    G("G"),
    T("T");

    private String base;

    public static boolean isInvalidBase(final String base) {
        return Arrays.stream(Nucleobases.values())
                .noneMatch(nucleobase -> nucleobase.getBase().equals(base));
    }
}