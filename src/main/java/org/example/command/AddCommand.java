package org.example.command;

import org.example.exception.InvalidArgsException;
import org.example.managers.CollectionManager;
import org.example.managers.CommandManager;
import org.example.models.StudyGroup;
import org.example.parser.SGParser;
import org.example.utils.IOProvider;

public class AddCommand extends Command {
   /* public AddCommand() {
        super("add", "добавить новый элемент в коллекцию");
    }*/
    public AddCommand() {
        super("add {element}", "добавить новый элемент в коллекцию",1, new String[]{
                "имя группы ",
                "Координата Х ",
                " Координата Y ",
                " Количество студентов ",
                "Кол-во исключенных студентов ",
                " Кол-во тех, кто должен быть исключен ",
                "Форма обучения ( доступные варианты - DISTANCE_EDUCATION, FULL_TIME_EDUCATION, EVENING_CLASSES) ",
                "Имя админа группы ",
                "id паспорта",
                "Цвет волос ( из доступных - RED,BLACK,BLUE, YELLOW, BROWN) ",
                "Локация админа, координата Х " ,
                "Локация админа, координата Y" ,
                        "Имя локаци"});
    }



    @Override
    public void execute(String[] args) throws InvalidArgsException {}
    public Response execute(String[] args, Integer stacksize, StudyGroup studyGroup, CommandManager commandmanager, CollectionManager collection) {
        studyGroup.setId();
        collection.push(studyGroup);
        return new Response(new String[]{"Добавлена группа: "+studyGroup.getName()});
    }

}
