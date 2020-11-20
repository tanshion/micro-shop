package com.abc1236.ms.exception;

import com.abc1236.ms.core.result.ResultEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.autoconfigure.web.servlet.error.AbstractErrorController;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.error.ErrorAttributeOptions.Include;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.server.AbstractServletWebServerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
/**
 * Basic global error {@link Controller @Controller}, rendering {@link ErrorAttributes}.
 * More specific errors can be handled either using Spring MVC abstractions (e.g.
 * {@code @ExceptionHandler}) or by adding servlet
 * {@link AbstractServletWebServerFactory#setErrorPages server error pages}.
 *
 * @author Dave Syer
 * @author Phillip Webb
 * @author Michael Stummvoll
 * @author Stephane Nicoll
 * @author Scott Frederick
 * @since 1.0.0
 * @see ErrorAttributes
 * @see ErrorProperties
 */
@Controller
@RequestMapping("${server.error.path:${error.path:/error}}")
public class GlobalErrorController extends AbstractErrorController {

    @Autowired
    public GlobalErrorController(ErrorAttributes errorAttributes, ServerProperties serverProperties) {
        super(errorAttributes);
        this.errorProperties=serverProperties.getError();
    }

	private final ErrorProperties errorProperties;

	//@RequestMapping(produces = MediaType.TEXT_HTML_VALUE)
    //@ResponseBody
    //public ResultEntity<Object> errorHtml(HttpServletRequest request, HttpServletResponse response) {
    //    return handError(request);
	//}

	@RequestMapping
    @ResponseBody
	public ResultEntity<Object> error(HttpServletRequest request) {
        return handError(request);
	}

    private ResultEntity<Object> handError(HttpServletRequest request) {
        HttpStatus status = getStatus(request);
        if (status == HttpStatus.NO_CONTENT) {
            return ResultEntity.fail().message(status.value()+":"+status.getReasonPhrase());
        }
        Map<String, Object> body = getErrorAttributes(request, getErrorAttributeOptions(request, MediaType.ALL));
        String msg = body.get("status")+":"+body.get("error")+":"+body.get("message");
        return ResultEntity.fail().message(msg);
    }

	@ExceptionHandler(HttpMediaTypeNotAcceptableException.class)
	public ResultEntity<Object> mediaTypeNotAcceptable(HttpServletRequest request) {
		HttpStatus status = getStatus(request);
        return ResultEntity.fail().message(status.value()+":"+status.getReasonPhrase());
	}

	protected ErrorAttributeOptions getErrorAttributeOptions(HttpServletRequest request, MediaType mediaType) {
		ErrorAttributeOptions options = ErrorAttributeOptions.defaults();
		if (this.errorProperties.isIncludeException()) {
			options = options.including(Include.EXCEPTION);
		}
		if (isIncludeStackTrace(request, mediaType)) {
			options = options.including(Include.STACK_TRACE);
		}
		if (isIncludeMessage(request, mediaType)) {
			options = options.including(Include.MESSAGE);
		}
		if (isIncludeBindingErrors(request, mediaType)) {
			options = options.including(Include.BINDING_ERRORS);
		}
		return options;
	}

	/**
	 * Determine if the stacktrace attribute should be included.
	 * @param request the source request
	 * @param produces the media type produced (or {@code MediaType.ALL})
	 * @return if the stacktrace attribute should be included
	 */
	@SuppressWarnings("deprecation")
	protected boolean isIncludeStackTrace(HttpServletRequest request, MediaType produces) {
		switch (getErrorProperties().getIncludeStacktrace()) {
		case ALWAYS:
			return true;
		case ON_PARAM:
		case ON_TRACE_PARAM:
			return getTraceParameter(request);
		default:
			return false;
		}
	}

	/**
	 * Determine if the message attribute should be included.
	 * @param request the source request
	 * @param produces the media type produced (or {@code MediaType.ALL})
	 * @return if the message attribute should be included
	 */
	protected boolean isIncludeMessage(HttpServletRequest request, MediaType produces) {
		switch (getErrorProperties().getIncludeMessage()) {
		case ALWAYS:
			return true;
		case ON_PARAM:
			return getMessageParameter(request);
		default:
			return false;
		}
	}

	/**
	 * Determine if the errors attribute should be included.
	 * @param request the source request
	 * @param produces the media type produced (or {@code MediaType.ALL})
	 * @return if the errors attribute should be included
	 */
	protected boolean isIncludeBindingErrors(HttpServletRequest request, MediaType produces) {
		switch (getErrorProperties().getIncludeBindingErrors()) {
		case ALWAYS:
			return true;
		case ON_PARAM:
			return getErrorsParameter(request);
		default:
			return false;
		}
	}

	/**
	 * Provide access to the error properties.
	 * @return the error properties
	 */
	protected ErrorProperties getErrorProperties() {
		return this.errorProperties;
	}

	@SuppressWarnings("deprecation")
    @Override
    public String getErrorPath() {
        return null;
    }
}
