
package com.blend.research;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;

import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

public class CreatePdfWithTextDemo {

	private static PDPageContentStream setupPage(PDDocument doc) throws IOException {

		PDPage page = new PDPage();
		doc.addPage(page);

		PDFont font = PDType1Font.HELVETICA;

		PDPageContentStream contents = new PDPageContentStream(doc, page);
		contents.beginText();
		contents.setFont(font, 12);
		contents.newLineAtOffset(50, 750);

		return contents;

	}

	public static InputStream makePdf(String text) throws IOException {


		PDDocument doc = new PDDocument();
		try {

			PDPageContentStream contents = setupPage(doc);
			int lineNum = 0;
			Scanner scanner = new Scanner(text);

			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				contents.showText(line);
				contents.newLineAtOffset(0, -15);
				lineNum += 1;
				if (lineNum > 45) {
					contents.endText();
					contents.close();
					contents = setupPage(doc);
					lineNum = 0;
				}
			}
			scanner.close();
			contents.endText();
			contents.close();

			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
			doc.save(byteArrayOutputStream);

			InputStream returnThisPlease = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
			doc.close();

			return returnThisPlease;
			// doc.save(filename);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			doc.close();
		}
		return null;
	}
}