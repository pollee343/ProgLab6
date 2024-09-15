package org.example.network;

import org.example.model.data.IdCounter;
import org.example.network.dto.Response;
import org.example.network.model.ArgumentType;
import org.example.utils.parser.avaliable.command.CommandParser;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

public class InitialResponseSender {
    public static Map<String, ArgumentType> getAvailableCommands() throws IOException, ClassNotFoundException {
        Response initResponse = ClientTCP.receiveResponse();

        String initData = initResponse.data();
        initData = initData.substring(1, initData.length() - 1);

        List<String> listOfNames = Arrays.asList(initData.split(", "));
        return listOfNames.stream()
                .map(CommandParser::parseCommand)   // Преобразуем каждую строку в Map.Entry
                .collect(Collectors.toMap(
                        Map.Entry::getKey,          // Ключ - имя команды
                        Map.Entry::getValue,        // Значение - тип аргумента
                        (e1, e2) -> e1,             // Если встречаются одинаковые ключи, оставляем первый
                        HashMap::new                // Создание новой HashMap
                ));
    }

    public static void setNexIdCounter() throws IOException, ClassNotFoundException {
        Response initResponse = ClientTCP.receiveResponse();
        IdCounter.setNextId(new AtomicLong(Long.parseLong(initResponse.data())));
    }
}
