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

import java.util.ArrayList;
import java.util.List;

import org.antlr.runtime.CharStream;
import org.antlr.runtime.RecognizerSharedState;

import com.toolazydogs.aunit.internal.LexerWrapper;
import com.toolazydogs.aunit.tests.CMinusLexer;


/**
 * Fake lexer to help w/ ASM investigation on extending existing lexers.
 *
 * @version $Revision: $ $Date: $
 */
public class FooLexer extends CMinusLexer implements LexerWrapper
{
    private final List<String> errors = new ArrayList<String>();
    private boolean failOnError = false;

    public FooLexer()
    {
    }

    public FooLexer(CharStream input)
    {
        super(input);
    }

    public FooLexer(CharStream input, RecognizerSharedState state)
    {
        super(input, state);
    }


    @Override
    public void emitErrorMessage(String msg)
    {
        errors.add(msg);
    }

    public List<String> getErrors()
    {
        return errors;
    }

    public boolean isFailOnError()
    {
        return failOnError;
    }

    public void setFailOnError(boolean failOnError)
    {
        this.failOnError = failOnError;
    }
}
