/*
 */
package com.cleverfishsoftware.spring.xd.jms.sender.generator.car;

import com.cleverfishsoftware.spring.xd.jms.sender.PayloadGenerator;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 * @author peter
 */
public class EdifactCarPayloadGenerator implements PayloadGenerator {

    private static final String EDIFACT_REQUEST = "UIB+UNOA:4+020E05++++1P+VV+020502:1848’      \n"
            + "UIH+AVLREQ:D:97B::UN++020E05+:F+020502:1848’ \n"
            + "MSD+2:36’ \n"
            + "ORG+1P:HDQ+99004802:RAZ+ATL:GA++1+US+TG’ \n"
            + "	TFF+:::DY’ \n"
            + "	TVL+020522:0900:020523:0900+MCI+VV’  \n"
            + "UIT++6’ \n"
            + "UIZ+020E05’ ";

    private static final String EDIFACT_RESPONSE = "UIB+UNOA:4+020E05:::XSNO12520001++++VV+1P+020502:1848’ \n"
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

    private final AtomicInteger count = new AtomicInteger(0);

    @Override
    public String[] getPayload(int size) {
        String[] payload;
        String identifier = String.format("%010d", count.incrementAndGet()).concat("\n");
        payload = new String[]{identifier.concat(EdifactCarPayloadGenerator.EDIFACT_RESPONSE)};
        return payload;
    }

}
