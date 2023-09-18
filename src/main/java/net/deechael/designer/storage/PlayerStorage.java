package net.deechael.designer.storage;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.deechael.designer.data.DataHolder;
import net.deechael.designer.data.DataProperty;
import net.deechael.designer.data.annotation.mysql.*;
import net.deechael.designer.data.annotation.share.Comment;
import net.deechael.designer.data.mysql.MysqlType;

import java.util.UUID;

public class PlayerStorage implements DataHolder {

    @MysqlObject(type = MysqlType.INT)
    @PrimaryKey
    @AutoIncrement
    @Immutable
    @NotNull
    @Comment("The id of the user")
    public DataProperty<Integer> uid;

    @MysqlObject(type = MysqlType.TEXT)
    public DataProperty<String> name;

    @MysqlObject(type = MysqlType.TEXT)
    public DataProperty<UUID> uuid;

    @MysqlObject(type = MysqlType.INT)
    public DataProperty<Integer> exp;

    @MysqlObject(type = MysqlType.TEXT)
    public DataProperty<String> backpack;

    @MysqlObject(type = MysqlType.TEXT)
    public DataProperty<String> mails;

    @MysqlObject(type = MysqlType.TEXT)
    public DataProperty<String> firstJoin;

    @MysqlObject(type = MysqlType.TEXT)
    public DataProperty<String> lastJoin;

    public JsonObject getBackpack() {
        String backpack = this.backpack.get();
        return JsonParser.parseString(backpack == null ? "{}" : backpack).getAsJsonObject();
    }

    public JsonObject getMails() {
        String mails = this.mails.get();
        return JsonParser.parseString(mails == null ? "{}" : mails).getAsJsonObject();
    }

}
