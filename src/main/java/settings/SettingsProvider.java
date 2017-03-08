package settings;

import optimization.nonlinear.unconstrained.core.SearchMethod;
import optimization.nonlinear.unconstrained.core.materialFunctions.MaterialFunction;

import java.util.HashMap;
import java.util.Map;

public class SettingsProvider {
    Map<SearchMethod,Map<MaterialFunction,MaterialFunctionSettings>> materialFunctionSettingsMap = new HashMap<>();
}
