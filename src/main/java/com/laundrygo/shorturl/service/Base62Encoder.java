package com.laundrygo.shorturl.service;


import org.springframework.stereotype.Component;

@Component
public class Base62Encoder implements Encoder {
    // 예측을 어렵게 하기 위해62진수를 랜덤으로 섞음
    private static final String BASE62_ALPHABET = "wsU74SHAfbk659xzgRYpyaZqXJdFtVGjBT0E2ilcCoePKnvMNhWOIQurD13mL8";
    private static final int BASE = 62;

    public String encode(long value) {
        return convertBase62(value);
    }

    private String convertBase62(long value) {
        StringBuilder encoded = new StringBuilder();

        while (value > 0) {
            encoded.append(convertBase62Word(value));
            value /= BASE;
        }

        return encoded.toString();
    }

    private char convertBase62Word(long value) {
        return BASE62_ALPHABET.charAt(getBase62Idx(value));
    }

    private int getBase62Idx(long value) {
        return (int) (value % BASE);
    }
}
