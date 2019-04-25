package br.com.bonaldo.simianchecker.gateways.controllers.jsons;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class ErrorResource {
    private List<String> errors;
}