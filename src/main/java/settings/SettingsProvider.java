package settings;

import java.util.HashMap;
import java.util.Map;

public class SettingsProvider {
    Map<SearchMethodType, Map<MaterialFunctionType, MaterialFunctionSettings>>
    materialFunctionSettingsMap =
            new HashMap<>();

    public SettingsProvider() {
        init();
    }

    private void init() {
        initSimple();
        initCompressed();
    }

    private void initSimple() {
        HashMap<MaterialFunctionType, MaterialFunctionSettings> simpleSettingsMap = new HashMap<>();
        simpleSettingsMap.put(MaterialFunctionType.SIMPLE, new SimpleMaterialFunctionSettings());
        materialFunctionSettingsMap.put(SearchMethodType.HOOKE, simpleSettingsMap);

        SimpleMaterialFunctionSettings powellSimpleSettings = new SimpleMaterialFunctionSettings();
        powellSimpleSettings.setStartingKValue(140.0);
        powellSimpleSettings.setStartingNValue(10.0);
        HashMap<MaterialFunctionType, MaterialFunctionSettings> powellSimpleSettingsMap = new HashMap<>();
        powellSimpleSettingsMap.put(MaterialFunctionType.SIMPLE, powellSimpleSettings);
        materialFunctionSettingsMap.put(SearchMethodType.POWELL,powellSimpleSettingsMap);
    }

    private void initCompressed() {
        HashMap<MaterialFunctionType, MaterialFunctionSettings> compressedSettings = new HashMap<>();
        compressedSettings.put(MaterialFunctionType.COMPRESSED, new
                CompressedMaterialFunctionSettings());
        materialFunctionSettingsMap.get(SearchMethodType.HOOKE).put(MaterialFunctionType.COMPRESSED, new
                CompressedMaterialFunctionSettings());
        materialFunctionSettingsMap.get(SearchMethodType.POWELL).put(MaterialFunctionType.COMPRESSED, new
                CompressedMaterialFunctionSettings());

//        materialFunctionSettingsMap.put(SearchMethodType.HOOKE, compressedSettings);
//        materialFunctionSettingsMap.put(SearchMethodType.POWELL, compressedSettings);
    }

//    public Map<SearchMethodType, Map<MaterialFunctionType, ? extends MaterialFunctionSettings>> getMaterialFunctionSettingsMap() {
//        return materialFunctionSettingsMap;
//    }
//
//    public void setMaterialFunctionSettingsMap(Map<SearchMethodType, Map<MaterialFunctionType, ? extends MaterialFunctionSettings>> materialFunctionSettingsMap) {
//        this.materialFunctionSettingsMap = materialFunctionSettingsMap;
//    }


    public Map<SearchMethodType, Map<MaterialFunctionType, MaterialFunctionSettings>> getMaterialFunctionSettingsMap() {
        return materialFunctionSettingsMap;
    }

    public void setMaterialFunctionSettingsMap(Map<SearchMethodType, Map<MaterialFunctionType, MaterialFunctionSettings>> materialFunctionSettingsMap) {
        this.materialFunctionSettingsMap = materialFunctionSettingsMap;
    }
}
