package pt.uc.dei.aor.project.presentation.bean;

import java.io.IOException;
import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import com.lowagie.text.BadElementException;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.PageSize;
import com.lowagie.text.Phrase;




@Named
@ViewScoped	
public class DocumentBean implements Serializable {

	private static final long serialVersionUID = -2553325766946051159L;

	public void preAppDate(Object document) throws IOException, BadElementException, DocumentException {
		Document pdf = (Document) document;
		pdf.open();
		pdf.setPageSize(PageSize.A4);

		Phrase phrase = new Phrase();
		phrase.add("Job applications received by date\n\n\n");
		pdf.add(phrase);
	}
	
	public void preAppPos(Object document) throws IOException, BadElementException, DocumentException {
		Document pdf = (Document) document;
		pdf.open();
		pdf.setPageSize(PageSize.A4);

		Phrase phrase = new Phrase();
		phrase.add("Job applications received by position\n\n\n");
		pdf.add(phrase);
	}
	
	public void preSponDate(Object document) throws IOException, BadElementException, DocumentException {
		Document pdf = (Document) document;
		pdf.open();
		pdf.setPageSize(PageSize.A4);

		Phrase phrase = new Phrase();
		phrase.add("Spontaneous job applications received by date\n\n\n");
		pdf.add(phrase);
	}
	
	public void preHiringTime(Object document) throws IOException, BadElementException, DocumentException {
		Document pdf = (Document) document;
		pdf.open();
		pdf.setPageSize(PageSize.A4);

		Phrase phrase = new Phrase();
		phrase.add("Average hiring time\n\n\n");
		pdf.add(phrase);
	}
	
	public void preTimeInter(Object document) throws IOException, BadElementException, DocumentException {
		Document pdf = (Document) document;
		pdf.open();
		pdf.setPageSize(PageSize.A4);

		Phrase phrase = new Phrase();
		phrase.add("Average 1st interview time\n\n\n");
		pdf.add(phrase);
	}
	
	public void preInterviews(Object document) throws IOException, BadElementException, DocumentException {
		Document pdf = (Document) document;
		pdf.open();
		pdf.setPageSize(PageSize.A4);

		Phrase phrase = new Phrase();
		phrase.add("Results of interviews\n\n\n");
		pdf.add(phrase);
	}
	
	public void preProposals(Object document) throws IOException, BadElementException, DocumentException {
		Document pdf = (Document) document;
		pdf.open();
		pdf.setPageSize(PageSize.A4);

		Phrase phrase = new Phrase();
		phrase.add("Results of proposals\n\n\n");
		pdf.add(phrase);
	}
	
	public void preReject(Object document) throws IOException, BadElementException, DocumentException {
		Document pdf = (Document) document;
		pdf.open();
		pdf.setPageSize(PageSize.A4);

		Phrase phrase = new Phrase();
		phrase.add("Reason for rejection\n\n\n");
		pdf.add(phrase);
	}
}

