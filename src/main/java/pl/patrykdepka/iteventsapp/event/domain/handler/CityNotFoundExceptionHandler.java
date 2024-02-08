package pl.patrykdepka.iteventsapp.event.domain.handler;

import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import pl.patrykdepka.iteventsapp.core.AbstractExceptionHandler;
import pl.patrykdepka.iteventsapp.event.domain.exception.CityNotFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class CityNotFoundExceptionHandler extends AbstractExceptionHandler {
    private static final String MESSAGE_CODE = "exception.CityNotFoundException.message";

    public CityNotFoundExceptionHandler(MessageSource messageSource) {
        super(HttpStatus.NOT_FOUND, messageSource, MESSAGE_CODE, CityNotFoundException.class);
    }

    @Override
    public ModelAndView handle(HttpServletRequest request, HttpServletResponse response, Exception ex) {
        response.setStatus(httpStatus.value());
        return getDefaultModelAndView();
    }
}
