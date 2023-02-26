package ru.alex.braim.utils.decoder;

public interface Decoder {
    AuthData decode(String code);
}
