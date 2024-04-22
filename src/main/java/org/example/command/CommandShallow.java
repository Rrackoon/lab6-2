package org.example.command;

import org.example.client.models.StudyGroup;
import org.example.client.models.Color;
import org.example.client.models.Location;
import org.example.client.models.Person;
import org.example.client.models.Coordinates;
import org.example.client.models.FormOfEducation;

public class CommandShallow {
    private Command command;
    private String[] args;
    private StudyGroup studyGroup;
   // private CommandShallow[] commands;

    public CommandShallow() {
        this.command = null;
        this.args = null;
        this.studyGroup = null;
    }

    public CommandShallow(Command command, String[] args) {
        this.command = command;
        this.args = args;
        this.studyGroup = null;
        //this.command.check(args.length);
    }


    public Command getCommand() {
        return command;
    }

    public String[] getArguments() {
        return args;
    }

   /* public void setCommands(CommandShallow[] commands) {
        this.commands = commands;
    }

    public CommandShallow[] getCommands() {
        return commands;
    }*/

    public void setStudyGroup(String[] splitted) {
        Color col = null;
        switch(splitted[4]) {
            case "RED":
                col = Color.RED;
                break;
            case "BLACK":
                col = Color.BLACK;
                break;
            case "BLUE":
                col = Color.BLUE;
                break;
            case "YELLOW":
                col = Color.YELLOW;
                break;
            case "BROWN":
                col = Color.BROWN;
                break;
            default:
                throw new IllegalArgumentException("Error! Unknown color \"" + splitted[4] + "\"");
        }

        FormOfEducation education = null;
        switch(splitted[8]) {
            case "DISTANCE_EDUCATION":
                education = FormOfEducation.DISTANCE_EDUCATION;
                break;
            case "FULL_TIME_EDUCATION":
                education = FormOfEducation.FULL_TIME_EDUCATION;
                break;
            case "EVENING_CLASSES":
                education = FormOfEducation.EVENING_CLASSES;
                break;
            default:
                throw new IllegalArgumentException("Error! Unknown form of education \"" + splitted[8] + "\"");
        }

        Location location = new Location(Integer.parseInt(splitted[10]), Integer.parseInt(splitted[11]), splitted[12]);
        if (!location.validate()) {
            throw new IllegalArgumentException("Error! Invalid location");
        }

        Person groupAdmin = new Person(splitted[13], splitted[14], null, location);
        if (!groupAdmin.validate()) {
            throw new IllegalArgumentException("Error! Invalid group admin");
        }

        this.studyGroup = new StudyGroup(
                splitted[0],
                new Coordinates(Integer.parseInt(splitted[1]), Long.parseLong(splitted[2])),
                Long.parseLong(splitted[3]),
                Integer.parseInt(splitted[5]),
                Long.parseLong(splitted[6]),
                col,
                education
        );
    }

    public StudyGroup getStudyGroup() {
        return studyGroup;
    }
}
