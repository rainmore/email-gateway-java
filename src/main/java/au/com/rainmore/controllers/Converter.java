package au.com.rainmore.controllers;

public interface Converter<E, D> {

    E from(D dto);

}
