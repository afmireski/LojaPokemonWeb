/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helpers;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.CMYKColor;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import daos.DAOPedido;
import daos.DAOPedidoHasPokemon;
import daos.DAOPokemon;
import daos.DAOUsuario;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import models.Pedido;
import models.PedidoHasPokemon;
import models.Pokemon;
import models.Preco;
import models.Usuario;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import tools.CaixaDeFerramentas;
import tools.DiretorioDaAplicacao;

/**
 *
 * @author AFMireski
 */
public class Recibo {

    private static final String MSG_FALHA_GERAR_RELATORIO = "Houve uma falha ao gerar o relatório da venda do pedido %d";
    private static final String MSG_FALHA_ENVIAR_RELATORIO = "Houve uma falha ao "
            + "enviarmos o relatório da venda do pedido %d para o e-mail %s";
    private static final String MSG_FALHA_ENCONTRAR_RELATORIO = "O arquivo ReciboPedido%d.pdf "
            + "não foi encontrado";

    public static void emitirRecibo(Integer pedidoID, String reciboPath) throws Exception {
        generateReciboByPedido(pedidoID, reciboPath);
        enviarReciboPorEmail(pedidoID, reciboPath);
    }

    private static void generateReciboByPedido(Integer pedidoID, String reciboPath) throws Exception {
        final Font titleFont = FontFactory.getFont(FontFactory.HELVETICA, 16, Font.BOLD, new CMYKColor(89, 34, 0, 64));
        final Font enphasisFont = FontFactory.getFont(FontFactory.HELVETICA, 14, Font.BOLD, new CMYKColor(89, 34, 0, 14));
        final Font defaultFont = FontFactory.getFont(FontFactory.HELVETICA, 12, Font.BOLD);
        final Font tableColHeaderFont = FontFactory.getFont(FontFactory.HELVETICA, 12, Font.BOLD, BaseColor.WHITE);

        final DAOPedidoHasPokemon daoPHP = new DAOPedidoHasPokemon();
        final DAOPedido daoPedido = new DAOPedido();
        final DAOPokemon daoPokemon = new DAOPokemon();

        ///BUSCA OS DADOS DO PEDIDO
        final List<PedidoHasPokemon> phps = daoPHP.findAllPHPByPedidoID(pedidoID);
        final Pedido pedido = daoPedido.get(pedidoID);

        final CaixaDeFerramentas cf = new CaixaDeFerramentas();

        final String path = String.format("%s/ReciboPedido%d.pdf", reciboPath, pedidoID);

        Document document = new Document();

        PdfWriter writer = PdfWriter.getInstance(
                document,
                new FileOutputStream(path));

        try {
            document.open();

            Paragraph title = new Paragraph("Loja Pokémon", titleFont);
            document.add(title);

            Paragraph br1 = new Paragraph(" ");
            document.add(br1);

            Paragraph nPedido = new Paragraph(String.format("Nº do pedido: %d", pedidoID), enphasisFont);
            document.add(nPedido);

            Paragraph cliente = new Paragraph(
                    String.format(
                            "Cliente: %s",
                            pedido.getUsuarioID().getPessoaCPF().getNome()),
                    defaultFont);
            document.add(cliente);

            Paragraph cartao = new Paragraph(
                    String.format(
                            "Cartao: %s",
                            pedido.getCartaoID().getNome()),
                    defaultFont);
            document.add(cartao);

            Paragraph dataCompra = new Paragraph(
                    String.format(
                            "Data da compra: %s",
                            cf.converteDeDateParaString(pedido.getDataPedido())),
                    defaultFont);
            document.add(dataCompra);

            Paragraph br2 = new Paragraph(" ");
            document.add(br2);

            ///CRIA TABELA PARA DETALHAR A COMPOSIÇÂO DO PEDIDO
            PdfPTable table = new PdfPTable(3);
            table.setWidthPercentage(80);
            table.setSpacingBefore(10f);
            table.setSpacingBefore(10f);

            float[] colWidths = {2f, 1f, 2f};
            table.setWidths(colWidths);

            PdfPCell pokeCol = new PdfPCell(new Paragraph("Pokémon", tableColHeaderFont));
            pokeCol.setBackgroundColor(new CMYKColor(89, 34, 0, 64));
            pokeCol.setPaddingLeft(10);
            pokeCol.setHorizontalAlignment(Element.ALIGN_CENTER);
            pokeCol.setVerticalAlignment(Element.ALIGN_MIDDLE);

            PdfPCell quantidadeCol = new PdfPCell(new Paragraph("Quantidade", tableColHeaderFont));
            quantidadeCol.setBackgroundColor(new CMYKColor(89, 34, 0, 64));
            quantidadeCol.setPaddingLeft(10);
            quantidadeCol.setHorizontalAlignment(Element.ALIGN_CENTER);
            quantidadeCol.setVerticalAlignment(Element.ALIGN_MIDDLE);
            
            PdfPCell valorCol = new PdfPCell(new Paragraph("Valor Unitário (R$)", tableColHeaderFont));
            valorCol.setBackgroundColor(new CMYKColor(89, 34, 0, 64));
            valorCol.setPaddingLeft(10);
            valorCol.setHorizontalAlignment(Element.ALIGN_CENTER);
            valorCol.setVerticalAlignment(Element.ALIGN_MIDDLE);

            table.addCell(pokeCol);
            table.addCell(quantidadeCol);
            table.addCell(valorCol);

            double valor_total = 0.0;
            for (PedidoHasPokemon php : phps) {
                Pokemon pokemon = daoPokemon.get(php.getPedidoHasPokemonPK().getPokemonID());
                Preco preco = pokemon.getPrecoList().get(pokemon.getPrecoList().size() - 1);
                valor_total += preco.getValor() * php.getQuantidade();

                PdfPCell pokeNome = new PdfPCell(new Paragraph(pokemon.getNome(), defaultFont));
                PdfPCell pokeQtd = new PdfPCell(new Paragraph(String.valueOf(php.getQuantidade()), defaultFont));
                PdfPCell pokeValor = new PdfPCell(new Paragraph(
                        String.format("%.2f", preco.getValor()), defaultFont));

                table.addCell(pokeNome);
                table.addCell(pokeQtd);
                table.addCell(pokeValor);
            }
            table.setHorizontalAlignment(Element.ALIGN_LEFT);
            document.add(table);

            Paragraph br3 = new Paragraph(" ");
            document.add(br3);

            ///MOSTRA O VALOR TOTAL DO PEDIDO
            Paragraph total = new Paragraph(String.format("Total: R$ %.2f", valor_total), titleFont);
            document.add(total);

            document.close();
            writer.close();

        } catch (Exception e) {
            document.close();
            writer.close();

            File pdf = new File(path);
            if (pdf.exists()) {
                pdf.delete();
            }
            throw new Exception(String.format(MSG_FALHA_GERAR_RELATORIO, pedidoID));
        }
    }

    private static void enviarReciboPorEmail(Integer pedidoID, String reciboPath) throws Exception {
        final DAOPedido daoPedido = new DAOPedido();
        final DAOUsuario daoUsuario = new DAOUsuario();

        ///BUSCA O REMETENTE
        final Usuario remetente = daoUsuario.get(100);

        ///BUSCA OS DADOS DO PEDIDO
        final Pedido pedido = daoPedido.get(pedidoID);

        final String path = String.format("%s/ReciboPedido%d.pdf", reciboPath, pedidoID);

        Properties props = System.getProperties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(remetente.getEmail(), remetente.getSenha());
            }

        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(remetente.getEmail()));
            message.setRecipients(
                    Message.RecipientType.TO, InternetAddress.parse(pedido.getUsuarioID().getEmail()));
            message.setSubject("Confirmação de pagamento Loja Pokémon");

            String msg = "Olá, a sua compra foi confirmada, aqui está seu recibo";

            MimeBodyPart mimeBodyPart = new MimeBodyPart();
            mimeBodyPart.setContent(msg, "text/html");

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(mimeBodyPart);

            message.setContent(multipart);

            MimeBodyPart attachmentBodyPart = new MimeBodyPart();
            attachmentBodyPart.attachFile(new File(path));
            multipart.addBodyPart(attachmentBodyPart);

            Transport.send(message);
        } catch (MessagingException e) {
            throw new Exception(String.format(
                    MSG_FALHA_ENVIAR_RELATORIO,
                    pedidoID, pedido.getUsuarioID().getEmail()));
        } catch (IOException e) {
            throw new Exception(String.format(MSG_FALHA_ENCONTRAR_RELATORIO, pedidoID));
        }

    }
}
