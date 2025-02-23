package com.laundrygo.shorturl.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class Base62EncoderTest {

//    wsU74SHAfbk659xzgRYpyaZqXJdFtVGjBT0E2ilcCoePKnvMNhWOIQurD13mL8

    @Test
    @DisplayName("0을 인코딩하면 w이어야 한다.")
    public void encodeZero() {
        Base62Encoder encoder = new Base62Encoder();
        assertThat(encoder.encode(0)).isEqualTo("w");
    }

    @Test
    @DisplayName("10을 인코딩하면 k이어야 한다.")
    public void encodeTen() {
        Base62Encoder encoder = new Base62Encoder();
        assertThat(encoder.encode(10)).isEqualTo("k");
    }

    @Test
    @DisplayName("61를 인코딩하면 8이어야 한다.")
    public void encodeSixtyOne() {
        Base62Encoder encoder = new Base62Encoder();
        assertThat(encoder.encode(61)).isEqualTo("8");
    }


    @Test
    @DisplayName("62를 인코딩하면 ws이어야 한다.")
    public void encodeSixtyTwo() {
        Base62Encoder encoder = new Base62Encoder();
        assertThat(encoder.encode(62)).isEqualTo("ws");
    }

}