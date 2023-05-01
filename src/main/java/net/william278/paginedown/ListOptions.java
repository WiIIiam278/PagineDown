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

import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Options, including placeholder strings, used to generate a {@link PaginatedList} of items
 */
public class ListOptions<I extends ListItem> {

    @NotNull
    protected String headerFormat = "[%topic% (%first_item_on_page_index%-%last_item_on_page_index% of %total_items%):](%color%)";
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
    protected List<SortOption<I>> availableSortOptions = new ArrayList<>();

    private ListOptions() {
    }

    @NotNull
    public static Builder builder() {
        return new Builder<>();
    }

    @SuppressWarnings("unused")
    public static class Builder<I extends ListItem> {
        @NotNull
        private final ListOptions<I> options = new ListOptions<>();

        protected Builder() {
        }

        @NotNull
        public Builder<I> setHeaderFormat(@NotNull String headerFormat) {
            options.headerFormat = headerFormat;
            return this;
        }

        @NotNull
        public Builder<I> setFooterFormat(@NotNull String footerFormat) {
            options.footerFormat = footerFormat;
            return this;
        }

        @NotNull
        public Builder<I> setItemSeparator(@NotNull String itemSeparator) {
            options.itemSeparator = itemSeparator;
            return this;
        }

        @NotNull
        public Builder<I> setThemeColor(@NotNull Color themeColor) {
            options.themeColor = themeColor;
            return this;
        }

        @NotNull
        public Builder<I> setSpaceAfterHeader(final boolean spaceAfterHeader) {
            options.spaceAfterHeader = spaceAfterHeader;
            return this;
        }

        @NotNull
        public Builder<I> setSpaceBeforeFooter(final boolean spaceBeforeFooter) {
            options.spaceBeforeFooter = spaceBeforeFooter;
            return this;
        }

        @NotNull
        public Builder<I> setItemsPerPage(final int itemsPerPage) {
            options.itemsPerPage = itemsPerPage;
            return this;
        }

        @NotNull
        public Builder<I> setTopic(@NotNull String topic) {
            options.topic = topic;
            return this;
        }

        @NotNull
        public Builder<I> setCommand(@NotNull String command) {
            options.command = command;
            return this;
        }

        @NotNull
        public Builder<I> setEscapeItemsMineDown(final boolean escapeItemsMineDown) {
            options.escapeItemsMineDown = escapeItemsMineDown;
            return this;
        }

        @NotNull
        public Builder<I> setPageJumpersFormat(@NotNull String pageJumpersFormat) {
            options.pageJumpersFormat = pageJumpersFormat;
            return this;
        }

        @NotNull
        public Builder<I> setPageJumperPageSeparator(@NotNull String pageJumperPageSeparator) {
            options.pageJumperPageSeparator = pageJumperPageSeparator;
            return this;
        }

        @NotNull
        public Builder<I> setPageJumperPageFormat(@NotNull String pageJumperPageFormat) {
            options.pageJumperPageFormat = pageJumperPageFormat;
            return this;
        }

        @NotNull
        public Builder<I> setPageJumperGroupSeparator(@NotNull String pageJumperGroupSeparator) {
            options.pageJumperGroupSeparator = pageJumperGroupSeparator;
            return this;
        }

        @NotNull
        public Builder<I> setPageJumperCurrentPageFormat(@NotNull String pageJumperCurrentPageFormat) {
            options.pageJumperCurrentPageFormat = pageJumperCurrentPageFormat;
            return this;
        }

        @NotNull
        public Builder<I> setPreviousButtonFormat(@NotNull String previousButtonFormat) {
            options.previousButtonFormat = previousButtonFormat;
            return this;
        }

        @NotNull
        public Builder<I> setNextButtonFormat(@NotNull String nextButtonFormat) {
            options.nextButtonFormat = nextButtonFormat;
            return this;
        }

        @NotNull
        public Builder<I> setPageJumperStartButtons(final int pageJumperStartButtons) {
            options.pageJumperStartButtons = pageJumperStartButtons;
            return this;
        }

        @NotNull
        public Builder<I> setPageJumperEndButtons(final int pageJumperEndButtons) {
            options.pageJumperEndButtons = pageJumperEndButtons;
            return this;
        }

        @NotNull
        public ListOptions<I> build() {
            return options;
        }
    }
}
