package com.vntana.core.rest.resource.conf.aop;

import com.vntana.commons.api.model.request.ValidatableRequest;
import com.vntana.commons.api.model.response.impl.AbstractResultResponseModel;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by Arthur Asatryan.
 * Date: 3/2/18
 * Time: 3:01 PM
 */
@Aspect
@Component
public class ServiceFacadeMethodValidationAspect {

    @Around("execution(public * com.vntana.core.rest.facade..*.impl..* (..)) " +
            "&& args(validatableRequest,..)"
    )
    public Object around(final ProceedingJoinPoint point, final ValidatableRequest<?> validatableRequest) throws Throwable {
        Assert.notNull(validatableRequest, "The validatable request should not be null");
        final Method method = ((MethodSignature) point.getSignature()).getMethod();
        final Type type = method.getGenericReturnType();
        final List<?> validationResult = validatableRequest.validate();
        if (!validationResult.isEmpty()) {
            final Class<AbstractResultResponseModel> clazz =
                    (type instanceof ParameterizedType) ? (Class<AbstractResultResponseModel>) ((ParameterizedType) type).getRawType() : (Class<AbstractResultResponseModel>) type;
            final AbstractResultResponseModel resultResponseModel = clazz.newInstance();
            resultResponseModel.errors(validationResult);
            resultResponseModel.setHttpStatusCode(422);
            resultResponseModel.success(false);
            return resultResponseModel;
        }
        return point.proceed();
    }

}
