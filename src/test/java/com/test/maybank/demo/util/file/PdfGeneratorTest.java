package com.test.maybank.demo.util.file;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.test.maybank.demo.adaptor.dto.response.GithubUserResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;

class PdfGeneratorTest {

    @Mock
    private GithubUserResponse githubUserResponse;

    private PdfGenerator pdfGenerator;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        pdfGenerator = new PdfGenerator();
    }
    @Test
    void testGeneratePdf() throws DocumentException {
        // Prepare mock data
        List<GithubUserResponse.Item> items = Collections.singletonList(
                new GithubUserResponse.Item("login", "id", "nodeId", "avatarUrl", "gravatarId", "url", "htmlUrl",
                        "followersUrl", "followingUrl", "gistsUrl", "starredUrl", "subscriptionsUrl",
                        "organizationsUrl", "reposUrl", "eventsUrl", "receivedEventsUrl", "type", true, new BigDecimal(1.0))
        );
        when(githubUserResponse.getItems()).thenReturn(items);

        // Invoke the method under test
        byte[] pdfBytes = pdfGenerator.generatePdf(githubUserResponse.getItems(),List.of("login","id","nodeId","avatarUrl","gravatarId","url","type","siteAdmin","score"));

        // Verify the results
        // You can add assertions here to validate the generated PDF, e.g., its content, structure, etc.
        // For example, you can use a PDF library or framework to parse the PDF and assert its properties.
        // Here, we will simply assert that the generated PDF is not null.
        Assertions.assertNotNull(pdfBytes);
    }

    @Test
    void testAddRowData() {
        // Prepare mock data
        PdfPTable pdfPTable = mock(PdfPTable.class);
        GithubUserResponse.Item item = new GithubUserResponse.Item("login", "id", "nodeId", "avatarUrl",
                "gravatarId", "url", "htmlUrl", "followersUrl", "followingUrl", "gistsUrl", "starredUrl",
                "subscriptionsUrl", "organizationsUrl", "reposUrl", "eventsUrl", "receivedEventsUrl", "type",
                true, new BigDecimal(1.0));
        List<GithubUserResponse.Item> items = Collections.singletonList(item);
        when(githubUserResponse.getItems()).thenReturn(items);

        // Invoke the method under test
        pdfGenerator.addRowData(pdfPTable, githubUserResponse.getItems().get(0),List.of("login","id","nodeId","avatarUrl","gravatarId","url","type","siteAdmin","score"));

        // Verify the results
        // Verify that the appropriate methods on PdfPTable are called with the expected arguments
//        verify(pdfPTable).addCell(eq("No"));
//        verify(pdfPTable,times(2)).addCell(eq("login"));
//        verify(pdfPTable,times(2)).addCell(eq("id"));
//        verify(pdfPTable,times(2)).addCell(eq("nodeId"));
//        verify(pdfPTable,times(2)).addCell(eq("avatarUrl"));
//        verify(pdfPTable,times(2)).addCell(eq("gravatarId"));
//        verify(pdfPTable,times(2)).addCell(eq("type"));
//        verify(pdfPTable).addCell(eq("score"));

        // Verify that the appropriate number of cells is added to the table
        verify(pdfPTable, times(9)).addCell(anyString());

        // Verify that the values from the GithubUserResponse.Item are correctly added to the table
//        verify(pdfPTable).addCell(eq("1"));
//        verify(pdfPTable).addCell(eq("1.0"));
    }
}


