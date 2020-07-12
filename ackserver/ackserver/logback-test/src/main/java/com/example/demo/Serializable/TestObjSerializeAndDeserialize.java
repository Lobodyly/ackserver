package com.example.demo.Serializable;

import org.springframework.stereotype.Service;

import java.io.*;
import java.text.MessageFormat;

@Service
public class TestObjSerializeAndDeserialize {

    public static void test() throws Exception {
        SerializePerson();
        Person person=DeserializePerson();
        System.out.println(MessageFormat.format("name={0},age={1},sex={2},address={3}",
                person.getName(), person.getAge(), person.getSex(),person.getAddress()));
    }

    /**
     * MethodName: SerializePerson
     * Description: 序列化Person对象
     * @throws FileNotFoundException
     * @throws IOException
     */
    private static void SerializePerson() throws IOException{
        Person person=new Person();
        person.setAge(25);
        person.setName("luoyu");
        person.setSex("男");
        person.setAddress("重庆市");
        FileOutputStream fileout = new FileOutputStream(new File("E:/test/Person.txt"));
        ObjectOutputStream out = new ObjectOutputStream(fileout);
        out.writeObject(person);
        System.out.println("Person对象序列化成功！");
        out.close();
        fileout.close();
    }

    private static Person DeserializePerson() throws Exception{
        FileInputStream inputFilt=new FileInputStream(new File("E:/test/Person.txt"));
        ObjectInputStream input=new ObjectInputStream(inputFilt);
        Person person = (Person) input.readObject();
        System.out.println("Person对象反序列化成功！");
        return person;
    }
}
