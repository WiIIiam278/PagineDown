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

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class PaginatedListTests {

    // Generates a test list of StringListItems
    private List<StringListItem> generateListData(final int size, final String format) {
        final ArrayList<StringListItem> dummyData = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            dummyData.add(new StringListItem(format.replaceAll("#", String.valueOf(i + 1))));
        }
        return dummyData;
    }

    @Test
    public void testListPagination() {
        final String listPage = PaginatedList.of(generateListData(100, "Element #")).getRawPage(3);
        Assertions.assertNotNull(listPage);
    }

    @Test
    public void testPageItemDividing() {
        final int pageCount = PaginatedList.of(
                generateListData(40, "Item #"),
                ListOptions.builder().setItemsPerPage(10).build()
        ).getTotalPages();
        Assertions.assertEquals(4, pageCount);
    }

    @Test
    public void testPageJumperButtonsOnLongList() {
        final PaginatedList<StringListItem> longList = PaginatedList.of(
                generateListData(200, "Item #"),
                ListOptions.builder()
                        .setItemsPerPage(10)
                        .setPageJumperStartButtons(2)
                        .setPageJumperEndButtons(2)
                        .setPageJumperPageFormat("%target_page_index%")
                        .setPageJumperCurrentPageFormat("%current_page%")
                        .build()
        );
        Assertions.assertEquals("1|2…19|20", longList.getPageJumperButtons(2));
        Assertions.assertEquals("1|2…6…19|20", longList.getPageJumperButtons(6));
        Assertions.assertEquals("1|2…19|20", longList.getPageJumperButtons(20));
        Assertions.assertEquals("1|2…19|20", longList.getPageJumperButtons(1));
    }

    @Test
    public void testPageJumperButtonsOnMediumList() {
        final PaginatedList<StringListItem> mediumList = PaginatedList.of(
                generateListData(45, "Item #"),
                ListOptions.builder()
                        .setItemsPerPage(10)
                        .setPageJumperStartButtons(2)
                        .setPageJumperEndButtons(2)
                        .setPageJumperPageFormat("%target_page_index%")
                        .setPageJumperCurrentPageFormat("%current_page%")
                        .build()
        );
        Assertions.assertEquals("1|2…4|5", mediumList.getPageJumperButtons(1));
        Assertions.assertEquals("1|2|3|4|5", mediumList.getPageJumperButtons(3));
        Assertions.assertEquals("1|2…4|5", mediumList.getPageJumperButtons(5));
    }

    @Test
    public void testPageJumperButtonsOnShortList() {
        final PaginatedList<StringListItem> shortList = PaginatedList.of(
                generateListData(20, "Item #"),
                ListOptions.builder()
                        .setItemsPerPage(10)
                        .setPageJumperStartButtons(2)
                        .setPageJumperEndButtons(2)
                        .setPageJumperPageFormat("%target_page_index%")
                        .setPageJumperCurrentPageFormat("%current_page%")
                        .build()
        );
        Assertions.assertEquals("1|2", shortList.getPageJumperButtons(2));
    }
}
