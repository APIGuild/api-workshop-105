package com.guild.api.demo.dao.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientException;
import com.netflix.hystrix.exception.HystrixRuntimeException;

public class DaoExceptionBuilder {
    private String source;

    public DaoExceptionBuilder(String source) {
        this.source = source;
    }

    public DaoException build(Throwable exception) {
        DaoException daoException = translateException(exception);
        daoException.setSource(source);
        return daoException;
    }

    private DaoException translateException(Throwable exception) {
        if (exception instanceof HystrixRuntimeException) {
            return translateHystrixException((HystrixRuntimeException) exception);
        } else if (exception instanceof RestClientException) {
            return translateRestClientException((RestClientException) exception);
        }
        return new DaoException(exception.getMessage(), exception);
    }

    private DaoException translateHystrixException(HystrixRuntimeException hystrixException) {
        HystrixRuntimeException.FailureType failureType = hystrixException.getFailureType();

        Throwable cause = hystrixException.getCause();
        if (failureType == HystrixRuntimeException.FailureType.COMMAND_EXCEPTION) {
            return translateException(cause);
        } else if (failureType == HystrixRuntimeException.FailureType.TIMEOUT) {
            return new CommunicationException("Communication Timeout", cause);
        }
        return new ClientException(cause.getMessage(), cause);
    }

    private DaoException translateRestClientException(RestClientException clientException) {
        if (clientException instanceof ResourceAccessException) {
            return new CommunicationException(clientException.getMessage(), clientException);
        } else if (clientException instanceof HttpStatusCodeException) {
            HttpStatus statusCode = ((HttpStatusCodeException) clientException).getStatusCode();
            if (statusCode == HttpStatus.NOT_FOUND) {
                return new NotFoundException(clientException);
            }
            return new ServiceException(clientException.getMessage(), clientException);
        }
        return new DaoException(clientException.getMessage(), clientException);
    }


}

