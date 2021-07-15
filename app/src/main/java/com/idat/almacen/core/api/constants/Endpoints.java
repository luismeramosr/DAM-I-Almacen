package com.idat.almacen.core.api.constants;

public final class Endpoints {
    // Auth endpoints
    public static final String login = "auth/login";

    // User endpoints
    public static final String operators = "user/operator";
    public static final String updateUser = "user";
    public static final String findUserById = "user/{id}";
    public static final String deleteUser = "user/{id}";
    public static final String saveUser = "user";

    // Inventory endpoints
    public static final String findAllItems = "item";
    public static final String addItem = "item";
    public static final String updateItem = "item";
    public static final String deleteItem = "item/{barcode}";
}
