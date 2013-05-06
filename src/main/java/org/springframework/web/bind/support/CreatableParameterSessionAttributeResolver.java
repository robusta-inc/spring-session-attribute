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

/**
 * @author sudhir.ravindramohan
 * @since 1.0
 */
class CreatableParameterSessionAttributeResolver extends AbstractChainingSessionAttributeResolver {

    protected CreatableParameterSessionAttributeResolver(SessionAttributeResolver next) {
        super(next);
    }

    @Override
    protected Object resolveSessionAttributeInternal(SessionHandler handler, SessionAttributeParameter parameter) {
        LOGGER.trace("Checking if SessionAttribute specified with createIfMissing = true");
        if(parameter.createableWhenNull()) {
            LOGGER.trace("createIfMissing is = true, creating a new instance with the SessionAttributeParameter and using the SessionHandler to add new instance to session");
            return handler.addToSession(parameter.resolvedAttributeName(), parameter.createNewInstance());
        }
        LOGGER.trace("createIfMissing is not = true, so this implementation cannot resolve SessionAttribute, passing the buck");
        return passTheBuck();
    }
}
