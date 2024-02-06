package pl.patrykdepka.iteventsapp.event.domain.exception;

public class CityNotFoundException extends RuntimeException {

    public CityNotFoundException(String message) {
        super(message);
    }
}
