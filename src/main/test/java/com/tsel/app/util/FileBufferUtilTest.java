package com.tsel.app.util;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "file:src/main/test/resources/FileBufferUtilTestContext.xml")
public class FileBufferUtilTest {

    @Autowired
    private FileBufferUtil bufferUtil;

    @Test
    public void shouldWriteAndReadObjectsToNotExistFile() {
        bufferUtil.clearBuff(TestObject.class);

        List<TestObject> testObjectList = asList(getTestObj(), getTestObj(), getTestObj());
        bufferUtil.addObjectsToBuff(TestObject.class, testObjectList);
        List<TestObject> objectsFromFile = bufferUtil.getObjectsFromBuff(TestObject.class);

        assertThat(objectsFromFile, is(testObjectList));
    }

    @Test
    public void writeThreeObjectsAndReadItShouldReturnAllElements() {
        bufferUtil.clearBuff(TestObject.class);

        TestObject t1 = getTestObj();
        TestObject t2 = getTestObj();
        TestObject t3 = getTestObj();

        bufferUtil.addObjectsToBuffEnd(TestObject.class, singletonList(t1));
        List<TestObject> objectsFromFile = bufferUtil.getObjectsFromBuff(TestObject.class);
        assertThat(objectsFromFile, is(singletonList(t1)));

        bufferUtil.addObjectsToBuffEnd(TestObject.class, singletonList(t2));
        objectsFromFile = bufferUtil.getObjectsFromBuff(TestObject.class);
        assertThat(objectsFromFile, is(asList(t1, t2)));

        bufferUtil.addObjectsToBuff(TestObject.class, singletonList(t3));
        objectsFromFile = bufferUtil.getObjectsFromBuff(TestObject.class);
        assertThat(objectsFromFile, is(singletonList(t3)));
    }

    private TestObject getTestObj() {
        Random random = new Random();
        return new TestObject(
                random.nextInt(Integer.MAX_VALUE),
                generateStingList(),
                random.nextInt(Integer.MAX_VALUE),
                random.nextBoolean()
        );
    }

    private List<String> generateStingList() {
        Random random = new Random();
        char[] chars = "abcdefghijklmnopqrstuvwxyz".toCharArray();
        int stringLength = random.nextInt(30);
        int stringCount = random.nextInt(10);
        List<String> strings = new ArrayList<>();

        for (int j = 0; j < stringCount; j++) {
            StringBuilder sb = new StringBuilder(stringLength);
            for (int i = 0; i < stringLength; i++) {
                char c = chars[random.nextInt(chars.length)];
                sb.append(c);
            }
            strings.add(sb.toString());
        }
        return strings;
    }

    private static class TestObject {
        private int integer;
        private List<String> string;
        private Subclass subclass;

        public TestObject(int integer, List<String> string, Integer sI, boolean sB) {
            this.integer = integer;
            this.string = string;
            this.subclass = new Subclass(sI, sB);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            TestObject that = (TestObject) o;
            return integer == that.integer &&
                    string.equals(that.string) &&
                    subclass.equals(that.subclass);
        }

        @Override
        public int hashCode() {
            return Objects.hash(integer, string, subclass);
        }

        private static class Subclass {
            private Integer integer;
            private boolean aBoolean;

            public Subclass(Integer integer, boolean aBoolean) {
                this.integer = integer;
                this.aBoolean = aBoolean;
            }

            @Override
            public boolean equals(Object o) {
                if (this == o) return true;
                if (o == null || getClass() != o.getClass()) return false;
                Subclass subclass = (Subclass) o;
                return aBoolean == subclass.aBoolean &&
                        integer.equals(subclass.integer);
            }

            @Override
            public int hashCode() {
                return Objects.hash(integer, aBoolean);
            }
        }
    }
}