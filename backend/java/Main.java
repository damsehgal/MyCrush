package com.company;

import com.company.Person;
import com.company.SetOfPerson;
import com.company.TernarySearchTree;
import com.company.Java8WatchServiceExample;

import java.io.IOException;
import java.nio.file.*;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import static java.nio.file.StandardWatchEventKinds.*;

/*
* Insert in TST on SignUp
* Search in TST on query receive number of persons to be returned
*
* /insert/
* args[0] = true
*   insert()
* args[1]
*   name
* args[2]
*   id
* args[3]
*   lopp
*   // java wale folder se chalani h
*
* /search/
*   args[0]: false
*   args[1]: number of values
*   args[2]: prefix
* */
public class Main {

    static TernarySearchTree <SetOfPerson> ternarySearchTree;

    public static void main(String[] args) throws IOException {

        Java8WatchServiceExample java8WatchServiceExample = new Java8WatchServiceExample(Paths.get("../ternarySearchInsert"));
        java8WatchServiceExample.processEvents();

        /*Scanner scanner = new Scanner(System.in);
        ternarySearchTree = new TernarySearchTree<SetOfPerson>();
        for (int i = 0; i < 16; i++)
        {
            String name, id, lopp;
            name = scanner.next();
            id = scanner.next();
            lopp = scanner.next();
            insert(name, id, lopp);
        }



        while (true)
        {
            returnPerson(scanner.nextInt(), scanner.next());
        }*/

    }

    public static void insert(String name, String id, String lopp)
    {
        Person person = new Person(Long.parseLong(id), lopp);
        if(ternarySearchTree.get(name) == null)
        {
            SetOfPerson setOfPerson = new SetOfPerson(name);
            setOfPerson.set.add(person);
            ternarySearchTree.put(name, setOfPerson);
        }
        else
        {
            ternarySearchTree.get(name).set.add(person);
        }

    }

    public static void returnPerson(int numberOfPerson, String prefix) {

            List<SetOfPerson> listSetOfPerson = ternarySearchTree.matchPrefix(prefix, numberOfPerson);
            int count = 0;

            Boolean allPersonsAchieved = false;
            for (SetOfPerson setOfPerson:listSetOfPerson) {
                System.err.println(setOfPerson.name);

                    for(Person person : setOfPerson.set)
                    {
                        count++;

                        System.err.println(person);
                        if (count > numberOfPerson){
                            allPersonsAchieved = true;
                            break;
                        }

                    }
                    if (allPersonsAchieved)
                        break;
            }




    }
}

