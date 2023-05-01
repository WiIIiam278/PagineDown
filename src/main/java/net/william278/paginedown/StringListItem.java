package net.william278.paginedown;

import org.jetbrains.annotations.NotNull;

public class StringListItem implements ListItem {

    private final String item;

    public StringListItem(String item) {
        this.item = item;
    }

    @Override
    @NotNull
    public String toItemString() {
        return item;
    }
}
