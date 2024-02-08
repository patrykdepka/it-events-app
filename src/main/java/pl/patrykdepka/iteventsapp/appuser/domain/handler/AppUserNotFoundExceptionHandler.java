package pl.patrykdepka.iteventsapp.appuser.domain.handler;

import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import pl.patrykdepka.iteventsapp.appuser.domain.exception.AppUserNotFoundException;
import pl.patrykdepka.iteventsapp.core.AbstractExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class AppUserNotFoundExceptionHandler extends AbstractExceptionHandler {
    private static final String MESSAGE_CODE = "exception.AppUserNotFoundException.message";

    public AppUserNotFoundExceptionHandler(MessageSource messageSource) {
        super(HttpStatus.NOT_FOUND, MESSAGE_CODE, messageSource, AppUserNotFoundException.class);
    }

    @Override
    public ModelAndView handle(HttpServletRequest request, HttpServletResponse response, Exception ex) {
        response.setStatus(httpStatus.value());
        return getDefaultModelAndView();
    }
}
