package org.securedata.helper.maskingstrategy;

import java.util.HashMap;
import java.util.Map;

public class MaskingRegistry {

private static final Map<String, MaskingStrategy> strategies = new HashMap<>();
private static final MaskingStrategy DEFAULT_STRATEGY = MaskingStrategies::maskDefault;

static {
    // Register default strategies
    registerMaskingStrategy("DEFAULT", DEFAULT_STRATEGY);
    registerMaskingStrategy("EMAIL", MaskingStrategies::emailMask);
    registerMaskingStrategy("PHONE", MaskingStrategies::phoneMask);
    registerMaskingStrategy("CARD", MaskingStrategies::maskCard);
}

public static void registerMaskingStrategy(String key, MaskingStrategy strategy) {
    strategies.put(key.toUpperCase(), strategy);
}

public static MaskingStrategy getStrategy(String key) {

    return strategies.getOrDefault(key.toUpperCase(), DEFAULT_STRATEGY);
}

public static boolean validateStrategy(String key){
    return strategies.containsKey(key.toUpperCase());
}
}