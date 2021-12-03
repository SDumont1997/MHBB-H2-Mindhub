package com.mindhub.homebanking.utils;

import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.models.TransactionType;
import org.springframework.stereotype.Service;


import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class TransactionsExporter {

    public void setResponseHeader(HttpServletResponse response, String contentType, String extension, String prefix) throws IOException {
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy_HH-mm-ss");
        String timeStamp = dateFormat.format(new Date());
        String filename = prefix + timeStamp + extension;

        response.setContentType(contentType);

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; fileName=" + filename;
        response.setHeader(headerKey, headerValue);
    }

    public void exportToPDF(List<Transaction> transactions, Client client, Account account, HttpServletResponse response) throws DocumentException, IOException{
        setResponseHeader(response, "application/pdf", ".pdf", "Transactions");

        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());

        document.open();

        Image logo = com.lowagie.text.Image.getInstance("logo_mhbb.png");
        logo.scalePercent(6);
        logo.setAlignment(Image.ALIGN_CENTER);

        document.add(logo);

        Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        font.setSize(22);
        font.setColor(Color.BLACK);

        Paragraph title = new Paragraph(("Client: " + client.getFirstName() + " " + client.getLastName()), font);
        title.setAlignment(Paragraph.ALIGN_CENTER);

        document.add(title);

        font.setSize(18);
        Paragraph paragraph = new Paragraph("Account " + account.getNumber() + " list of transactions", font);
        paragraph.setAlignment(Paragraph.ALIGN_CENTER);

        document.add(paragraph);

        PdfPTable table = new PdfPTable(5);
        table.setWidthPercentage(100f);
        table.setSpacingBefore(10);

        writeTransactionHeader(table);
        writeTransactionData(table, transactions);

        document.add(table);
        document.close();
    }

    private void writeTransactionHeader(PdfPTable table){
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(Color.WHITE);
        cell.setPadding(5);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);

        Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        font.setColor(Color.BLACK);

        cell.setPhrase(new Phrase("Transaction date", font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Amount", font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Origin/ Destination Account", font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Account balance", font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Detail", font));
        table.addCell(cell);
    }

    private void writeTransactionData(PdfPTable table, List<Transaction> transactions){
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(Color.WHITE);
        cell.setPadding(5);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);

        Font font = FontFactory.getFont(FontFactory.HELVETICA);
        font.setColor(Color.BLACK);

        for (Transaction transaction : transactions){
            cell.setPhrase(new Phrase(transaction.getDate(), font));
            table.addCell(cell);
            if (transaction.getType() == TransactionType.CREDIT) {
                cell.setPhrase(new Phrase("+$" + transaction.getAmount(), font));
                table.addCell(cell);
            }
            if (transaction.getType() == TransactionType.DEBIT){
                cell.setPhrase(new Phrase("-$" + transaction.getAmount(), font));
                table.addCell(cell);
            }
            cell.setPhrase(new Phrase(transaction.getOtherPart(), font));
            table.addCell(cell);
            cell.setPhrase(new Phrase("$" + transaction.getAccountState(), font));
            table.addCell(cell);
            if(transaction.getDetail() == null){
                cell.setPhrase(new Phrase("-", font));
                table.addCell(cell);
            } else{
                cell.setPhrase(new Phrase(transaction.getDetail(), font));
                table.addCell(cell);
            }
        }
    }
}
