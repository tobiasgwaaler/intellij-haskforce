package com.haskforce.parsing.jsonParser;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.haskforce.parsing.srcExtsDatatypes.*;

import java.lang.reflect.Type;

/**
 * Deserializes types.
 */
public class TypeTopTypeDeserializer implements JsonDeserializer<TypeTopType> {
    public TypeTopType deserialize(JsonElement jsonElement, Type type,
                                   JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject objType = jsonElement.getAsJsonObject();
        JsonArray stuff;
        if ((stuff = objType.getAsJsonArray("TyApp")) != null) {
            TyApp tyApp = new TyApp();
            tyApp.srcInfoSpan = jsonDeserializationContext.deserialize(stuff.get(0), SrcInfoSpan.class);
            tyApp.t1 = jsonDeserializationContext.deserialize(stuff.get(1), TypeTopType.class);
            tyApp.t2 = jsonDeserializationContext.deserialize(stuff.get(2), TypeTopType.class);
            return tyApp;
        } else if ((stuff = objType.getAsJsonArray("TyCon")) != null) {
            TyCon tyCon = new TyCon();
            tyCon.srcInfoSpan = jsonDeserializationContext.deserialize(stuff.get(0), SrcInfoSpan.class);
            tyCon.qName = jsonDeserializationContext.deserialize(stuff.get(1), QNameTopType.class);
            return tyCon;
        } else if ((stuff = objType.getAsJsonArray("TyVar")) != null) {
            TyVar tyVar = new TyVar();
            tyVar.srcInfoSpan = jsonDeserializationContext.deserialize(stuff.get(0), SrcInfoSpan.class);
            tyVar.name = jsonDeserializationContext.deserialize(stuff.get(1), NameTopType.class);
            return tyVar;
        } else if ((stuff = objType.getAsJsonArray("TyFun")) != null) {
            TyFun tyFun = new TyFun();
            tyFun.srcInfoSpan = jsonDeserializationContext.deserialize(stuff.get(0), SrcInfoSpan.class);
            tyFun.t1 = jsonDeserializationContext.deserialize(stuff.get(1), TypeTopType.class);
            tyFun.t2 = jsonDeserializationContext.deserialize(stuff.get(2), TypeTopType.class);
            return tyFun;
        } else if ((stuff = objType.getAsJsonArray("TyList")) != null) {
            TyList tyList = new TyList();
            tyList.srcInfoSpan = jsonDeserializationContext.deserialize(stuff.get(0), SrcInfoSpan.class);
            tyList.t = jsonDeserializationContext.deserialize(stuff.get(1), TypeTopType.class);
            return tyList;
        } else if ((stuff = objType.getAsJsonArray("TyForall")) != null) {
            TyForall tyForall = new TyForall();
            tyForall.srcInfoSpan = jsonDeserializationContext.deserialize(stuff.get(0), SrcInfoSpan.class);
            tyForall.tyVarBinds = jsonDeserializationContext.deserialize(stuff.get(1), TyVarBindTopType[].class);
            tyForall.context = jsonDeserializationContext.deserialize(stuff.get(2), ContextTopType.class);
            tyForall.type = jsonDeserializationContext.deserialize(stuff.get(3), TypeTopType.class);
            return tyForall;
        }
        // TODO: Rest of TypeTopType
        throw new JsonParseException("Unexpected object type: " + objType.toString());
    }
}
