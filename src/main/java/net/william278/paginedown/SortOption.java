package net.william278.paginedown;

import org.jetbrains.annotations.NotNull;

import java.util.*;

public class SortOption<I extends ListItem> {
    private final String name;
    private final Comparator<I> sortFunction;
    public static final SortOption<? extends ListItem> NAME = new SortOption<>("name", Comparator.comparing(ListItem::toItemString));

    public SortOption(@NotNull String name, @NotNull Comparator<I> sortFunction) {
        this.name = name;
        this.sortFunction = sortFunction;
    }

    @NotNull
    public List<I> sort(Collection<I> items, boolean ascending) {
        final List<I> sortedItems = new ArrayList<>(items);
        sortedItems.sort(sortFunction);
        if (!ascending) {
            Collections.reverse(sortedItems);
        }
        return sortedItems;
    }

    @NotNull
    public String getName() {
        return name;
    }

    public boolean matchesName(@NotNull String name) {
        return getName().equalsIgnoreCase(name);
    }

}
