package ru.alex.braim.utils.decoder.decoderImpl;

import ru.alex.braim.utils.decoder.AuthData;
import ru.alex.braim.utils.decoder.Decoder;

import java.util.Base64;

public class Base64Decoder implements Decoder {
    @Override
    public AuthData decode(String code) {
        String withoutBasic = code.split(" ")[1];

        byte[] decode = Base64.getDecoder().decode(withoutBasic);
        String[] emailAndPassword = new String(decode).split(":");

        return new AuthData(emailAndPassword[0], emailAndPassword[1]);
    }
}
