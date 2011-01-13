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

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.toolazydogs.aunit.internal.ParserWrapper;
import org.antlr.runtime.ANTLRInputStream;
import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.Lexer;
import org.antlr.runtime.Parser;
import org.antlr.runtime.RuleReturnScope;
import org.antlr.runtime.tree.Tree;


/**
 * @version $Revision: $ $Date: $
 */
public class Work
{
    public static LexerResults scan(String characters) throws Exception
    {
        if (characters == null) throw new IllegalArgumentException("Characters cannot be null");
        if (AunitRuntime.getLexerFactory() == null) throw new IllegalStateException("Lexer factory not set by configuration");

        Lexer lexer = AunitRuntime.getLexerFactory().generate(new ANTLRStringStream(characters));
        return new LexerResults(lexer);
    }

    public static Tree parse(String stream, SelectedRule selectedRule) throws Exception
    {
        if (stream == null) throw new IllegalArgumentException("Stream cannot be null");
        if (selectedRule == null) throw new IllegalArgumentException("SelectedRule cannot be null, please use rule()");
        if (AunitRuntime.getLexerFactory() == null) throw new IllegalStateException("Lexer factory not set by configuration");
        if (AunitRuntime.getParserFactory() == null) throw new IllegalStateException("Parser factory not set by configuration");

        Lexer lexer = AunitRuntime.getLexerFactory().generate(new ANTLRStringStream(stream));
        Parser parser = AunitRuntime.getParserFactory().generate(new CommonTokenStream(lexer));

        RuleReturnScope rs = selectedRule.invoke(parser);

        ParserWrapper wrapper = (ParserWrapper)parser;
        if (wrapper.isFailOnError() && !wrapper.getErrors().isEmpty())
        {
            throw new ParserException(wrapper.getErrors());
        }

        return (Tree)rs.getTree();
    }

    public static SelectedRule rule(String rule, Object... arguments) throws Exception
    {
        if (AunitRuntime.getParserFactory() == null) throw new IllegalStateException("Parser factory not set by configuration");

        for (Method method : collectMethods(AunitRuntime.getParserFactory().getParserClass()))
        {
            if (method.getName().equals(rule))
            {
                return new SelectedRule(method, arguments);
            }
        }

        throw new Exception("Rule " + rule + " not found");
    }

    public static <T> T generateParser(String src) throws Exception
    {
        List<String> l = Collections.emptyList();
        ANTLRInputStream input = new ANTLRInputStream(new ByteArrayInputStream(src.getBytes()));
        Lexer lexer = AunitRuntime.getLexerFactory().generate(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        return (T)AunitRuntime.getParserFactory().generate(tokens);
    }

    public static <T> T generateParser(File src) throws Exception
    {
        ANTLRInputStream input = new ANTLRInputStream(new FileInputStream(src));
        Lexer lexer = AunitRuntime.getLexerFactory().generate(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        return (T)AunitRuntime.getParserFactory().generate(tokens);
    }

    private static Set<Method> collectMethods(Class clazz)
    {
        if (clazz == null) return Collections.emptySet();

        Set<Method> s = new HashSet<Method>();

        s.addAll(Arrays.asList(clazz.getDeclaredMethods()));
        s.addAll(collectMethods(clazz.getSuperclass()));

        return s;
    }

    private Work() { }
}
