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

import static org.springframework.web.bind.support.WebArgumentResolver.UNRESOLVED;

/**
 * @author sudhir.ravindramohan
 * @since 1.0
 */
class OptionalParameterSessionAttributeResolver extends AbstractChainingSessionAttributeResolver {

    protected OptionalParameterSessionAttributeResolver(SessionAttributeResolver nextInChain) {
        super(nextInChain);
    }

    @Override
    protected Object resolveSessionAttributeInternal(SessionHandler handler, SessionAttributeParameter parameter) {
        LOGGER.trace("Checking if SessionAttribute specified with required = false (i.e. is optional)");
        if(parameter.isOptional()) {
            LOGGER.trace("The SessionAttribute is indeed optional, so returning resolution as WebArgumentResolver.UNRESOLVED");
            return UNRESOLVED;
        }
        LOGGER.trace("SessionAttribute is mandatory, so this implementation cannot resolve SessionAttribute, passing the buck");
        return passTheBuck();
    }
}
