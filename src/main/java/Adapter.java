
import com.google.gson.*;
import java.lang.reflect.Type;

public class Adapter implements JsonSerializer<Adaptable>, JsonDeserializer<Adaptable> {
        @Override
        public JsonElement serialize(Adaptable src, Type typeOfSrc, JsonSerializationContext context) {
            JsonObject result = new JsonObject();
            result.add("type", new JsonPrimitive(src.getClass().getSimpleName()));
            result.add("properties", context.serialize(src, src.getClass()));

            return result;
        }

        @Override
        public Adaptable deserialize(JsonElement json, Type typeOfSrc, JsonDeserializationContext context) throws JsonParseException {
            JsonObject jsonObject = json.getAsJsonObject();
            String type = jsonObject.get("type").getAsString();
            JsonElement element = jsonObject.get("properties");

            try {
                return context.deserialize(element, Class.forName(type));
            } catch (ClassNotFoundException cnfe) {
                throw new JsonParseException("Unknown element type: " + type, cnfe);
            }
        }
}

//hola