package com.company;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by dam on 12/4/17.
 */
public class SetOfPerson {
    String name;
    public Set<Person> set;

    SetOfPerson(String name) {
        this.name = name;
        set = new HashSet<Person>();
    }
}

