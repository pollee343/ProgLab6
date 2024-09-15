package org.example.utils.parser.abstracts.argument;

import lombok.Getter;
import org.example.exceptions.collection.InvalidArgumentException;
import org.example.network.model.ArgumentType;


@Getter
public abstract class ArgumentParser {
    private final ArgumentType argumentType;

    public ArgumentParser(ArgumentType argumentType) {
        this.argumentType = argumentType;
    }

    public abstract boolean parse(String argument) throws InvalidArgumentException;
}
