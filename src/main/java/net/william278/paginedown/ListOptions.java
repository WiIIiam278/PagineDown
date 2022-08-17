package net.william278.paginedown;

import org.jetbrains.annotations.NotNull;

import java.awt.*;

/**
 * Options, including placeholder strings, used to generate a {@link PaginatedList} of items
 */
public class ListOptions {

    @NotNull
    protected String headerFormat = "[Viewing %topic%](%color%) [(%first_item_on_page_index%-%last_item_on_page_index% of](%color%) [%total_items%](%color% bold)[)](%color%)";
    @NotNull
    protected String footerFormat = "%previous_page_button%Page [%current_page%](%color%)/[%total_pages%](%color%)%next_page_button%   %page_jumpers%";
    @NotNull
    protected String previousButtonFormat = "[◁](white show_text=%color%View previous page \\(%previous_page_index%\\) run_command=/%command% %previous_page_index%) ";
    @NotNull
    protected String nextButtonFormat = " [▷](white show_text=%color%View next page \\(%next_page_index%\\) run_command=/%command% %next_page_index%)";
    @NotNull
    protected String pageJumpersFormat = "(%page_jump_buttons%)";
    @NotNull
    protected String pageJumperPageSeparator = "|";
    @NotNull
    protected String pageJumperGroupSeparator = "…";
    @NotNull
    protected String pageJumperCurrentPageFormat = "[%current_page%](%color%)";
    @NotNull
    protected String pageJumperPageFormat = "[%target_page_index%](show_text=&7Jump to page %target_page_index% run_command=/%command% %target_page_index%)";
    @NotNull
    protected String topic = "List";
    @NotNull
    protected String command = "example";
    @NotNull
    protected Color themeColor = new Color(0x00fb9a);
    protected boolean spaceAfterHeader = true;
    protected boolean spaceBeforeFooter = true;
    protected boolean escapeItemsMineDown = true;
    @NotNull
    protected String itemSeparator = "\n";
    protected int itemsPerPage = 10;

    protected int pageJumperStartButtons = 3;

    protected int pageJumperEndButtons = 3;

    private ListOptions() {
    }

    @SuppressWarnings("unused")
    public static class Builder {
        @NotNull
        private final ListOptions options = new ListOptions();

        @NotNull
        public Builder setHeaderFormat(@NotNull String headerFormat) {
            options.headerFormat = headerFormat;
            return this;
        }

        @NotNull
        public Builder setFooterFormat(@NotNull String footerFormat) {
            options.footerFormat = footerFormat;
            return this;
        }

        @NotNull
        public Builder setItemSeparator(@NotNull String itemSeparator) {
            options.itemSeparator = itemSeparator;
            return this;
        }

        @NotNull
        public Builder setThemeColor(@NotNull Color themeColor) {
            options.themeColor = themeColor;
            return this;
        }

        @NotNull
        public Builder setSpaceAfterHeader(final boolean spaceAfterHeader) {
            options.spaceAfterHeader = spaceAfterHeader;
            return this;
        }

        @NotNull
        public Builder setSpaceBeforeFooter(final boolean spaceBeforeFooter) {
            options.spaceBeforeFooter = spaceBeforeFooter;
            return this;
        }

        @NotNull
        public Builder setItemsPerPage(final int itemsPerPage) {
            options.itemsPerPage = itemsPerPage;
            return this;
        }

        @NotNull
        public Builder setTopic(@NotNull String topic) {
            options.topic = topic;
            return this;
        }

        @NotNull
        public Builder setCommand(@NotNull String command) {
            options.command = command;
            return this;
        }

        @NotNull
        public Builder setEscapeItemsMineDown(final boolean escapeItemsMineDown) {
            options.escapeItemsMineDown = escapeItemsMineDown;
            return this;
        }

        @NotNull
        public Builder setPageJumpersFormat(@NotNull String pageJumpersFormat) {
            options.pageJumpersFormat = pageJumpersFormat;
            return this;
        }

        @NotNull
        public Builder setPageJumperPageSeparator(@NotNull String pageJumperPageSeparator) {
            options.pageJumperPageSeparator = pageJumperPageSeparator;
            return this;
        }

        @NotNull
        public Builder setPageJumperPageFormat(@NotNull String pageJumperPageFormat) {
            options.pageJumperPageFormat = pageJumperPageFormat;
            return this;
        }

        @NotNull
        public Builder setPageJumperGroupSeparator(@NotNull String pageJumperGroupSeparator) {
            options.pageJumperGroupSeparator = pageJumperGroupSeparator;
            return this;
        }

        @NotNull
        public Builder setPageJumperCurrentPageFormat(@NotNull String pageJumperCurrentPageFormat) {
            options.pageJumperCurrentPageFormat = pageJumperCurrentPageFormat;
            return this;
        }

        @NotNull
        public Builder setPreviousButtonFormat(@NotNull String previousButtonFormat) {
            options.previousButtonFormat = previousButtonFormat;
            return this;
        }

        @NotNull
        public Builder setNextButtonFormat(@NotNull String nextButtonFormat) {
            options.nextButtonFormat = nextButtonFormat;
            return this;
        }

        @NotNull
        public Builder setPageJumperStartButtons(final int pageJumperStartButtons) {
            options.pageJumperStartButtons = pageJumperStartButtons;
            return this;
        }

        @NotNull
        public Builder setPageJumperEndButtons(final int pageJumperEndButtons) {
            options.pageJumperEndButtons = pageJumperEndButtons;
            return this;
        }

        @NotNull
        public ListOptions build() {
            return options;
        }
    }
}
