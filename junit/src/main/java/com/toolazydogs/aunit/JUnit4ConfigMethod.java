/**
 *
 * Copyright 2010 (C) The original author or authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.toolazydogs.aunit;

import java.lang.reflect.Method;


/**
 * A source of configuration options.
 *
 * @author Alin Dreghiciu (adreghiciu@gmail.com)
 * @version $Revision: $ $Date: $
 */
public interface JUnit4ConfigMethod
{
    /**
     * Returns true if the configuration method matches (applies) to the test method about to be run.
     *
     * @param testMethod test method that is about to be run
     * @return tru, if the configuration method applies to the test method
     */
    boolean matches(Method testMethod);

    /**
     * Returns an array of options.
     *
     * @return array of options (cannot be null)
     * @throws Exception - If determining the options fails
     */
    Option[] getOptions() throws Exception;

}
