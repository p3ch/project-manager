package layers.domain;

import java.math.BigDecimal;

/**
 * Policy
 */
public interface RebateCalculator {

    BigDecimal calculateRebate(BigDecimal stockPrise);
}