/*
 * Copyright 2002-2012 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.web.bind.support;

import org.springframework.beans.BeanUtils;
import org.springframework.core.MethodParameter;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.SessionAttribute;

import static org.springframework.util.StringUtils.isEmpty;

/**
 * Encapsulates a method parameter (spring object).
 * Provides behaviors for:
 * <ol>
 * <li>Reading annotated attributes of {@link org.springframework.web.bind.annotation.SessionAttribute}.</li>
 * <li>Bean instantiation - delegates to {@link org.springframework.beans.BeanUtils}</li>
 * </ol>
 * @author sudhir.ravindramohan
 * @since 1.0
 * @see org.springframework.beans.BeanUtils
 * @see org.springframework.core.MethodParameter
 * @see org.springframework.web.bind.annotation.SessionAttribute
 */
public final class SessionAttributeParameter {
    private final SessionAttribute definedAnnotation;
    private final String parameterName;
    private final Class<?> parameterType;

    public SessionAttributeParameter(MethodParameter methodParameter) {
        Assert.notNull(methodParameter, "Cannot initialize with a null MethodParameter");
        this.parameterName = methodParameter.getParameterName();
        Assert.state(!isEmpty(this.parameterName), "Cannot initialize without a valid MethodParameter.parameterName");
        this.definedAnnotation = methodParameter.getParameterAnnotation(SessionAttribute.class);
        Assert.notNull(this.definedAnnotation, "Cannot initialize without a valid MethodParameter.parameterAnnotation of type SessionAttribute");
        this.parameterType = methodParameter.getParameterType();
        Assert.notNull(this.parameterType, "Cannot initialize without a valid MethodParameter.parameterType");
    }


    public String resolvedAttributeName() {
        return isEmpty(definedAnnotation.value()) ? parameterName : definedAnnotation.value();
    }

    public boolean createableWhenNull() {
        return definedAnnotation.createIfMissing();
    }

    public Object createNewInstance() {
        return BeanUtils.instantiateClass(parameterType);
    }

    public boolean isOptional() {
        return !definedAnnotation.required();
    }

    public boolean isAlwaysCreate() {
        return definedAnnotation.createNew();
    }
}