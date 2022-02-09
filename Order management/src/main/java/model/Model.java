package model;

import bll.ClientBLL;
import bll.ProductBLL;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import dao.ProductDAO;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Aceasta clasa contine metode care tin de logica aplicatiei, precum calcularea pretului comenzii sau generarea chitantei
 */
public class Model {

    /**
     * Metoda care calculeaza pretul total al comenzii si actualizeaza cantitatea produsului cumparat
     *
     * @param quantity    cantitatea veche a comenzii sau 0 daca este o noua comanda
     * @param product     produsul cumparat
     * @param newQuantity numarul de produse cumparate
     * @return pretul total al comenzii
     */
    public double placeOrder(int quantity, String product, int newQuantity) {
        ProductDAO productDAO = new ProductDAO();
        ProductBLL productBLL = new ProductBLL();
        Product prod1 = productDAO.findByField(product, "product_name");
        double price = prod1.getPrice() * newQuantity;
        prod1.setQuantity(prod1.getQuantity() + (quantity - newQuantity));
        if (prod1.getQuantity() < 0) {
            throw new IllegalArgumentException("Not enough stock left.");
        }
        productBLL.updateProduct(prod1);
        return price;
    }

    /**
     * Metoda care genereaza chitanta in format pdf
     *
     * @param orders comanda pentru care va fi generata chitanta
     * @throws FileNotFoundException in cazul in care fisierul nu a putut fi creat/deschis
     * @throws DocumentException     in cazul in care nu se poate scrie in fisier
     */
    public void bill(Orders orders) throws FileNotFoundException, DocumentException {
        Document bill = new Document();
        String title = "Bill_";
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("dd_MMM_yyyy_HH_mm_ss");
        title += format.format(date) + ".pdf";
        PdfWriter.getInstance(bill, new FileOutputStream(title));
        bill.open();
        Font font = FontFactory.getFont(FontFactory.COURIER, 12, BaseColor.BLACK);

        ClientBLL clientBLL = new ClientBLL();
        ProductBLL productBLL = new ProductBLL();
        Client client = clientBLL.findClientById(orders.getClient_id());
        Product product = productBLL.findProductByName(orders.getProduct_name());

        Paragraph chunk1 = new Paragraph("----- Receipt -----\n", font);
        chunk1.setAlignment(Element.ALIGN_CENTER);
        Paragraph chunk2 = new Paragraph("Order: " + orders.getOrder_id() + "\n", font);
        chunk2.setAlignment(Element.ALIGN_LEFT);
        Paragraph chunk3 = new Paragraph("Client: " + client.getClient_name() + ", id: " + client.getClient_id() + ", address: " + client.getAddress() + "\n", font);
        chunk3.setAlignment(Element.ALIGN_LEFT);
        Paragraph chunk4 = new Paragraph("Purchased: " + orders.getProduct_name() + " x" + orders.getQuantity() + "....." + orders.getTotal_price() + "\n", font);
        chunk4.setAlignment(Element.ALIGN_LEFT);
        Paragraph chunk5 = new Paragraph(product.getPrice() + " * " + orders.getQuantity() + "\n", font);
        chunk5.setAlignment(Element.ALIGN_LEFT);
        Paragraph chunk6 = new Paragraph("----- Thank you! -----\n", font);
        chunk6.setAlignment(Element.ALIGN_CENTER);

        bill.add(chunk1);
        bill.add(chunk2);
        bill.add(chunk3);
        bill.add(chunk4);
        bill.add(chunk5);
        bill.add(chunk6);
        bill.close();
    }
}
