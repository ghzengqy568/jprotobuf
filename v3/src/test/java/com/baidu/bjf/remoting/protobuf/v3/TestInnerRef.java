/*
 * Copyright 2002-2007 the original author or authors.
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
package com.baidu.bjf.remoting.protobuf.v3;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.baidu.bjf.remoting.protobuf.Codec;
import com.baidu.bjf.remoting.protobuf.FieldType;
import com.baidu.bjf.remoting.protobuf.ProtobufProxy;
import com.baidu.bjf.remoting.protobuf.annotation.Protobuf;

import junit.framework.Assert;

/**
 * The Class TestInnerRef.
 */
public class TestInnerRef {

    /** The test 1. */
    @Protobuf(fieldType = FieldType.MAP, order = 1, description = "test")
    Map<Integer, TestInnerRef.Test1> test1;

    @Protobuf
    TestInnerRef.Test1 tt;
    
    /**
     * The Class Test1.
     */
    public static class Test1{
        
        /** The tast 2. */
        @Protobuf(fieldType = FieldType.OBJECT, order = 1, description = "test1")
        List<TestInnerRef.Test2> tast2;
    }

    /**
     * The Class Test2.
     */
    public static class Test2{
        
        /** The name. */
        @Protobuf(fieldType = FieldType.INT32, order = 1, description = "test2")
        int name;
    }
    

    /**
     * Test inner ref class.
     */
    @Test
    public void testInnerRefClass() {
        Codec<TestInnerRef> codec = ProtobufProxy.create(TestInnerRef.class, true);
        
        TestInnerRef ref = new TestInnerRef();
        ref.test1 = new HashMap<Integer, TestInnerRef.Test1>();
        
        Test1 test1 = new Test1();
        test1.tast2 = new ArrayList<TestInnerRef.Test2>();
        
        Test2 test2 = new Test2();
        test2.name = 12;
        
        test1.tast2.add(test2);
        
        ref.test1.put(1, test1);
        
        try {
            byte[] bytes = codec.encode(ref);
            
            TestInnerRef decode = codec.decode(bytes);
            
            Assert.assertEquals(ref.test1.size(), decode.test1.size());
            
        } catch (IOException e) {
            Assert.fail(e.getMessage());
        }
    }
}
