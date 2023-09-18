package net.deechael.designer.data;

public interface DataTableCreator<T extends DataHolder> {

    DataTableCreator<T> withParameter(String key, String value);

    DataTableCreator<T> withParameter(String value);

    DataTable<T> create();

    Database database();

}
