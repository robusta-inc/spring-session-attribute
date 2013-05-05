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

import org.springframework.web.bind.MissingServletRequestSessionAttributeException;

/**
 * An AbstractChainingSessionAttributeResolver implementation that checks if the
 * session attribute is annotated as always create new.
 * <p>If so calls for creation of the session attribute and returns the created
 * instance as resolved</p>
 * <p>Else returns null.</p>
 * @author sudhir.ravindramohan
 * @since 1.0
 */
class AlwaysCreateSessionAttributeResolver extends AbstractChainingSessionAttributeResolver {
    protected AlwaysCreateSessionAttributeResolver(SessionAttributeResolver next) {
        super(next);
    }

    @Override
    protected Object resolveSessionAttributeInternal(SessionHandler handler, SessionAttributeParameter parameter) throws MissingServletRequestSessionAttributeException {
        if(parameter.isAlwaysCreate()) {
            return handler.addToSession(parameter.resolvedAttributeName(), parameter.createNewInstance());
        }
        return null;
    }
}
