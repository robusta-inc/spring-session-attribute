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
import org.springframework.web.bind.annotation.SessionAttribute;

import static org.springframework.util.StringUtils.isEmpty;

/**
 * @author sudhir.ravindramohan
 * @since 1.0
 */
class SessionAttributeParameter {
    private final SessionAttribute definedAnnotation;
    private final String parameterName;
    private final Class<?> parameterType;

    SessionAttributeParameter(MethodParameter methodParameter) {
        this.parameterName = methodParameter.getParameterName();
        this.definedAnnotation = methodParameter.getParameterAnnotation(SessionAttribute.class);
        this.parameterType = methodParameter.getParameterType();
    }


    String resolvedAttributeName() {
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
