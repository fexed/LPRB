package com.fexed.lprb.postoffice;

import java.util.LinkedList;

public class PostOffice {
    private Thread[] door;
    private LinkedList<Person> waitingRoom;

    public PostOffice(int k) {
        this.door = new Thread[4];
        this.waitingRoom = new LinkedList<>();
    }

    public boolean addNewPerson(Person p) {
        Boolean b = this.waitingRoom.add(p);
        if (b) System.out.println("\t\t**OKWT person: " + p.number + ", " + p.duration + "s");
        else System.out.println("\t\t**NOWT person: " + p.number + ", " + p.duration + "s\t\t!!");
        return b;
    }

    public void assignPerson(int i, Person p) {
        door[i] = new Thread(p);
        door[i].start();
        System.out.println("**ASSI: person " + p.number + " door " + i + "");
    }

    public int waitForDoor() {
        int i = 0;
        do {
            try {
                if (!door[i].isAlive()) return i;
                else i = (i + 1) % 4;
            } catch (NullPointerException ex) {
                System.out.println("**NULL: door " + i + " opening\t\t!!");
                return i;
            }
        } while(true);
    }

    public boolean hasPeople() {
        return !(this.waitingRoom.isEmpty());
    }

    public Person popPerson() {
        return this.waitingRoom.removeFirst();
    }
}
