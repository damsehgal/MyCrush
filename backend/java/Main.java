package com.company;

import com.company.Person;
import com.company.SetOfPerson;
import com.company.TernarySearchTree;
import com.company.Java8WatchServiceExample;

import java.io.*;
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

        ternarySearchTree = new TernarySearchTree<>();
        Java8WatchServiceExample java8WatchServiceExample = new Java8WatchServiceExample(Paths.get("../ternarySearchInsert"));
        java8WatchServiceExample.setOnFileChangeListener(new Java8WatchServiceExample.OnFileChangeListener() {
            BufferedReader bufferedReader;
            @Override
            public void OnFileChange(String fileName) {
                System.err.println(fileName);
                if (fileName.equals("../ternarySearchInsert/insert.txt"))
                {
                    try
                    {
                        bufferedReader= new BufferedReader(new FileReader("../tstInsert.txt"));
                        String line;
                        while ((line = bufferedReader.readLine()) != null)
                        {
                            System.err.println(line);
			    String arr[] = line.split("\\s+");
			    for (int i = 0; i < 3; ++i)
			    {
				System.err.println(arr[i]);
			    }
			    insert(arr[0], Long.parseLong(arr[1]), arr[2]);
                        }
                        bufferedReader.close();
                    }
                    catch (FileNotFoundException e)
                    {
                        System.err.print("IO Exception");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                else if (fileName.equals("../ternarySearchInsert/inputSearchInTST.txt")) {
                    try {
                        bufferedReader = new BufferedReader(new FileReader("../inputSearchInTST.txt"));
                        String line;
                        while ((line = bufferedReader.readLine()) != null) {
                            System.err.println(line);
                            returnPerson(line.split("\\s+")[1], Integer.parseInt(line.split("\\s+")[0]));

                        }
                        bufferedReader.close();
                    } catch (FileNotFoundException e) {
                        System.err.print("IO Exception");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                else if (fileName.equals("../ternarySearchInsert/inputSearchAllInTST.txt"))
                {
                    try
                    {

                        bufferedReader = new BufferedReader(new FileReader("../inputSearchAllInTST.txt"));
                        String line = bufferedReader.readLine();
                        System.err.println(line);
                        getAllPersons(line);
                        bufferedReader.close();
                    }
                    catch (FileNotFoundException e)
                    {
                        System.err.print("IO Exception");

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        java8WatchServiceExample.processEvents();



    }
    private static void getAllPersons(String name) throws FileNotFoundException {
        SetOfPerson setOfPerson = ternarySearchTree.get(name);
        PrintWriter printWriter = new PrintWriter("../outputSearchAllInTST.txt");
        PrintWriter printWriter1 = new PrintWriter("../outputSearchInTernarySearchTree/outputSearchAllInTST.txt");

        if (setOfPerson == null) {
            printWriter.println("no user found");
            printWriter1.println("no user found");
        }
        else
        {
            for (Person person:setOfPerson.set)
            {
                printWriter.println(person);
                printWriter1.println("no user found");
            }
        }
        printWriter.close();
        printWriter1.close();
    }
    private static void insert(String name, long id, String lopp)
    {
        Person person = new Person(id, lopp);
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

    private static void returnPerson(String prefix, int numberOfPerson) throws FileNotFoundException {
        PrintWriter printWriter = new PrintWriter("../outputSearchInTST.txt");
        PrintWriter printWriter1 = new PrintWriter("../outputSearchInTernarySearchTree/outputSearchInTST.txt");

        List<SetOfPerson> listSetOfPerson = ternarySearchTree.matchPrefix(prefix, numberOfPerson);
            int count = 0;

            Boolean allPersonsAchieved = false;
            for (SetOfPerson setOfPerson:listSetOfPerson) {


                    for(Person person : setOfPerson.set)
                    {
                        count++;
                        System.err.println(setOfPerson.name + " " + person);
                        printWriter.println(setOfPerson.name + " " + person);
                        printWriter1.println(setOfPerson.name + " " + person);
                        if (count > numberOfPerson){
                            allPersonsAchieved = true;
                            break;
                        }

                    }
                    if (allPersonsAchieved)
                        break;
            }
            printWriter.close();
            printWriter1.close();
    }
}

