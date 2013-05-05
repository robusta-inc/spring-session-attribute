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
package org.springframework.web.bind.annotation;

/**
 * Annotation which indicates that a method parameter should be bound to a web
 * request session attribute. Supported for annotated handler methods in
 * Servlet environments.
 *
 * <p>An class which is injected with @SessionAttribute should provide
 * a default constructor (private scope is fine)</p>
 *
 * <p>Collections are not supported. As a workaround encapsulate the collection
 * in a session object and use with @SessionAttribute</p>
 * @author sudhir.ravindramohan
 * @since 1.0
 * @see RequestParam
 * @see org.springframework.web.bind.support.SessionAttributeWebArgumentResolver
 */
public @interface SessionAttribute {
    /**
     * The name of the session attribute to bind to.
     */
    String value() default "";

    /**
     * Whether the parameter is required.
     * <p>Default is {@code true}, leading to an exception thrown in case
     * of the attribute missing in the request. Switch this to {@code false}
     * if you prefer a {@code null} in case missing attribute.
     * <p>Alternatively, if {@link #createIfMissing() createIfMissing} is true
     * sets this flag to {@code false}.
     */
    boolean required() default true;

    /**
     * Whether a new attribute can be created if found missing in session.
     * When specified as {@code true}, required is ignored. The default constructor
     * will be used to initialize an instance of the attribute.
     */
    boolean createIfMissing() default false;

    /**
     * Create a new instance of the attribute irrespective of the session state of
     * the attribute.
     */
    boolean createNew() default false;
}
