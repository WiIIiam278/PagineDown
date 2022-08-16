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
}
