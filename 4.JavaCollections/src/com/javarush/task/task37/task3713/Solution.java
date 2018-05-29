package com.javarush.task.task37.task3713;

import com.javarush.task.task37.task3713.space.crew.AbstractCrewMember;
import com.javarush.task.task37.task3713.space.SpaceShip;

import static com.javarush.task.task37.task3713.space.crew.AbstractCrewMember.CompetencyLevel.*;

/* 
Chain of Responsibility
*/
public class Solution {
    public static void main(String[] args) {
        SpaceShip spaceShip = new SpaceShip();
        AbstractCrewMember crewMember = spaceShip.getCrewChain();

        crewMember.handleRequest(EXPERT, "ATTACK");
        System.out.println("-----------------------------------------");
        crewMember.handleRequest(NOVICE, "WASH THE FLOOR");
        System.out.println("-----------------------------------------");
        crewMember.handleRequest(INTERMEDIATE, "CHECK ENGINE");
        System.out.println("-----------------------------------------");
        crewMember.handleRequest(ADVANCED, "SET ROUTE");
    }
}
