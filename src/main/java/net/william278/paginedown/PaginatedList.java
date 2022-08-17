package net.william278.paginedown;


import de.themoep.minedown.MineDown;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.StringJoiner;
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
public class PaginatedList {

    /**
     * {@link ListOptions} to be used for generating the list
     */
    @NotNull
    private final ListOptions options;

    /**
     * A list of items to be paginated; can be MineDown formatted
     */
    @NotNull
    private final List<String> items;

    /**
     * Private constructor used by {@code #get(List, ListOptions)}
     *
     * @param items   a list of items to be paginated
     * @param options {@link ListOptions} to be used for generating list pages
     */
    private PaginatedList(@NotNull List<String> items, @NotNull ListOptions options) {
        this.items = items;
        this.options = options;
    }

    /**
     * Create a new {@link PaginatedList} from a {@link List} of items
     *
     * @param items The {@link List} of items to paginate
     * @return A new {@link PaginatedList}
     */
    @NotNull
    public static PaginatedList of(@NotNull List<String> items) {
        return new PaginatedList(items, new ListOptions.Builder().build());
    }

    /**
     * Create a new {@link PaginatedList} from a {@link List} of items
     *
     * @param items   The {@link List} of items to paginate
     * @param options The {@link ListOptions} to use for generating list pages
     * @return A new {@link PaginatedList}
     */
    @NotNull
    public static PaginatedList of(@NotNull List<String> items, @NotNull ListOptions options) {
        return new PaginatedList(items, options);
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
    public MineDown getNearestValidPage(final int page) {
        return getPage(Math.max(1, Math.min(getTotalPages(), page)));
    }

    /**
     * Returns a {@link MineDown} formatted message to be sent to a player of the paginated list for the specified page
     * <p>List formats and options from the {@link ListOptions} are applied to generate the list.
     *
     * @param page The page number to get
     * @return A {@link MineDown} object, for formatting the list
     * @throws PaginationException If the page number is out of bounds
     */
    public MineDown getPage(final int page) throws PaginationException {
        return new MineDown(getRawPage(page));
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
    public String getRawPage(final int page) throws PaginationException {
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

        if (options.escapeItemsMineDown) {
            menuJoiner.add(getItemsForPage(page).stream().map(MineDown::escape)
                    .collect(Collectors.joining(options.itemSeparator)));
        } else {
            menuJoiner.add(String.join(options.itemSeparator, getItemsForPage(page)));
        }

        if (!options.footerFormat.isBlank()) {
            if (options.spaceBeforeFooter) {
                menuJoiner.add("");
            }
            menuJoiner.add(formatPageString(options.footerFormat, page));
        }
        return menuJoiner.toString();
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
     * @param page The page number to get
     * @return The sub-list of items to be shown on a given page
     */
    @NotNull
    private List<String> getItemsForPage(final int page) {
        return items.subList((page - 1) * options.itemsPerPage, Math.min(items.size(), page * options.itemsPerPage));
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
                        case "topic":
                            convertedFormat.append(formatPageString(options.topic, page));
                            break;
                        case "color":
                            convertedFormat.append(String.format("#%02x%02x%02x", options.themeColor.getRed(), options.themeColor.getGreen(), options.themeColor.getBlue()));
                            break;
                        case "first_item_on_page_index":
                            convertedFormat.append(((page - 1) * options.itemsPerPage) + 1);
                            break;
                        case "last_item_on_page_index":
                            convertedFormat.append(((page - 1) * options.itemsPerPage) + getItemsForPage(page).size());
                            break;
                        case "total_items":
                            convertedFormat.append(items.size());
                            break;
                        case "current_page":
                            convertedFormat.append(page);
                            break;
                        case "total_pages":
                            convertedFormat.append(getTotalPages());
                            break;
                        case "previous_page_button":
                            if (page > 1) {
                                convertedFormat.append(formatPageString(options.previousButtonFormat, page));
                            }
                            break;
                        case "next_page_button":
                            if (page < getTotalPages()) {
                                convertedFormat.append(formatPageString(options.nextButtonFormat, page));
                            }
                            break;
                        case "next_page_index":
                            convertedFormat.append(page + 1);
                            break;
                        case "previous_page_index":
                            convertedFormat.append(page - 1);
                            break;
                        case "command":
                            convertedFormat.append(options.command);
                            break;
                        case "page_jumpers":
                            if (getTotalPages() > 2) {
                                convertedFormat.append(formatPageString(options.pageJumpersFormat, page));
                            }
                            break;
                        case "page_jump_buttons": {
                            convertedFormat.append(getPageJumperButtons(page));
                            break;
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
            if (i < 3 || i > getTotalPages() - 2 || page == i) {
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
