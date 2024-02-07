package pl.patrykdepka.iteventsapp.image.domain.handler;

import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import pl.patrykdepka.iteventsapp.core.AbstractExceptionHandler;
import pl.patrykdepka.iteventsapp.image.domain.exception.DefaultImageNotFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class DefaultImageNotFoundExceptionHandler extends AbstractExceptionHandler {
    private static final String MESSAGE_CODE = "exception.DefaultEventImageNotFoundException.message";

    public DefaultImageNotFoundExceptionHandler(MessageSource messageSource) {
        super(HttpStatus.INTERNAL_SERVER_ERROR, messageSource, MESSAGE_CODE, DefaultImageNotFoundException.class);
    }

    @Override
    public ModelAndView handle(HttpServletRequest request, HttpServletResponse response, Exception ex) {
        response.setStatus(httpStatus.value());
        return getDefaultModelAndView();
    }
}
