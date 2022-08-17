package net.william278.paginedown;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class PaginatedListTests {

    // Generates dummy list data for testing
    private List<String> generateListData(final int size, final String format) {
        final ArrayList<String> dummyData = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            dummyData.add(format.replaceAll("#", String.valueOf(i + 1)));
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
        final int pageCount = PaginatedList.of(generateListData(40, "Item #"),
                new ListOptions.Builder()
                        .setItemsPerPage(10)
                        .build()
        ).getTotalPages();
        Assertions.assertEquals(4, pageCount);
    }

    @Test
    public void testPageJumperButtonsOnLongList() {
        final PaginatedList longList = PaginatedList.of(generateListData(200, "Item #"),
                new ListOptions.Builder()
                        .setItemsPerPage(10)
                        .setPageJumperPageFormat("%target_page_index%")
                        .setPageJumperCurrentPageFormat("%current_page%")
                        .build());
        Assertions.assertEquals("1|2…19|20", longList.getPageJumperButtons(2));
        Assertions.assertEquals("1|2…6…19|20", longList.getPageJumperButtons(6));
        Assertions.assertEquals("1|2…19|20", longList.getPageJumperButtons(20));
        Assertions.assertEquals("1|2…19|20", longList.getPageJumperButtons(1));
    }

    @Test
    public void testPageJumperButtonsOnMediumList() {
        final PaginatedList mediumList = PaginatedList.of(generateListData(45, "Item #"),
                new ListOptions.Builder()
                        .setItemsPerPage(10)
                        .setPageJumperPageFormat("%target_page_index%")
                        .setPageJumperCurrentPageFormat("%current_page%")
                        .build());
        Assertions.assertEquals("1|2…4|5", mediumList.getPageJumperButtons(1));
        Assertions.assertEquals("1|2|3|4|5", mediumList.getPageJumperButtons(3));
        Assertions.assertEquals("1|2…4|5", mediumList.getPageJumperButtons(5));
    }

    @Test
    public void testPageJumperButtonsOnShortList() {
        final PaginatedList shortList = PaginatedList.of(generateListData(20, "Item #"),
                new ListOptions.Builder()
                        .setItemsPerPage(10)
                        .setPageJumperPageFormat("%target_page_index%")
                        .setPageJumperCurrentPageFormat("%current_page%")
                        .build());
        Assertions.assertEquals("1|2", shortList.getPageJumperButtons(2));
    }
}
