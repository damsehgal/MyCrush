package com.company;

/**
 * Created by dam on 11/4/17.
 */
public class Person {
    public long id;
    public String linkOfProfilePicture;

    public Person(long id, String linkOfProfilePicture) {
        this.id = id;
        this.linkOfProfilePicture = linkOfProfilePicture;
    }

    @Override
    public boolean equals(Object obj) {
        Person me = (Person) obj;
        return me.id == this.id;
    }

    @Override
    public String toString() {
        return "id = " + Long.toString(id) + " linkOfProfilePicture = " + linkOfProfilePicture;
    }
}

