package pl.patrykdepka.iteventsapp.event.domain.exception;

public class EventNotFoundException extends RuntimeException {

    public EventNotFoundException(String message) {
        super(message);
    }
}
