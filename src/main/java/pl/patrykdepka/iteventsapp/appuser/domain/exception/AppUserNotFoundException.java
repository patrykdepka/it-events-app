package pl.patrykdepka.iteventsapp.appuser.domain.exception;

public class AppUserNotFoundException extends RuntimeException {

    public AppUserNotFoundException(String message) {
        super(message);
    }
}
