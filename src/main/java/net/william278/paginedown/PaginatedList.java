/*
 * This file is part of PagineDown, licensed under the Apache License 2.0.
 *
 *  Copyright (c) William278 <will27528@gmail.com>
 *  Copyright (c) contributors
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package net.william278.paginedown;


import de.themoep.minedown.adventure.MineDown;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.stream.Collectors;

/**
 * A class used to generate {@link MineDown} formatted chat menus of paginated list items.
 * <p>
 * A paginated list contains the following elements:
 * <ul>
 *     <li>A header, by default identifying the items listed on the current page</li>
 *     <li>The items, separated by new lines by default</li>
 *     <li>A footer, by default containing page navigation buttons and quick-jump page numbers</li>
 * </ul>
 * You can supply {@link ListOptions} to modify the format of each element, including shortcuts for modifying a theme color.
 * Once you have created a {@link PaginatedList} object using the static {@code get(items, options)},
 * you can easily generate a {@link MineDown} formatted chat menu for a given page using the {@code getPage()} methods.
 *
 * @author William278
 * @see ListOptions
 */
@SuppressWarnings("unused")
public class PaginatedList<I extends ListItem> {

    /**
     * {@link ListOptions} to be used for generating the list
     */
    @NotNull
    private final ListOptions<I> options;

    /**
     * A list of items to be paginated; can be MineDown formatted
     */
    @NotNull
    private final List<I> items;

    /**
     * Private constructor used by {@code #get(List, ListOptions)}
     *
     * @param items   a list of items to be paginated
     * @param options {@link ListOptions} to be used for generating list pages
     */
    private PaginatedList(@NotNull Collection<I> items, @NotNull ListOptions<I> options) {
        this.items = new ArrayList<>(items);
        this.options = options;
    }

    /**
     * Create a new {@link PaginatedList} from a {@link List} of items
     *
     * @param items The {@link List} of items to paginate
     * @return A new {@link PaginatedList}
     */
    @SuppressWarnings("unchecked")
    @NotNull
    public static <I extends ListItem> PaginatedList<I> of(@NotNull Collection<I> items) {
        return of(items, ListOptions.builder().build());
    }

    /**
     * Create a new {@link PaginatedList} from a {@link List} of items
     *
     * @param items   The {@link List} of items to paginate
     * @param options The {@link ListOptions} to use for generating list pages
     * @return A new {@link PaginatedList}
     */
    @NotNull
    public static <I extends ListItem> PaginatedList<I> of(@NotNull Collection<I> items, @NotNull ListOptions<I> options) {
        return new PaginatedList<>(items, options);
    }


    /**
     * Returns a {@link MineDown} formatted message to be sent to a player of the paginated list for the nearest specified page that exists
     * <p>List formats and options from the {@link ListOptions} are applied to generate list pages.
     * </p>{@code #getNearestValidPage()} will return the nearest valid page (i.e. values below 0 will be set to 0, values above the maximum page will be set to the maximum page)
     *
     * @param page The page number to get
     * @return A {@link MineDown} object, for formatting the list
     */
    @NotNull
    public MineDown getNearestValidPage(final int page, boolean ascending, @NotNull SortOption<I> option) {
        return getPage(Math.max(1, Math.min(getTotalPages(), page)), ascending, option);
    }

    @SuppressWarnings("unchecked")
    @NotNull
    public MineDown getNearestValidPage(final int page, boolean ascending) {
        return getNearestValidPage(page, ascending, (SortOption<I>) SortOption.NAME);
    }

    @NotNull
    public MineDown getNearestValidPage(final int page) {
        return getNearestValidPage(page, true);
    }

    /**
     * Returns a {@link MineDown} formatted message to be sent to a player of the paginated list for the specified page
     * <p>List formats and options from the {@link ListOptions} are applied to generate the list.
     *
     * @param page The page number to get
     * @return A {@link MineDown} object, for formatting the list
     * @throws PaginationException If the page number is out of bounds
     */
    @NotNull
    public MineDown getPage(final int page, boolean ascending, @NotNull SortOption<I> option) throws PaginationException {
        return new MineDown(getRawPage(page, ascending, option));
    }

    /**
     * Returns a {@link MineDown} formatted message to be sent to a player of the paginated list for the specified page
     * <p>List formats and options from the {@link ListOptions} are applied to generate the list.
     *
     * @param page The page number to get
     * @return A {@link MineDown} object, for formatting the list
     * @throws PaginationException If the page number is out of bounds
     */
    @SuppressWarnings("unchecked")
    @NotNull
    public MineDown getPage(final int page, boolean ascending) throws PaginationException {
        return getPage(page, ascending, (SortOption<I>) SortOption.NAME);
    }

    /**
     * Returns a {@link MineDown} formatted message to be sent to a player of the paginated list for the specified page
     * <p>List formats and options from the {@link ListOptions} are applied to generate the list.
     *
     * @param page The page number to get
     * @return A {@link MineDown} object, for formatting the list
     * @throws PaginationException If the page number is out of bounds
     */
    @NotNull
    public MineDown getPage(final int page) throws PaginationException {
        return getPage(page, true);
    }

    /**
     * Generates a raw string of pre-{@link MineDown}-formatted text that when formatted will create the page menu.
     * <p>List formats and options from the {@link ListOptions} are applied to generate the list.
     *
     * @param page The page number to get
     * @return A raw string of pre-MineDown-formatted text, representing the page menu.
     * @throws PaginationException If the page number is out of bounds
     */
    @NotNull
    public String getRawPage(final int page, final boolean ascending, final SortOption<I> option) throws PaginationException {
        if (page < 1) {
            throw new PaginationException("Page index must be >= 1");
        }
        if (page > getTotalPages()) {
            throw new PaginationException("Page index must be <= the total number of pages (" + getTotalPages() + ")");
        }

        final StringJoiner menuJoiner = new StringJoiner("\n");
        if (!options.headerFormat.isBlank()) {
            menuJoiner.add(formatPageString(options.headerFormat, page));
            if (options.spaceAfterHeader) {
                menuJoiner.add("");
            }
        }

        final List<I> items = option.sort(this.items, ascending);
        if (options.escapeItemsMineDown) {
            menuJoiner.add(getItemsForPage(items, page).stream().map(MineDown::escape)
                    .collect(Collectors.joining(options.itemSeparator)));
        } else {
            menuJoiner.add(String.join(options.itemSeparator, getItemsForPage(items, page)));
        }

        if (!options.footerFormat.isBlank()) {
            if (options.spaceBeforeFooter) {
                menuJoiner.add("");
            }
            menuJoiner.add(formatPageString(options.footerFormat, page));
        }
        return menuJoiner.toString();
    }

    @SuppressWarnings("unchecked")
    @NotNull
    public String getRawPage(final int page, final boolean ascending) {
        return getRawPage(page, ascending, (SortOption<I>) SortOption.NAME);
    }

    @NotNull
    public String getRawPage(final int page) {
        return getRawPage(page, true);
    }

    /**
     * Returns the total number of pages
     *
     * @return The total number of pages
     */
    public int getTotalPages() {
        return (int) Math.ceil((double) items.size() / options.itemsPerPage);
    }

    /**
     * Returns the items to be displayed on the specified page
     *
     * @param items The items to paginate
     * @param page  The page number to get
     * @return The sub-list of items to be shown on a given page
     */
    @NotNull
    private List<String> getItemsForPage(@NotNull List<I> items, int page) {
        return items.subList((page - 1) * options.itemsPerPage, Math.min(items.size(), page * options.itemsPerPage))
                .stream().map(ListItem::toItemString)
                .collect(Collectors.toList());
    }

    /**
     * Formats a ListOption placeholder format with values
     *
     * @param format The format string
     * @param page   The page number
     * @return The formatted page string
     */
    @NotNull
    private String formatPageString(@NotNull String format, int page) {
        final StringBuilder convertedFormat = new StringBuilder();
        StringBuilder currentPlaceholder = new StringBuilder();
        boolean readingPlaceholder = false;
        for (char c : format.toCharArray()) {
            if (c == '%') {
                if (readingPlaceholder) {
                    switch (currentPlaceholder.toString().toLowerCase()) {
                        case "topic" -> convertedFormat.append(formatPageString(options.topic, page));
                        case "color" ->
                                convertedFormat.append(String.format("#%02x%02x%02x", options.themeColor.getRed(), options.themeColor.getGreen(), options.themeColor.getBlue()));
                        case "first_item_on_page_index" ->
                                convertedFormat.append(((page - 1) * options.itemsPerPage) + 1);
                        case "last_item_on_page_index" ->
                                convertedFormat.append(((page - 1) * options.itemsPerPage) + getItemsForPage(items, page).size());
                        case "total_items" -> convertedFormat.append(items.size());
                        case "current_page" -> convertedFormat.append(page);
                        case "total_pages" -> convertedFormat.append(getTotalPages());
                        case "previous_page_button" -> {
                            if (page > 1) {
                                convertedFormat.append(formatPageString(options.previousButtonFormat, page));
                            }
                        }
                        case "next_page_button" -> {
                            if (page < getTotalPages()) {
                                convertedFormat.append(formatPageString(options.nextButtonFormat, page));
                            }
                        }
                        case "next_page_index" -> convertedFormat.append(page + 1);
                        case "previous_page_index" -> convertedFormat.append(page - 1);
                        case "command" -> convertedFormat.append(options.command);
                        case "page_jumpers" -> {
                            if (getTotalPages() > 2) {
                                convertedFormat.append(formatPageString(options.pageJumpersFormat, page));
                            }
                        }
                        case "page_jump_buttons" -> {
                            convertedFormat.append(getPageJumperButtons(page));
                        }
                    }
                } else {
                    currentPlaceholder = new StringBuilder();
                }
                readingPlaceholder = !readingPlaceholder;
                continue;
            }
            if (readingPlaceholder) {
                currentPlaceholder.append(c);
            } else {
                convertedFormat.append(c);
            }
        }
        return convertedFormat.toString();
    }

    /**
     * Formats a page jumper format with values
     *
     * @param page The page number of the jumper to format
     * @return The formatted page jumper
     */
    @NotNull
    private String formatPageJumper(final int page) {
        return formatPageString(options.pageJumperPageFormat.replaceAll("%target_page_index%",
                Integer.toString(page)), page);
    }

    @NotNull
    protected String getPageJumperButtons(final int page) {
        final StringJoiner pageGroups = new StringJoiner(options.pageJumperGroupSeparator);
        StringJoiner pages = new StringJoiner(options.pageJumperPageSeparator);
        int lastPage = 1;
        for (int i = 1; i <= getTotalPages(); i++) {
            if (i <= options.pageJumperStartButtons || i > getTotalPages() - options.pageJumperEndButtons || page == i) {
                if (i - lastPage > 1) {
                    pageGroups.add(pages.toString());
                    pages = new StringJoiner(options.pageJumperPageSeparator);
                }
                if (page == i) {
                    pages.add(formatPageString(options.pageJumperCurrentPageFormat, i));
                } else {
                    pages.add(formatPageString(formatPageJumper(i), i));
                }
                lastPage = i;
            }
        }
        if (!pages.toString().isBlank()) {
            pageGroups.add(pages.toString());
        }
        return pageGroups.toString();
    }

}
