package pl.patrykdepka.iteventsapp.event.domain.handler;

import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import pl.patrykdepka.iteventsapp.core.AbstractExceptionHandler;
import pl.patrykdepka.iteventsapp.event.domain.exception.EventNotFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class EventNotFoundExceptionHandler extends AbstractExceptionHandler {
    private static final String MESSAGE_CODE = "exception.EventNotFoundException.message";

    public EventNotFoundExceptionHandler(MessageSource messageSource) {
        super(HttpStatus.NOT_FOUND, messageSource, MESSAGE_CODE, EventNotFoundException.class);
    }

    @Override
    public ModelAndView handle(HttpServletRequest request, HttpServletResponse response, Exception ex) {
        response.setStatus(httpStatus.value());
        return getDefaultModelAndView();
    }
}
