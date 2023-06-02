import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/*public class MapAdapter implements  JsonDeserializer<Object> {

    public Object deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context){
        if(json.isJsonNull()) {
            return null;
        }

        if(json.isJsonPrimitive() == false && json.isJsonArray() == false){
            return handleMap(json.getAsJsonObject(), context);
        }
        return null;
    }

    private Object handleMap(JsonObject json, JsonDeserializationContext context) {
        Map<Integer, Reminder> map = new HashMap<Integer, Reminder>();
        for(Map.Entry<String, JsonElement> entry : json.entrySet())
            map.put(Integer.parseInt(entry.getKey()), context.deserialize(entry.getValue(), Object.class));
        return map;
    }
}*/

import com.google.gson.*;
import java.lang.reflect.Type;
import java.util.HashMap;

public class MapAdapter implements JsonDeserializer<HashMap<?, ?>> {


    @Override
    public HashMap<?, ?> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        Type hashMapType = new TypeToken<HashMap<?, ?>>(){}.getType();
        HashMap<?, ?> hashMap = context.deserialize(json, hashMapType);
        return hashMap;
    }


}
