package br.com.bonaldo.simianchecker.gateways.adapters;

import br.com.bonaldo.simianchecker.gateways.exceptions.InvalidConversionException;

public interface Adapter<T, J> {
    J parse(T t) throws InvalidConversionException;
}