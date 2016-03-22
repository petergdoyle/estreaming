/*
 */
package com.cleverfishsoftware.spring.xd.jms.sender;

import java.nio.ByteBuffer;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 * @author peter
 */
public class OnesAndZerosPayloadGenerator implements PayloadGenerator {

    private static ByteBuffer BUFFER;

    public OnesAndZerosPayloadGenerator() {
    }

    @Override
    public String[] getPayload(final int size) {
        if (BUFFER == null) {
            BUFFER = ByteBuffer.allocateDirect(size);
            fillBuffer(BUFFER);
        } else if (size <= BUFFER.capacity()) {
        } else {
            synchronized (this) {
                BUFFER = resizeBuffer(BUFFER, size);
                fillBuffer(BUFFER);
            }
        }
        byte[] data = extractBytes(BUFFER, size);
        return new String[]{new String(data)};
    }

    private byte[] extractBytes(final ByteBuffer buffer, int size) {
        byte[] data = new byte[size];
        ByteBuffer duplicate = buffer.duplicate();
        duplicate.rewind();
        duplicate.get(data, 0, size);
        return data;
    }

    private void fillBuffer(final ByteBuffer buffer) {
        boolean on = false;
        byte[] one = "1".getBytes();
        byte[] zero = "0".getBytes();
        while (buffer.hasRemaining()) {
            if (on) {
                buffer.put(one);
            } else {
                buffer.put(zero);
            }
            on = !on;
        }
    }

    public static ByteBuffer resizeBuffer(final ByteBuffer original, final int size) {
        ByteBuffer resizedBuffer = ByteBuffer.allocate(size);
        original.rewind();//copy from the beginning
        resizedBuffer.put(original);
        //resizedBuffer.flip(); // don't flip it, as it will be filled from the current position rather than starting from zero
        return resizedBuffer;
    }

    public static void main(String[] args) {
        int size = 0;
        if (args.length > 0) {
            String argValue = args[0];
            if (argValue != null && argValue.length() > 0) {
                size = Integer.parseInt(argValue);
            }
        }
        OnesAndZerosPayloadGenerator payloadGenerator = new OnesAndZerosPayloadGenerator();
        String[] payloads = payloadGenerator.getPayload(size);
        for (String payload : payloads) {
            System.out.println("payload is " + payload.length() + " bytes long");
            System.out.println(payload);
        }
        payloads = payloadGenerator.getPayload(size);
        for (String payload : payloads) {
            System.out.println("payload is " + payload.length() + " bytes long");
            System.out.println(payload);
        }
        payloads = payloadGenerator.getPayload(size * 2);
        for (String payload : payloads) {
            System.out.println("payload is " + payload.length() + " bytes long");
            System.out.println(payload);
        }

        String edifact_request = " UIB+UNOA:4+020E05++++1P+VV+020502:1848’      \n"
                + "UIH+AVLREQ:D:97B::UN++020E05+:F+020502:1848’ \n"
                + "MSD+2:36’ \n"
                + "ORG+1P:HDQ+99004802:RAZ+ATL:GA++1+US+TG’ \n"
                + "	TFF+:::DY’ \n"
                + "	TVL+020522:0900:020523:0900+MCI+VV’  \n"
                + "UIT++6’ \n"
                + "UIZ+020E05’ ";

        System.out.println("edifact request length: " + edifact_request.replace(" ", "").length());
        
        String edifact_response = "UIB+UNOA:4+020E05:::XSNO12520001++++VV+1P+020502:1848’ \n"
                + "UIH+AVLRSP:D:97B::UN++020E05:::XSNO12520001++020502:1848’ \n"
                + "MSD+2:36+1’ \n"
                + "PLI+J02:KANSAS CTY MO APT*176:MCIT01*J03:MCIT01’ \n"
                + "	PRD+:CPAR’ \n"
                + "		PDT++:5’   \n"
                + "		RTC+ZYX’ \n"
                + "			TFF+:59.90:USD:DY:1:::75.30++33:::100*31:.25*8:59.90*79:::100*9:19.97*81:::33’ \n"
                + "	PRD+:ECAR’ \n"
                + "		PDT++:5’ \n"
                + "		RTC+WWW’\n"
                + "			TFF+:51.80:USD:DY:1:::64.20++33::UNL’ \n"
                + "	PRD+:CCAR’  \n"
                + "		PDT++:5’  \n"
                + "		RTC+TTT’  \n"
                + "			TFF+:52.80:USD:DY:1:::65.20++33::UNL’ \n"
                + "	PRD+:ICAR’ \n"
                + "		PDT++:5’ \n"
                + "		RTC+PPP’ \n"
                + "			TFF+:56.80:USD:DY:1:::70.44++33::UNL’ \n"
                + "	PRD+:STAR’ \n"
                + "		PDT++:5’ \n"
                + "		RTC+MMM’ \n"
                + "			TFF+:59.99:USD:DY:1:::76.45++33::UNL’\n"
                + "	PRD+:SCAR’  \n"
                + "		PDT++:5’\n"
                + "		RTC+JJJ’ \n"
                + "			TFF+:59.80:USD:DY:1:::76.01++33::UNL’  \n"
                + "	PRD+:FCAR’ \n"
                + "		PDT++:5’ \n"
                + "		RTC+LLL’   \n"
                + "			TFF+:59.80:USD:DY:1:::76.01++33::UNL’ \n"
                + "	PRD+:PCAR’ \n"
                + "		PDT++:5’ \n"
                + "		RTC+OOO’ \n"
                + "			TFF+:68.80:USD:DY:1:::78.20++33::UNL’ \n"
                + "	PRD+:LCAR’\n"
                + "		PDT++:5’ \n"
                + "		RTC+KK’\n"
                + "			TFF+:68.80:USD:DY:1:::77.30++33::UNL’\n"
                + "	PRD+:MVAR’\n"
                + "		PDT++:5’ \n"
                + "		RTC+QQ’\n"
                + "			TFF+:64.80:USD:DY:1:::79.40++33::UNL’ \n"
                + "	PRD+:IFAR’\n"
                + "		PDT++:5’ \n"
                + "		RTC+AB’ \n"
                + "			TFF+:59.80:USD:DY:1:::82.85++33::UNL’ \n"
                + "	PRD+:XPAR’ \n"
                + "		PDT++:5’\n"
                + "		RTC+ZZ’\n"
                + "			TFF+:69.80:USD:DY:1:::99.99++33::UNL’ \n"
                + "UIT++52’ \n"
                + "UIZ+020E05:::XSNO12520001’";

        System.out.println("edifact response length: " + edifact_response.replace(" ", "").length());
    }

}
